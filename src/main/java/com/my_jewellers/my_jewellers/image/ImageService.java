package com.my_jewellers.my_jewellers.image;

import com.my_jewellers.my_jewellers.exception.OperationNotPermittedException;
import com.my_jewellers.my_jewellers.product.Product;
import com.my_jewellers.my_jewellers.product.ProductRepository;
import com.my_jewellers.my_jewellers.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ImageMapper imageMapper;

    private Image storeImage(MultipartFile file, Integer productId, Integer userId) throws IOException {
        log.info("Storing image with imageName " + file.getOriginalFilename() + " & productId " + productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("No Product found with ID:: " + productId));
        Image image = new Image();
        image.setImageName(file.getOriginalFilename());
        image.setImageType(file.getContentType());
        image.setData(imageMapper.compressImage(file.getBytes()));
        image.setProduct(product);
        image.setLastModifiedBy(userId);
        return imageRepository.save(image);
    }

    public List<ImageResponse> storeMultipleImages(MultipartFile[] images, Integer productId, Authentication connectedUser) throws IOException {
        User user = ((User) connectedUser.getPrincipal());
        if (user.getRoles() == null || user.getRoles().stream().noneMatch(role -> role.getRoleName().equals("ADMIN"))) {
            throw new OperationNotPermittedException("You do not have the permission to make these changes");
        }
        List<Image> savedImages = new ArrayList<>();
        for (MultipartFile image : images) {
            savedImages.add(storeImage(image, productId, user.getId()));
        }
        return savedImages.stream().map(imageMapper::toImageResponse).toList();
    }

    public byte[] getImage(Integer imageId) {
        Optional<Image> dbImage = imageRepository.findById(imageId);

        return dbImage.map(image -> {
            try {
                return imageMapper.decompressImage(image.getData());
            } catch (DataFormatException | IOException exception) {
                throw new ContextedRuntimeException("Error downloading an image", exception)
                        .addContextValue("Image ID", image.getId())
                        .addContextValue("Image name", image.getImageName());
            }
        }).orElse(null);
    }

}

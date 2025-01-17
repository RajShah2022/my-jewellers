package com.my_jewellers.my_jewellers.product;

import com.my_jewellers.my_jewellers.common.PageResponse;
import com.my_jewellers.my_jewellers.exception.OperationNotPermittedException;
import com.my_jewellers.my_jewellers.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Integer createNewProduct(@Valid ProductRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        if (user.getRoles() == null || user.getRoles().stream().noneMatch(role -> role.getRoleName().equals("ADMIN"))) {
            throw new OperationNotPermittedException("You do not have the permission to make these changes");
        }
        Product product = productMapper.toProduct(request);
        product.setCreatedBy(user.getId());
        return productRepository.save(product).getId();
    }

    public Integer editProduct(@Valid ProductRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        if (user.getRoles() == null || user.getRoles().stream().noneMatch(role -> role.getRoleName().equals("ADMIN"))) {
            throw new OperationNotPermittedException("You do not have the permission to make these changes");
        }
        Product product = productRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("No Product found with ID:: " + request.id()));
        product.setProductName(request.productName());
        product.setDescriptionList(productMapper.toDescList(request.descriptionList(), product));
        return productRepository.save(product).getId();
    }

    public void deleteProduct(@Valid ProductIdRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        if (user.getRoles() == null || user.getRoles().stream().noneMatch(role -> role.getRoleName().equals("ADMIN"))) {
            throw new OperationNotPermittedException("You do not have the permission to make these changes");
        }
        Product product = productRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("No Product found with ID:: " + request.id()));

        productRepository.deleteById(product.getId());
    }

    public PageResponse<ProductSnippetResponse> findAllProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Product> products = productRepository.findAll(pageable);
        List<ProductSnippetResponse> productResponse = products.stream()
                .map(productMapper::toProductSnippetResponse)
                .toList();
        return new PageResponse<>(
                productResponse,
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.isFirst(),
                products.isLast()
        );
    }

    public ProductDetailResponse findProductById(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductDetailResponse)
                .orElseThrow(() -> new EntityNotFoundException("No product found with ID:: " + productId));
    }
}

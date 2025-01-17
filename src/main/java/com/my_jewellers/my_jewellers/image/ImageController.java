package com.my_jewellers.my_jewellers.image;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("images")
@RequiredArgsConstructor
@Tag(name = "images")
public class ImageController {

    private final ImageService service;

    @PostMapping("/upload")
    public ResponseEntity<List<ImageResponse>> uploadImages(
            @RequestParam("images") MultipartFile[] images,
            @RequestParam("product_id") Integer productId,
            Authentication connectedUser
    ) throws IOException {
        return ResponseEntity.ok(service.storeMultipleImages(images, productId, connectedUser));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable Integer id) {
        byte[] imageData = service.getImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG); // Ensure correct content type
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}

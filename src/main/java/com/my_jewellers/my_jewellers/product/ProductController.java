package com.my_jewellers.my_jewellers.product;

import com.my_jewellers.my_jewellers.common.PageRequest;
import com.my_jewellers.my_jewellers.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@Tag(name = "Product")
public class ProductController {

    private final ProductService service;


    @PostMapping("/createNewProduct")
    public ResponseEntity<Integer> createNewProduct(
            @RequestBody @Valid ProductRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.createNewProduct(request, connectedUser));
    }

    @PostMapping("/editProduct")
    public ResponseEntity<Integer> editProduct(
            @RequestBody @Valid ProductRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.editProduct(request, connectedUser));
    }

    @PostMapping("/deleteProduct")
    public ResponseEntity<?> deleteProduct(
            @RequestBody @Valid ProductIdRequest request,
            Authentication connectedUser
    ) {
        service.deleteProduct(request, connectedUser);
        return ResponseEntity.accepted().build();
    }


    @PostMapping("/findAllProducts")
    public ResponseEntity<PageResponse<ProductSnippetResponse>> findAllProducts(
            @RequestBody @Valid PageRequest request
    ) {
        return ResponseEntity.ok(service.findAllProducts(request.page(), request.size()));
    }


    @PostMapping("/findProductById")
    public ResponseEntity<ProductDetailResponse> findProductById(
            @RequestBody @Valid ProductIdRequest request
    ) {
        return ResponseEntity.ok(service.findProductById(request.id()));
    }
}

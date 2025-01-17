package com.my_jewellers.my_jewellers.category;

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
@RequestMapping("categories")
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {

    private final CategoryService service;

    @PostMapping("/createNewCategory")
    public ResponseEntity<Integer> createNewCategory(
            @RequestBody @Valid CategoryRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.createNewCategory(request, connectedUser));
    }

    @PostMapping("/editCategory")
    public ResponseEntity<Integer> editCategory(
            @RequestBody @Valid CategoryRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.editCategory(request, connectedUser));
    }

    @PostMapping("/deleteCategory")
    public ResponseEntity<?> deleteCategory(
            @RequestBody @Valid CategoryIdRequest request,
            Authentication connectedUser
    ) {
        service.deleteCategory(request, connectedUser);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/findAllCategories")
    public ResponseEntity<PageResponse<CategoryResponse>> findAllCategories(
            @RequestBody @Valid PageRequest request
    ) {
        return ResponseEntity.ok(service.findAllCategories(request.page(), request.size()));
    }

    @PostMapping("/findCategoryById")
    public ResponseEntity<CategoryResponse> findCategoryById(
            @RequestBody @Valid CategoryIdRequest request
    ) {
        return ResponseEntity.ok(service.findCategoryById(request.id()));
    }

}

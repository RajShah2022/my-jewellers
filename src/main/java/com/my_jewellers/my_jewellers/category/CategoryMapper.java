package com.my_jewellers.my_jewellers.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryMapper {
    public Category toCategory(@Valid CategoryRequest request) {
        return Category.builder()
                .id(request.id())
                .categoryName(request.categoryName())
                .priceRate(request.priceRate())
                .build();
    }

    public CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .priceRate(category.getPriceRate())
                .build();
    }
}

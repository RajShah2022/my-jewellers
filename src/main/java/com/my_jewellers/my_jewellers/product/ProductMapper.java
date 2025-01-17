package com.my_jewellers.my_jewellers.product;

import com.my_jewellers.my_jewellers.category.Category;
import com.my_jewellers.my_jewellers.category.CategoryMapper;
import com.my_jewellers.my_jewellers.category.CategoryRepository;
import com.my_jewellers.my_jewellers.description.Description;
import com.my_jewellers.my_jewellers.description.DescriptionRequest;
import com.my_jewellers.my_jewellers.description.DescriptionResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductMapper {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Product toProduct(@Valid ProductRequest request) {
        Product product = new Product();
        product.setId(request.id());
        product.setProductName(request.productName());
        product.setDescriptionList(toDescList(request.descriptionList(), product));
        return product;
    }

    public List<Description> toDescList(List<DescriptionRequest> descriptionRequests, Product product) {

        List<Description> newDescriptionList = new ArrayList<>();
        for (DescriptionRequest descriptionRequest : descriptionRequests) {
            newDescriptionList.add(toDescription(descriptionRequest, product));
        }
        return newDescriptionList;
    }

    public Description toDescription(DescriptionRequest descriptionRequest, Product product) {
        Category savedCategory = categoryRepository.findById(descriptionRequest.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("No Category found with ID:: " + descriptionRequest.categoryId()));

        return Description.builder()
                .id(descriptionRequest.id())
                .descriptionName(descriptionRequest.descriptionName())
                .quantity(descriptionRequest.quantity())
                .units(descriptionRequest.units())
                .product(product)
                .category(savedCategory)
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .descriptionList(toDescListResponse(product.getDescriptionList()))
                .build();
    }

    private List<DescriptionResponse> toDescListResponse(List<Description> descriptionList) {
        return descriptionList.stream().map(this::toDescriptionResponse).toList();
    }

    private DescriptionResponse toDescriptionResponse(Description description) {
        return DescriptionResponse.builder()
                .id(description.getId())
                .descriptionName(description.getDescriptionName())
                .quantity(description.getQuantity())
                .units(description.getUnits())
                .categoryId(description.getCategory().getId())
                .build();
    }
}

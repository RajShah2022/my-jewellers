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

    public PageResponse<ProductResponse> findAllProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Product> products = productRepository.findAll(pageable);
        List<ProductResponse> productResponse = products.stream()
                .map(productMapper::toProductResponse)
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

    public ProductResponse findProductById(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("No product found with ID:: " + productId));
    }

//    public Product saveProductWithMultipleDescAndCategories() {
//
//        // Create Product
//        Product product = new Product();
//        product.setProductName("Jewelry Set");
//
//
//        // Create Categories
//        Category category1 = new Category();
//        category1.setCategoryName("Gold");
//        category1.setPriceRate(59F);
//        category1.setCreatedBy(0);
//
//        Category category2 = new Category();
//        category2.setCategoryName("Silver");
//        category2.setPriceRate(29F);
//        category2.setCreatedBy(0);
//
//        // Create Descriptions
//        Description desc1 = new Description();
//        desc1.setDescriptionName("2 grams");
//        desc1.setQuantity(2F);
//        desc1.setUnits("grams");
//        desc1.setCreatedBy(0);
//
//        Description desc2 = new Description();
//        desc2.setDescriptionName("4 grams");;
//        desc2.setQuantity(4F);
//        desc2.setCreatedBy(0);
//
////        Category savedCategory1 = categoryRepository.save(category1);
////        Category savedCategory2 = categoryRepository.save(category2);
//
//        desc1.setCategory(category1);
//        desc2.setCategory(category2);
//
////        Description savedDesc1 = descriptionRepository.save(desc1);
////        Description savedDesc2 = descriptionRepository.save(desc2);
////        // Add to map: Desc -> Category
////        product.getDescCategoryMap().put(savedDesc1, savedCategory1); // 2 grams -> Gold
////        product.getDescCategoryMap().put(savedDesc2, savedCategory2); // 4 grams -> Silver
//        product.setCreatedBy(0);
//
//        List<Description> descriptionList = new ArrayList<Description>();
//
//
//        descriptionList.add(desc1);
//        descriptionList.add(desc2);
////        descriptionList.add(savedDesc1);
////        descriptionList.add(savedDesc2);
//        product.setDescriptionList(descriptionList);
//
//        // Save Product
//        return productRepository.save(product);
//    }

}

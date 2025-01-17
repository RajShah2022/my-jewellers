package com.my_jewellers.my_jewellers.category;

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
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Integer createNewCategory(@Valid CategoryRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        if (user.getRoles() == null || user.getRoles().stream().noneMatch(role -> role.getRoleName().equals("ADMIN"))) {
            throw new OperationNotPermittedException("You do not have the permission to make these changes");
        }
        Category category = categoryMapper.toCategory(request);
        category.setCreatedBy(user.getId());
        return categoryRepository.save(category).getId();
    }


    public Integer editCategory(@Valid CategoryRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        if (user.getRoles() == null || user.getRoles().stream().noneMatch(role -> role.getRoleName().equals("ADMIN"))) {
            throw new OperationNotPermittedException("You do not have the permission to make these changes");
        }
        Category category = categoryRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("No Category found with ID:: " + request.id()));
        category.setCategoryName(request.categoryName());
        category.setPriceRate(request.priceRate());
        category.setLastModifiedBy(user.getId());
        return categoryRepository.save(category).getId();
    }


    public void deleteCategory(@Valid CategoryIdRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        if (user.getRoles() == null || user.getRoles().stream().noneMatch(role -> role.getRoleName().equals("ADMIN"))) {
            throw new OperationNotPermittedException("You do not have the permission to make these changes");
        }
        Category category = categoryRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("No Category found with ID:: " + request.id()));

        categoryRepository.deleteById(category.getId());
    }

    public PageResponse<CategoryResponse> findAllCategories(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Category> categories = categoryRepository.findAll(pageable);
        List<CategoryResponse> categoryResponse = categories.stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
        return new PageResponse<>(
                categoryResponse,
                categories.getNumber(),
                categories.getSize(),
                categories.getTotalElements(),
                categories.getTotalPages(),
                categories.isFirst(),
                categories.isLast()
        );
    }


    public CategoryResponse findCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .map(categoryMapper::toCategoryResponse)
                .orElseThrow(() -> new EntityNotFoundException("No category found with ID:: " + categoryId));
    }
}

package com.fresh.ecommerce.service;

import com.fresh.ecommerce.dto.CategoryDTO;
import com.fresh.ecommerce.entity.Category;
import com.fresh.ecommerce.exception.ResourceNotFoundException;
import com.fresh.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO.Response> getAllCategories() {
        return categoryRepository.findAllByOrderBySortOrderAsc().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CategoryDTO.Response getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("分类不存在"));
        return toResponse(category);
    }

    @Transactional
    public CategoryDTO.Response createCategory(CategoryDTO.CreateRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .iconUrl(request.getIconUrl())
                .sortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0)
                .build();
        
        return toResponse(categoryRepository.save(category));
    }

    @Transactional
    public CategoryDTO.Response updateCategory(Long id, CategoryDTO.UpdateRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("分类不存在"));
        
        if (request.getName() != null) {
            category.setName(request.getName());
        }
        if (request.getDescription() != null) {
            category.setDescription(request.getDescription());
        }
        if (request.getIconUrl() != null) {
            category.setIconUrl(request.getIconUrl());
        }
        if (request.getSortOrder() != null) {
            category.setSortOrder(request.getSortOrder());
        }
        
        return toResponse(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("分类不存在");
        }
        categoryRepository.deleteById(id);
    }

    private CategoryDTO.Response toResponse(Category category) {
        CategoryDTO.Response response = new CategoryDTO.Response();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setIconUrl(category.getIconUrl());
        response.setSortOrder(category.getSortOrder());
        response.setProductCount(category.getProducts() != null ? category.getProducts().size() : 0);
        return response;
    }
}

package com.fresh.ecommerce.controller;

import com.fresh.ecommerce.dto.CategoryDTO;
import com.fresh.ecommerce.service.CategoryService;
import com.fresh.ecommerce.vo.ResultVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResultVO<List<CategoryDTO.Response>> getAllCategories() {
        return ResultVO.success(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResultVO<CategoryDTO.Response> getCategoryById(@PathVariable Long id) {
        return ResultVO.success(categoryService.getCategoryById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResultVO<CategoryDTO.Response> createCategory(@Valid @RequestBody CategoryDTO.CreateRequest request) {
        return ResultVO.success(categoryService.createCategory(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResultVO<CategoryDTO.Response> updateCategory(
            @PathVariable Long id, 
            @Valid @RequestBody CategoryDTO.UpdateRequest request) {
        return ResultVO.success(categoryService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResultVO<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResultVO.success("删除成功");
    }
}

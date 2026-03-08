package com.fresh.ecommerce.controller;

import com.fresh.ecommerce.dto.ProductDTO;
import com.fresh.ecommerce.service.ProductService;
import com.fresh.ecommerce.vo.ResultVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResultVO<Page<ProductDTO.Response>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createTime") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) Long categoryId) {
        
        Sort sort = sortDir.equalsIgnoreCase("asc") ? 
                Sort.by(sortBy).ascending() : 
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<ProductDTO.Response> products;
        if (categoryId != null) {
            products = productService.getProductsByCategory(categoryId, pageable);
        } else {
            products = productService.getProducts(pageable);
        }
        
        return ResultVO.success(products);
    }

    @GetMapping("/search")
    public ResultVO<Page<ProductDTO.Response>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return ResultVO.success(productService.searchProducts(keyword, pageable));
    }

    @GetMapping("/{id}")
    public ResultVO<ProductDTO.Response> getProductById(@PathVariable Long id) {
        return ResultVO.success(productService.getProductById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResultVO<ProductDTO.Response> createProduct(@Valid @RequestBody ProductDTO.CreateRequest request) {
        return ResultVO.success(productService.createProduct(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResultVO<ProductDTO.Response> updateProduct(
            @PathVariable Long id, 
            @Valid @RequestBody ProductDTO.UpdateRequest request) {
        return ResultVO.success(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResultVO<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResultVO.success("删除成功");
    }
}

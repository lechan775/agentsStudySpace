package com.fresh.ecommerce.service;

import com.fresh.ecommerce.dto.ProductDTO;
import com.fresh.ecommerce.entity.Category;
import com.fresh.ecommerce.entity.Product;
import com.fresh.ecommerce.exception.BusinessException;
import com.fresh.ecommerce.exception.ResourceNotFoundException;
import com.fresh.ecommerce.repository.CategoryRepository;
import com.fresh.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Page<ProductDTO.Response> getProducts(Pageable pageable) {
        return productRepository.findByIsActiveTrue(pageable)
                .map(ProductDTO.Response::fromEntity);
    }

    public Page<ProductDTO.Response> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryIdAndIsActiveTrue(categoryId, pageable)
                .map(ProductDTO.Response::fromEntity);
    }

    public Page<ProductDTO.Response> searchProducts(String keyword, Pageable pageable) {
        return productRepository.searchByKeyword(keyword, pageable)
                .map(ProductDTO.Response::fromEntity);
    }

    public ProductDTO.Response getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("商品不存在"));
        return ProductDTO.Response.fromEntity(product);
    }

    @Transactional
    public ProductDTO.Response createProduct(ProductDTO.CreateRequest request) {
        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("分类不存在"));
        }

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .unit(request.getUnit() != null ? request.getUnit() : "份")
                .imageUrl(request.getImageUrl())
                .category(category)
                .productionDate(request.getProductionDate())
                .expiryDate(request.getExpiryDate())
                .isActive(true)
                .salesCount(0)
                .freshness(calculateFreshness(request.getProductionDate(), request.getExpiryDate()))
                .build();

        return ProductDTO.Response.fromEntity(productRepository.save(product));
    }

    @Transactional
    public ProductDTO.Response updateProduct(Long id, ProductDTO.UpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("商品不存在"));

        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getStock() != null) {
            product.setStock(request.getStock());
        }
        if (request.getUnit() != null) {
            product.setUnit(request.getUnit());
        }
        if (request.getImageUrl() != null) {
            product.setImageUrl(request.getImageUrl());
        }
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("分类不存在"));
            product.setCategory(category);
        }
        if (request.getProductionDate() != null) {
            product.setProductionDate(request.getProductionDate());
        }
        if (request.getExpiryDate() != null) {
            product.setExpiryDate(request.getExpiryDate());
        }
        if (request.getIsActive() != null) {
            product.setIsActive(request.getIsActive());
        }

        product.setFreshness(calculateFreshness(product.getProductionDate(), product.getExpiryDate()));

        return ProductDTO.Response.fromEntity(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("商品不存在"));
        product.setIsActive(false);
        productRepository.save(product);
    }

    @Transactional
    public void reduceStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("商品不存在"));
        
        if (product.getStock() < quantity) {
            throw new BusinessException("库存不足");
        }
        
        product.setStock(product.getStock() - quantity);
        product.setSalesCount(product.getSalesCount() + quantity);
        productRepository.save(product);
    }

    public List<Product> getLowStockProducts(Integer threshold) {
        return productRepository.findLowStockProducts(threshold);
    }

    private Product.FreshnessLevel calculateFreshness(LocalDateTime productionDate, LocalDateTime expiryDate) {
        if (productionDate == null || expiryDate == null) {
            return Product.FreshnessLevel.GOOD;
        }
        
        LocalDateTime now = LocalDateTime.now();
        
        if (now.isAfter(expiryDate)) {
            return Product.FreshnessLevel.EXPIRED;
        }
        
        if (now.plusHours(24).isAfter(expiryDate)) {
            return Product.FreshnessLevel.WARNING;
        }
        
        if (now.plusDays(1).isBefore(productionDate)) {
            return Product.FreshnessLevel.FRESH;
        }
        
        return Product.FreshnessLevel.GOOD;
    }
}

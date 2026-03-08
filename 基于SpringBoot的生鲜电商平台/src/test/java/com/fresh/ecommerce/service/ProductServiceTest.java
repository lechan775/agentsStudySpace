package com.fresh.ecommerce.service;

import com.fresh.ecommerce.dto.ProductDTO;
import com.fresh.ecommerce.entity.Category;
import com.fresh.ecommerce.entity.Product;
import com.fresh.ecommerce.exception.ResourceNotFoundException;
import com.fresh.ecommerce.repository.CategoryRepository;
import com.fresh.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Category category;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1L)
                .name("水果")
                .description("新鲜水果")
                .build();

        product = Product.builder()
                .id(1L)
                .name("苹果")
                .description("红富士苹果")
                .price(new BigDecimal("9.90"))
                .stock(100)
                .unit("斤")
                .imageUrl("http://example.com/apple.jpg")
                .category(category)
                .isActive(true)
                .salesCount(0)
                .freshness(Product.FreshnessLevel.GOOD)
                .build();
    }

    @Test
    void getProductById_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDTO.Response response = productService.getProductById(1L);

        assertNotNull(response);
        assertEquals("苹果", response.getName());
        assertEquals(new BigDecimal("9.90"), response.getPrice());
    }

    @Test
    void getProductById_NotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(999L));
    }

    @Test
    void createProduct_Success() {
        ProductDTO.CreateRequest request = new ProductDTO.CreateRequest();
        request.setName("香蕉");
        request.setDescription("海南香蕉");
        request.setPrice(new BigDecimal("5.00"));
        request.setStock(50);
        request.setUnit("把");
        request.setCategoryId(1L);
        request.setProductionDate(LocalDateTime.now());
        request.setExpiryDate(LocalDateTime.now().plusDays(7));

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product p = invocation.getArgument(0);
            p.setId(2L);
            return p;
        });

        ProductDTO.Response response = productService.createProduct(request);

        assertNotNull(response);
        assertEquals("香蕉", response.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_Success() {
        ProductDTO.UpdateRequest request = new ProductDTO.UpdateRequest();
        request.setName("更新后的苹果");
        request.setPrice(new BigDecimal("12.00"));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO.Response response = productService.updateProduct(1L, request);

        assertNotNull(response);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void deleteProduct_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.deleteProduct(1L);

        verify(productRepository).save(any(Product.class));
    }
}

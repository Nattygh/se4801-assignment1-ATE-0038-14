// ID ATE/0038/14

package com.shopwave.service;

import com.shopwave.dto.CreateProductRequest;
import com.shopwave.model.Product;
import com.shopwave.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_success() {

        CreateProductRequest request = new CreateProductRequest(
                "Laptop", "Gaming", BigDecimal.valueOf(1000), 10, 1L
        );

        Product saved = Product.builder()
                .id(1L)
                .name("Laptop")
                .price(BigDecimal.valueOf(1000))
                .stock(10)
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(saved);

        var result = productService.createProduct(request);

        assertNotNull(result);
        assertEquals("Laptop", result.name());
    }

    @Test
    void updateStock_negative_shouldThrow() {

        Product product = Product.builder()
                .id(1L)
                .stock(5)
                .build();

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));

        assertThrows(IllegalArgumentException.class, () ->
                productService.updateStock(1L, -10)
        );
    }
}
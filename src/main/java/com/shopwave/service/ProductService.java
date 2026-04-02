// ID: ATE/0038/14

package com.shopwave.service;

import com.shopwave.dto.CreateProductRequest;
import com.shopwave.dto.ProductDTO;
import com.shopwave.exception.ProductNotFoundException;
import com.shopwave.mapper.ProductMapper;
import com.shopwave.model.Category;
import com.shopwave.model.Product;
import com.shopwave.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // Create Product

    public ProductDTO createProduct(CreateProductRequest request) {

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .category(Category.builder().id(request.categoryId()).build())
                .build();

        Product saved = productRepository.save(product);
        return ProductMapper.toDTO(saved);
    }

    // Get all Products (pagination)

    @Transactional
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductMapper::toDTO);
    }

    // Get Product by ID
    @Transactional
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return ProductMapper.toDTO(product);
    }

    // Search Product
    @Transactional
    public List<ProductDTO> searchProducts(String keyword, BigDecimal maxPrice) {

        List<Product> products;

        if (keyword != null && maxPrice != null) {
            products = productRepository.findByNameContainingIgnoreCase(keyword)
                    .stream()
                    .filter(p -> p.getPrice().compareTo(maxPrice) <= 0)
                    .toList();

        } else if (keyword != null) {
            products = productRepository.findByNameContainingIgnoreCase(keyword);

        } else if (maxPrice != null) {
            products = productRepository.findByPriceLessThanEqual(maxPrice);

        } else {
            products = productRepository.findAll();
        }

        return products.stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    // Update Stock

    @Transactional
    public ProductDTO updateStock(Long id, int delta) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        int newStock = product.getStock() + delta;

        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        product.setStock(newStock);

        return ProductMapper.toDTO(product);
    }
}
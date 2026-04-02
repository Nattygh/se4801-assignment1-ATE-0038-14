//ID ATE/0038/14

package com.shopwave.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopwave.dto.ProductDTO;
import com.shopwave.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    private ProductService productService;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllProducts_shouldReturn200() throws Exception {

        ProductDTO dto = new ProductDTO(
                1L, "Laptop", "Gaming", BigDecimal.valueOf(1000), 10, 1L
        );

        when(productService.getAllProducts(org.springframework.data.domain.PageRequest.of(0,10)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        mockMvc.perform(get("/api/products?page=0&size=10"))
                .andExpect(status().isOk());
    }

    @Test
    void getProduct_notFound_shouldReturn404() throws Exception {

        when(productService.getProductById(999L))
                .thenThrow(new com.shopwave.exception.ProductNotFoundException(999L));

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());
    }
}
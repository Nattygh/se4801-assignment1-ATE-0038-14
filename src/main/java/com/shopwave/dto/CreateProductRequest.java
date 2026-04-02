// ID: ATE/0038/14

package com.shopwave.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateProductRequest(

        @NotBlank String name,
        String description,

        @NotNull @Positive BigDecimal price,

        @NotNull Integer stock,

        @NotNull Long categoryId
) {}
package com.example.RednGreenBE.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {
    private String name;
    @PositiveOrZero
    private BigDecimal min;
    @PositiveOrZero
    private BigDecimal max;

}

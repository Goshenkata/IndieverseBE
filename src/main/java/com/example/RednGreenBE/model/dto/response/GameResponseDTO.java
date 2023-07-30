package com.example.RednGreenBE.model.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameResponseDTO {
    private Long id;
    private String name;
    private String imageUrl;
    private String description;
    private BigDecimal price;
    private String authorUsername;
}


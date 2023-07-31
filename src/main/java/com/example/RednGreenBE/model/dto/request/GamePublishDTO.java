package com.example.RednGreenBE.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GamePublishDTO {
    @NotNull(message = "Game name cannot be null")
    @Size(min = 1, max = 50, message = "Game name must be between 1 and 50 characters")
    private String name;
    @Size(max = 10000, message = "Image url must be less than 10000 characters")
    private String imageUrl;
    @Size(max = 10000, message = "Description cannot be over 10000 characters")
    private String description;
    @PositiveOrZero
    private BigDecimal price;
}

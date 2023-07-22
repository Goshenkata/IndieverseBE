package com.example.RednGreenBE.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressDataDTO {
    @NotNull(message = "Country cannot be null")
    @Size(min = 3, max = 100)
    private String country;
    @NotNull(message = "City cannot be null")
    @Size(min = 1, max = 100)
    private String city;
    @NotNull(message = "City cannot be null")
    @Size(min = 3, max = 100)
    private String street;
}

package com.example.RednGreenBE.model.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data @NoArgsConstructor @ToString @AllArgsConstructor
public class RegistrationDTO implements Serializable {
    @NotNull(message = "username can't be null")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
    private String username;
    @NotNull(message = "username can't be null")
    @Size(min = 3, max = 20, message = "First name must be between 3 and 20 characters long")
    private String firstName;
    @NotNull(message = "username can't be null")
    @Size(min = 3, max = 20, message = "Last name must be between 3 and 20 characters long")
    private String lastName;
    @NotNull(message = "email can't be null")
    @Email(message = "invalid email")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,32}$",
            message = "Password must be between 6 and 32 characters," +
                    "must contain at least 1 capital letter and 1 digit.")
    private String password;

    @Pattern(regexp = "\\+\\d{12}", message = "Phone number invalid, please use the international standard: +359XXXXXXXXX" )
    private String phoneNumber;
    @PositiveOrZero
    private BigDecimal money;
    private AddressDataDTO addressData;

    public BigDecimal getMoney() {
        return money;
    }

    public RegistrationDTO setMoney(BigDecimal money) {
        this.money = money;
        return this;
    }
}

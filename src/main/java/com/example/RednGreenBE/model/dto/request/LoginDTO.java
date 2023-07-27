package com.example.RednGreenBE.model.dto.request;

import jakarta.persistence.Access;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @NotNull(message = "Username can't be null")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
    private String username;
    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,32}$",
            message = "Password must be between 6 and 32 characters," +
                    "must contain at least 1 capital letter and 1 digit.")
    private String password;
}

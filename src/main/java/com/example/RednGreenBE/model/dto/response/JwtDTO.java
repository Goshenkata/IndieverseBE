package com.example.RednGreenBE.model.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class JwtDTO {
    private String token;
    private String type = "Bearer";
    private String username;

    public JwtDTO(String accessToken, String username) {
        this.token = accessToken;
        this.username = username;
    }
}

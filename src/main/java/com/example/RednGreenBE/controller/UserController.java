package com.example.RednGreenBE.controller;

import com.example.RednGreenBE.model.dto.RegistrationDTO;
import com.example.RednGreenBE.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register/userData")
    public ResponseEntity<String> register1(@Valid @RequestBody RegistrationDTO registrationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if (userService.usernameExists(registrationDTO.getUsername())) {
            return ResponseEntity.badRequest().body("Username is taken.");
        }
        if (userService.emailExists(registrationDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Username is taken.");
        }
        return ResponseEntity.ok("This user data is valid");
    }
}

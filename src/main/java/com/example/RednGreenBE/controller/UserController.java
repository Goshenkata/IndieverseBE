package com.example.RednGreenBE.controller;

import com.example.RednGreenBE.model.dto.AddressDataDTO;
import com.example.RednGreenBE.model.dto.RegistrationDTO;
import com.example.RednGreenBE.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register/check")
    public ResponseEntity<String> checkRegister(@Valid @RequestBody RegistrationDTO registrationDTO, BindingResult bindingResult) {
        Optional<ResponseEntity<String>> result = checkIfRegisterDTOvalid(registrationDTO, bindingResult);
        //data is valid
        return result.orElseGet(() -> ResponseEntity.ok("Valid user data"));
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationDTO registrationDTO, BindingResult bindingResult) {
        log.info(String.valueOf(registrationDTO));
        Optional<ResponseEntity<String>> result = checkIfRegisterDTOvalid(registrationDTO, bindingResult);
        //data is invalid
        if (result.isPresent()) {
         return result.get();
        }
        registrationDTO.setPassword(passwordEncoder.encode(passwordEncoder.encode(registrationDTO.getPassword())));
        userService.registerUser(registrationDTO);
        return ResponseEntity.ok("Valid user data");
    }

    private Optional<ResponseEntity<String>> checkIfRegisterDTOvalid(RegistrationDTO registrationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Optional.of(ResponseEntity.badRequest().body(bindingResult.getAllErrors().get(0).getDefaultMessage()));
        }
        if (userService.usernameExists(registrationDTO.getUsername())) {
            return Optional.of(ResponseEntity.badRequest().body("Username is taken."));
        }
        if (userService.emailExists(registrationDTO.getEmail())) {
            return Optional.of(ResponseEntity.badRequest().body("Username is taken."));
        }
        if (userService.phoneNumberExists(registrationDTO.getPhoneNumber())) {
            return Optional.of(ResponseEntity.badRequest().body("This phone number is already in use"));
        }
        return Optional.empty();
    }

}

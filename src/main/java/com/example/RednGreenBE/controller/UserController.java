package com.example.RednGreenBE.controller;

import com.example.RednGreenBE.model.dto.request.LoginDTO;
import com.example.RednGreenBE.model.dto.request.RegistrationDTO;
import com.example.RednGreenBE.model.dto.response.JwtDTO;
import com.example.RednGreenBE.model.dto.response.SimpleMessageDTO;
import com.example.RednGreenBE.service.UserService;
import com.example.RednGreenBE.util.JwtUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/register/check")
    public ResponseEntity<SimpleMessageDTO> checkRegister(@Valid @RequestBody RegistrationDTO registrationDTO, BindingResult bindingResult) {
        Optional<ResponseEntity<SimpleMessageDTO>> result = checkIfRegisterDTOvalid(registrationDTO, bindingResult);
        //data is valid
        return result.orElseGet(() -> ResponseEntity.ok(new SimpleMessageDTO("Valid user data")));
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationDTO registrationDTO, BindingResult bindingResult) {
        log.info(String.valueOf(registrationDTO));
        Optional<ResponseEntity<SimpleMessageDTO>> result = checkIfRegisterDTOvalid(registrationDTO, bindingResult);
        //data is invalid
        if (result.isPresent()) {
         return result.get();
        }
        String oldPassword = registrationDTO.getPassword();
        registrationDTO.setPassword(passwordEncoder.encode(oldPassword));
        userService.registerUser(registrationDTO);
        return ResponseEntity.ok(login(new LoginDTO(registrationDTO.getUsername(), oldPassword)));
    }

    private Optional<ResponseEntity<SimpleMessageDTO>> checkIfRegisterDTOvalid(RegistrationDTO registrationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Optional.of(ResponseEntity.badRequest().body(new SimpleMessageDTO(bindingResult.getAllErrors().get(0).getDefaultMessage())));
        }
        if (userService.usernameExists(registrationDTO.getUsername())) {
            return Optional.of(ResponseEntity.badRequest().body(new SimpleMessageDTO("Username is taken.")));
        }
        if (userService.emailExists(registrationDTO.getEmail())) {
            return Optional.of(ResponseEntity.badRequest().body(new SimpleMessageDTO("Email is taken.")));
        }

        if (userService.phoneNumberExists(registrationDTO.getPhoneNumber())) {
            return Optional.of(ResponseEntity.badRequest().body(new SimpleMessageDTO("This phone number is already in use")));
        }
        return Optional.empty();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(login(loginDTO));
    }

    private JwtDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new JwtDTO(jwt, userDetails.getUsername());
    }

    @GetMapping("/test/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/test/user")
    public String userAccess() {
        return "User Content.";
    }

}

package com.example.RednGreenBE.controller;

import com.example.RednGreenBE.model.dto.request.LoginDTO;
import com.example.RednGreenBE.model.dto.request.RegistrationDTO;
import com.example.RednGreenBE.model.dto.response.JwtDTO;
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
        registrationDTO.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
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

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        log.info("cock1");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        log.info("cock2");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("cock3");
        String jwt = jwtUtils.generateJwtToken(authentication);

        log.info("cock4");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("cock5");
        return ResponseEntity.ok(new JwtDTO(jwt, userDetails.getUsername()));
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

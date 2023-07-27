package com.example.RednGreenBE.service;

import com.example.RednGreenBE.model.dto.request.RegistrationDTO;
import com.example.RednGreenBE.model.entities.AddressData;
import com.example.RednGreenBE.model.entities.UserEntity;
import com.example.RednGreenBE.repositories.AddressDataRepository;
import com.example.RednGreenBE.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.beans.Transient;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final AddressDataRepository addressDataRepository;
    private final ModelMapper modelMapper;

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transient
    public void registerUser(RegistrationDTO registrationDTO) {
        AddressData addressData = modelMapper.map(registrationDTO.getAddressData(), AddressData.class);
        UserEntity userEntity = modelMapper.map(registrationDTO, UserEntity.class);
        userEntity.setAddress(addressData);

        addressDataRepository.save(addressData);
        userRepository.save(userEntity);
    }

    public boolean phoneNumberExists(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }
}

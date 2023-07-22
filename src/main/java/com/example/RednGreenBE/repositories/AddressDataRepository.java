package com.example.RednGreenBE.repositories;

import com.example.RednGreenBE.model.entities.AddressDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDataRepository extends JpaRepository<AddressDataEntity, Long> {
}

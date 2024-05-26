package com.sa1zer.simplebanking.repo;

import com.sa1zer.simplebanking.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneRepo extends JpaRepository<Phone, Long> {

    Optional<Phone> findByPhone(String phone);
}

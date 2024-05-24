package com.sa1zer.simplebanking.repo;

import com.sa1zer.simplebanking.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepo extends JpaRepository<Phone, Long> {
}

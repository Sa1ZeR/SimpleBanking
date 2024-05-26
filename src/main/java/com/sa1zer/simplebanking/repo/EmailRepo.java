package com.sa1zer.simplebanking.repo;

import com.sa1zer.simplebanking.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepo extends JpaRepository<Email, Long> {

    Optional<Email> findByEmail(String email);
}

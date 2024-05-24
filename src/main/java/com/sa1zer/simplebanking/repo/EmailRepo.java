package com.sa1zer.simplebanking.repo;

import com.sa1zer.simplebanking.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepo extends JpaRepository<Email, Long> {
}

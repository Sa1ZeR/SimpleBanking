package com.sa1zer.simplebanking.service;

import com.sa1zer.simplebanking.entity.Email;
import com.sa1zer.simplebanking.repo.EmailRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepo emailRepo;

    @Transactional(readOnly = true)
    public Email findByEmailOrNull(String email) {
        return emailRepo.findByEmail(email).orElse(null);
    }

    public void delete(Email email) {
        emailRepo.delete(email);
    }
}

package com.sa1zer.simplebanking.service;

import com.sa1zer.simplebanking.entity.Phone;
import com.sa1zer.simplebanking.repo.PhoneRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhoneService {

    private final PhoneRepo phoneRepo;

    @Transactional(readOnly = true)
    public Phone findByPhoneOrNull(String phone) {
        return phoneRepo.findByPhone(phone).orElse(null);
    }

    public void delete(Phone phone) {
        phoneRepo.delete(phone);
    }
}

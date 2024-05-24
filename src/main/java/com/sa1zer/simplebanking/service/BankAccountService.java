package com.sa1zer.simplebanking.service;

import com.sa1zer.simplebanking.repo.BankAccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepo repo;

    @Transactional
    public void updateAllBalance() {
        repo.updateAllBalance();
    }
}

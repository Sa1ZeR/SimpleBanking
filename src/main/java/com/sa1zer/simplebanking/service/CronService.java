package com.sa1zer.simplebanking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CronService {

    private final BankAccountService bankAccountService;

    @Scheduled(cron = "0 * * * * *")
    public void updateBalance() {
        bankAccountService.updateAllBalance();

        log.info("Customer's balance successfully increased by 5%");
    }
}

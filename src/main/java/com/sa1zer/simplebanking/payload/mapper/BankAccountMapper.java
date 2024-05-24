package com.sa1zer.simplebanking.payload.mapper;

import com.sa1zer.simplebanking.entity.BankAccount;
import com.sa1zer.simplebanking.payload.dto.BankAccountDto;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper implements Mapper<BankAccount, BankAccountDto> {
    @Override
    public BankAccountDto map(BankAccount from) {
        return BankAccountDto.builder()
                .id(from.getId())
                .balance(from.getBalance())
                .build();
    }
}

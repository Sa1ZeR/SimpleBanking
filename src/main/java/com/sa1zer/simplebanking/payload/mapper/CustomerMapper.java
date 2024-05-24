package com.sa1zer.simplebanking.payload.mapper;

import com.sa1zer.simplebanking.entity.Customer;
import com.sa1zer.simplebanking.entity.Email;
import com.sa1zer.simplebanking.entity.Phone;
import com.sa1zer.simplebanking.payload.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomerMapper implements Mapper<Customer, CustomerDto> {

    private final BankAccountMapper mapper;
    @Override
    public CustomerDto map(Customer from) {
        return CustomerDto.builder()
                .id(from.getId())
                .login(from.getLogin())
                .firstname(from.getFirstname())
                .lastname(from.getLastname())
                .surname(from.getSurname())
                .emails(from.getEmail().stream().map(Email::getEmail).collect(Collectors.toList()))
                .phones(from.getPhones().stream().map(Phone::getPhone).collect(Collectors.toList()))
                .account(mapper.map(from.getAccount()))
                .birthday(from.getBirthday())
                .build();
    }
}

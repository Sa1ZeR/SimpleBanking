package com.sa1zer.simplebanking.facade;

import com.sa1zer.simplebanking.entity.BankAccount;
import com.sa1zer.simplebanking.entity.Customer;
import com.sa1zer.simplebanking.entity.Email;
import com.sa1zer.simplebanking.entity.Phone;
import com.sa1zer.simplebanking.payload.dto.CustomerDto;
import com.sa1zer.simplebanking.payload.mapper.CustomerMapper;
import com.sa1zer.simplebanking.payload.request.CreateCustomerRequest;
import com.sa1zer.simplebanking.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerFacade {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @Transactional
    public CustomerDto createCustomer(CreateCustomerRequest request) {
        Customer byEmail = customerService.findByEmailOrNull(request.email());
        if(byEmail != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Customer with %s email already exists", request.email()));

        Customer byPhone = customerService.findByPhoneOrNull(request.phone());
        if(byPhone != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Customer with %s phone already exists", request.phone()));

        Customer byLogin = customerService.findByLoginOrNull(request.login());
        if(byLogin != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Customer with %s login already exists", request.login()));

        Phone phone = Phone.builder()
                .phone(request.phone())
                .build();

        Email email = Email.builder()
                .email(request.email())
                .build();

        BankAccount bankAccount = BankAccount.builder()
                .balance(request.balance())
                .firstDeposit(request.balance())
                .build();

        Customer customer = Customer.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .surname(request.surname())
                .password(bCryptPasswordEncoder.encode(request.password()))
                .login(request.login())
                .account(bankAccount)
                .email(List.of(email))
                .phones(List.of(phone))
                .birthday(request.birthday())
                .build();

        customer = customerService.save(customer);

        return customerMapper.map(customer);
    }
}

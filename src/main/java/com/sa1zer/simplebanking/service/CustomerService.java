package com.sa1zer.simplebanking.service;

import com.sa1zer.simplebanking.entity.Customer;
import com.sa1zer.simplebanking.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;

    @Transactional(readOnly = true)
    public Customer findByLoginOrNull(String login) {
        return customerRepo.findByLogin(login).orElse(null);
    }

    @Transactional(readOnly = true)
    public Customer findByLoginOrEmail(String login, String email) {
        return customerRepo.findByLoginOrEmail(login, email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Customer with login %s or %s email not found!", login, email)));
    }

    @Transactional(readOnly = true)
    public Customer findByEmail(String email) {
        return customerRepo.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Customer with %s email not found!", email)));
    }

    @Transactional(readOnly = true)
    public Customer findByEmailOrNull(String email) {
        return customerRepo.findByEmail(email).orElse(null);
    }

    @Transactional(readOnly = true)
    public Customer findByPhoneOrNull(String phone) {
        return customerRepo.findByPhone(phone).orElse(null);
    }

    @Transactional
    public Customer save(Customer customer) {
        return customerRepo.save(customer);
    }
}

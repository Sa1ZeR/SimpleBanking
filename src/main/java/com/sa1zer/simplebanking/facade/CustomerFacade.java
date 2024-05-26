package com.sa1zer.simplebanking.facade;

import com.sa1zer.simplebanking.entity.BankAccount;
import com.sa1zer.simplebanking.entity.Customer;
import com.sa1zer.simplebanking.entity.Email;
import com.sa1zer.simplebanking.entity.Phone;
import com.sa1zer.simplebanking.payload.dto.CustomerDto;
import com.sa1zer.simplebanking.payload.mapper.CustomerMapper;
import com.sa1zer.simplebanking.payload.request.CreateCustomerRequest;
import com.sa1zer.simplebanking.payload.request.EditContactsRequest;
import com.sa1zer.simplebanking.payload.request.UpdateContactsRequest;
import com.sa1zer.simplebanking.repo.CustomerRepo;
import com.sa1zer.simplebanking.service.CustomerService;
import com.sa1zer.simplebanking.service.PhoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerFacade {
    private final CustomerRepo customerRepo;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final PhoneService phoneService;

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

    @Transactional
    public CustomerDto removeContacts(UpdateContactsRequest request, Principal principal) {
        if(!StringUtils.hasText(request.phone()) && !StringUtils.hasText(request.email()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Customer customer = customerService.findByLoginOrEmailLock(principal.getName(), principal.getName());

        if(StringUtils.hasText(request.email())) {
            customerService.removeEmail(customer, request.email());
        }

        if(StringUtils.hasText(request.phone())) {
            customerService.removePhone(customer, request.phone());
        }

        customerService.save(customer);
        log.info("Customer Contacts successfully updated for {}", customer.getLogin());

        return customerMapper.map(customer);
    }

    @Transactional
    public CustomerDto addContacts(UpdateContactsRequest request, Principal principal) {
        if(!StringUtils.hasText(request.phone()) && !StringUtils.hasText(request.email()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Customer customer = customerService.findByLoginOrEmailLock(principal.getName(), principal.getName());

        if(StringUtils.hasText(request.phone())) {
            customerService.addPhone(customer, request.phone());
        }

        if(StringUtils.hasText(request.email())) {
            customerService.addEmail(customer, request.email());
        }

        customerService.save(customer);

        log.info("Customer Contacts successfully updated for {}", customer.getLogin());

        return customerMapper.map(customer);
    }

    @Transactional
    public CustomerDto editContacts(EditContactsRequest request, Principal principal) {
        Customer customer = customerService.findByLoginOrEmailLock(principal.getName(), principal.getName());

        if(!ObjectUtils.isEmpty(request.email())) {
            if(!StringUtils.hasText(request.email().newValue()) || !StringUtils.hasText(request.email().oldValue()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email values can't be empty");

            customerService.editEmail(customer, request.email().oldValue(), request.email().newValue());
        }

        if(!ObjectUtils.isEmpty(request.phone())) {
            if(!StringUtils.hasText(request.phone().newValue()) || !StringUtils.hasText(request.phone().oldValue()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "phone values can't be empty");

            customerService.editPhone(customer, request.phone().oldValue(), request.phone().newValue());
        }

        customerRepo.save(customer);
        log.info("Customer Contacts successfully updated for {}", customer.getLogin());

        return customerMapper.map(customer);
    }
}

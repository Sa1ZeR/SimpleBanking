package com.sa1zer.simplebanking.service;

import com.sa1zer.simplebanking.entity.Customer;
import com.sa1zer.simplebanking.entity.Email;
import com.sa1zer.simplebanking.entity.Phone;
import com.sa1zer.simplebanking.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final PhoneService phoneService;
    private final EmailService emailService;

    @Transactional(readOnly = true)
    public Customer findByLoginOrNull(String login) {
        return customerRepo.findByLogin(login).orElse(null);
    }

    @Transactional(readOnly = true)
    public Customer findByLoginOrEmail(String login, String email) {
        return customerRepo.findByLoginOrEmail(login, email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Customer with login %s or %s email not found!", login, email)));
    }

    @Transactional
    public Customer findByLoginOrEmailLock(String login, String email) {
        return customerRepo.findByLoginOrEmailLock(login, email).orElseThrow(() ->
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

    @Transactional
    public void addPhone(Customer customer, String phoneNumber) {
        Phone phone = phoneService.findByPhoneOrNull(phoneNumber);
        if(!ObjectUtils.isEmpty(phone))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Phone %s already used", phoneNumber));

        phone = Phone.builder()
                .phone(phoneNumber)
                .build();
        customer.getPhones().add(phone);

        log.info("Customer {} successfully add {} phone", customer.getLogin(), phone.getPhone());
    }

    @Transactional
    public void addEmail(Customer customer, String email) {
        Email emailEntity = emailService.findByEmailOrNull(email);
        if(!ObjectUtils.isEmpty(emailEntity))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Email %s already used", email));

        emailEntity = Email.builder()
                .email(email)
                .build();
        customer.getEmail().add(emailEntity);

        log.info("Customer {} successfully add {} email", customer.getLogin(), emailEntity.getEmail());
    }

    @Transactional
    public void removeEmail(Customer customer, String email) {
        if (customer.getEmail().size() == 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email data can't be empty");

        Optional<Email> result = customer.getEmail().stream().filter(p -> p.getEmail().equals(email)).findFirst();
        if(result.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You can't do it");

        customer.getEmail().removeIf(e -> e.equals(result.get()));
        emailService.delete(result.get());

        log.info("Email {} successfully deleted for {}", email, customer.getLogin());
    }

    @Transactional
    public void removePhone(Customer customer, String phone) {
        if (customer.getPhones().size() == 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone data can't be empty");

        Optional<Phone> result = customer.getPhones().stream().filter(p -> p.getPhone().equals(phone)).findFirst();
        if(result.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You can't do it");

        customer.getPhones().removeIf(p -> p.equals(result.get()));
        phoneService.delete(result.get());

        log.info("Phone {} successfully deleted for {}", phone, customer.getLogin());
    }

    @Transactional
    public void editEmail(Customer customer, String oldValue, String newValue) {
        Optional<Email> result = customer.getEmail().stream().filter(e -> e.getEmail().equals(oldValue)).findFirst();
        if(result.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You can't do it");

        Email phone = result.get();
        phone.setEmail(newValue);

        log.info("Email {} successfully changed by {} for {}", oldValue, newValue, customer.getLogin());
    }

    @Transactional
    public void editPhone(Customer customer, String oldValue, String newValue) {
        Optional<Phone> result = customer.getPhones().stream().filter(p -> p.getPhone().equals(oldValue)).findFirst();
        if(result.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You can't do it");

        Phone phone = result.get();
        phone.setPhone(newValue);

        log.info("Phone {} successfully changed by {} for {}", oldValue, newValue, customer.getLogin());
    }
}

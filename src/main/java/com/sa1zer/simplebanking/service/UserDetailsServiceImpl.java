package com.sa1zer.simplebanking.service;

import com.sa1zer.simplebanking.entity.Customer;
import com.sa1zer.simplebanking.repo.CustomerRepo;
import com.sa1zer.simplebanking.security.CustomerDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepo customerRepo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepo.findByLoginOrEmail(username, username).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Customer not found with %s email", username)));

        return buildDetails(customer);
    }

    private UserDetails buildDetails(Customer customer) {
        return CustomerDetails.builder()
                .login(customer.getLogin())
                .password(customer.getPassword())
                .build();
    }
}

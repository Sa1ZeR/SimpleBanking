package com.sa1zer.simplebanking.api;

import com.sa1zer.simplebanking.facade.CustomerFacade;
import com.sa1zer.simplebanking.payload.dto.CustomerDto;
import com.sa1zer.simplebanking.payload.request.CreateCustomerRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/manager/")
public class ManagerController {

    private final CustomerFacade customerFacade;

    @PostMapping("create")
    public CustomerDto createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        return customerFacade.createCustomer(request);
    }
}

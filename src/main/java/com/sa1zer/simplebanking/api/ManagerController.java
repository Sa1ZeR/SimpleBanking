package com.sa1zer.simplebanking.api;

import com.sa1zer.simplebanking.facade.CustomerFacade;
import com.sa1zer.simplebanking.payload.dto.CustomerDto;
import com.sa1zer.simplebanking.payload.request.CreateCustomerRequest;
import com.sa1zer.simplebanking.payload.request.CustomerSearchFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/manager/")
public class ManagerController {

    private final CustomerFacade customerFacade;

    @PostMapping("create")
    public CustomerDto createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        return customerFacade.createCustomer(request);
    }

    @GetMapping("search")
    public List<CustomerDto> search(CustomerSearchFilter request) {
        return customerFacade.search(request);
    }
}

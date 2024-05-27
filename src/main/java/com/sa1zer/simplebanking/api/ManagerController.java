package com.sa1zer.simplebanking.api;

import com.sa1zer.simplebanking.facade.CustomerFacade;
import com.sa1zer.simplebanking.payload.dto.CustomerDto;
import com.sa1zer.simplebanking.payload.request.CreateCustomerRequest;
import com.sa1zer.simplebanking.payload.request.CustomerSearchFilter;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/manager/")
public class ManagerController {

    private final CustomerFacade customerFacade;

    @PostMapping("create")
    @Operation(description = "Создание нового пользователя")
    public CustomerDto createCustomer(@Valid @RequestBody @ParameterObject CreateCustomerRequest request) {
        return customerFacade.createCustomer(request);
    }

    @GetMapping("search")
    @Operation(description = "Поиск пользователей с фильтрацией")
    public List<CustomerDto> search(@ParameterObject CustomerSearchFilter request) {
        return customerFacade.search(request);
    }
}

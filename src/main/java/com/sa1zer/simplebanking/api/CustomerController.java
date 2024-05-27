package com.sa1zer.simplebanking.api;

import com.sa1zer.simplebanking.facade.CustomerFacade;
import com.sa1zer.simplebanking.payload.dto.CustomerDto;
import com.sa1zer.simplebanking.payload.request.EditContactsRequest;
import com.sa1zer.simplebanking.payload.request.UpdateContactsRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/customer/")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerFacade customerFacade;

    @Operation(description = "Добавление новых контактов для пользователя")
    @PostMapping("add-contacts")
    public CustomerDto addContacts(@Valid @RequestBody @ParameterObject UpdateContactsRequest request, Principal principal) {
        return customerFacade.addContacts(request, principal);
    }

    @Operation(description = "Редактирование контактов для пользователя")
    @PostMapping("edit-contacts")
    public CustomerDto editContacts(@Valid @RequestBody @ParameterObject EditContactsRequest request, Principal principal) {
        return customerFacade.editContacts(request, principal);
    }

    @Operation(description = "Удаление контактов для пользователя")
    @PostMapping("remove-contacts")
    public CustomerDto removeContacts(@Valid @RequestBody @ParameterObject UpdateContactsRequest request, Principal principal) {
        return customerFacade.removeContacts(request, principal);
    }
}

package com.sa1zer.simplebanking.api;

import com.sa1zer.simplebanking.facade.CustomerFacade;
import com.sa1zer.simplebanking.payload.dto.CustomerDto;
import com.sa1zer.simplebanking.payload.request.EditContactsRequest;
import com.sa1zer.simplebanking.payload.request.UpdateContactsRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/customer/")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerFacade customerFacade;

    @PostMapping("add-contacts")
    public CustomerDto addContacts(@Valid @RequestBody UpdateContactsRequest request, Principal principal) {
        return customerFacade.addContacts(request, principal);
    }

    @PostMapping("edit-contacts")
    public CustomerDto editContacts(@Valid @RequestBody EditContactsRequest request, Principal principal) {
        return customerFacade.editContacts(request, principal);
    }

    @PostMapping("remove-contacts")
    public CustomerDto removeContacts(@Valid @RequestBody UpdateContactsRequest request, Principal principal) {
        return customerFacade.removeContacts(request, principal);
    }
}

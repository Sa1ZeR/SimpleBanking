package com.sa1zer.simplebanking.api;

import com.sa1zer.simplebanking.facade.CustomerFacade;
import com.sa1zer.simplebanking.payload.request.TransferRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/balance/")
@RequiredArgsConstructor
public class BalanceController {

    private final CustomerFacade customerFacade;

    @PostMapping("transfer")
    @Operation(description = "Перевод денежных средств другому пользователю")
    public String transfer(@Valid @RequestBody @ParameterObject TransferRequest request, Principal principal) {
        return customerFacade.transfer(request, principal);
    }
}

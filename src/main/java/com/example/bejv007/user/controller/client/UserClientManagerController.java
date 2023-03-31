package com.example.bejv007.user.controller.client;

import com.example.bejv007.user.exceptions.IdNotFoundException;
import com.example.bejv007.user.request.TransactionRequest;
import com.example.bejv007.user.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/client/users")
@PreAuthorize("hasRole('CLIENT')")
@RequiredArgsConstructor
public class UserClientManagerController {

    private final UserServiceImpl userService;

    @GetMapping(value = "/{id}", params = "currency")
    public BigDecimal getBalance(@PathVariable Long id, @RequestParam String currency) throws IdNotFoundException {
        return userService.getBalance(id, currency);
    }

    @PostMapping("/{id}/btc")
    @ResponseStatus(HttpStatus.OK)
    public void transactBtc(@PathVariable Long id, @RequestBody TransactionRequest request) throws IdNotFoundException {
        userService.transactBtc(id, BigDecimal.valueOf(request.value()));
    }

    @PostMapping("/{id}/brl")
    @ResponseStatus(HttpStatus.OK)
    public void performBrlOperation(@PathVariable Long id, @RequestBody TransactionRequest request) throws IdNotFoundException {
        userService.performBrlOperation(id, BigDecimal.valueOf(request.value()));
    }


}

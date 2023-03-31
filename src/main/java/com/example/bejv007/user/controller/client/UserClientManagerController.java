package com.example.bejv007.user.controller.client;

import com.example.bejv007.user.TransactionRequest;
import com.example.bejv007.user.UserModel;
import com.example.bejv007.user.UserRequest;
import com.example.bejv007.user.UserResponse;
import com.example.bejv007.user.dto.UserDTO;
import com.example.bejv007.user.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public BigDecimal getBalance(@PathVariable Long id, @RequestParam String currency) {
        return userService.getBalance(id, currency);
    }

    @PostMapping("/{id}/btc")
    @ResponseStatus(HttpStatus.OK)
    public void transactBtc(@PathVariable Long id, @RequestBody TransactionRequest request) {
        userService.transactBtc(id, BigDecimal.valueOf(request.value()));
    }

    @PostMapping("/{id}/brl")
    @ResponseStatus(HttpStatus.OK)
    public void performBrlOperation(@PathVariable Long id, @RequestBody TransactionRequest request) {
        userService.performBrlOperation(id, BigDecimal.valueOf(request.value()));
    }


}

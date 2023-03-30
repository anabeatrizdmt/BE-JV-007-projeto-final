package com.example.bejv007.user;

import com.example.bejv007.account.AccountRepository;
import com.example.bejv007.account.AccountService;
import com.example.bejv007.user.dto.UserDTO;
import com.example.bejv007.user.exceptions.EmailDontExistException;
import com.example.bejv007.user.exceptions.IdNotFoundException;
import com.example.bejv007.user.repositories.UserJpaRepository;
import com.example.bejv007.user.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserRequest userRequest) throws Exception {
        return new ResponseEntity<>(userService.saveUserClient(UserDTO.from(userRequest)),HttpStatus.CREATED );
    }









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

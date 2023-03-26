package com.example.bejv007.user;

import com.example.bejv007.account.AccountModel;
import com.example.bejv007.account.AccountRepository;
import com.example.bejv007.account.AccountService;
import com.example.bejv007.user.repositories.UserJpaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserJpaRepository repository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody @Valid UserRequest userRequest) throws Exception {
        Optional<UserModel> optionalUserModel = this.repository.findByEmail(userRequest.getEmail());
        if (optionalUserModel.isPresent()) {
            throw new Exception("E-mail já cadastrado");
        }
        UserModel userModel = this.repository.save(UserModel.from(userRequest));
        accountService.createAccount(userModel);
        return new UserDTO(userModel);
    }

    @GetMapping("/{id}")
    public Optional<UserModel> findById (@PathVariable("id") Long id) {

        return this.repository.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUserById(@PathVariable("id") Long id) throws Exception {
        Optional<UserModel> optionalUserModel = this.repository.findById(id);
        if (optionalUserModel.isEmpty()) {
            throw new Exception("Usuário não encontrado");
        }
        AccountModel accountModel = accountRepository.findByUser(optionalUserModel);

        Long userAccountId = accountService.findAccountIdByUser(optionalUserModel);

        BigDecimal totalBalanceInBtc = accountModel.getBtcBalance();
        BigDecimal totalBalanceInBrl = accountModel.getBrlBalance();
        if (totalBalanceInBtc.compareTo(BigDecimal.ZERO) != 0 || totalBalanceInBrl.compareTo(BigDecimal.ZERO) != 0) {
            throw new Exception("A conta do usuário tem saldos disponível. Por favor, vender e/ou sacar os reais para poder encerrar a conta.");
        }

        accountRepository.delete(accountModel);

        UserModel userModel = optionalUserModel.orElseThrow(() -> new RuntimeException("User not found"));
        this.repository.delete(userModel);
    }
}

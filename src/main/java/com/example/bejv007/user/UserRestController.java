package com.example.bejv007.user;

import com.example.bejv007.account.AccountModel;
import com.example.bejv007.account.AccountRepository;
import com.example.bejv007.account.AccountService;
import com.example.bejv007.user.exceptions.EmailDontExistException;
import com.example.bejv007.user.repositories.UserJpaRepository;
import com.example.bejv007.user.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserServiceImpl userService;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final UserJpaRepository repository;

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserRequest userRequest) throws Exception {
        return new ResponseEntity<>(userService.saveUser(UserDTO.from(userRequest)),HttpStatus.CREATED );
    }

    @GetMapping("/{id}")
    public Optional<UserModel> findById (@PathVariable("id") Long id) {

        return null;
    }

    @GetMapping ("/{email}")
    public UserResponse findByEmail(@PathVariable String email) throws EmailDontExistException {
        var x = UserJpaRepository;
        Optional<UserModel> optionalUserModel = this.repository.findByEmail(email);
        if (optionalUserModel.isPresent()) {
            throw new EmailDontExistException();
        }
        UserResponse userResponse = UserModel.userModelToUserResponse(optionalUserModel.get());
        return userResponse;


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

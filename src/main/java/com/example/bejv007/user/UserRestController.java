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

import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserServiceImpl userService;
    private final UserJpaRepository repository;

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserRequest userRequest) throws Exception {
        return new ResponseEntity<>(userService.saveUser(UserDTO.from(userRequest)),HttpStatus.CREATED );
    }

    @GetMapping("/{id}")
    public UserResponse findById (@PathVariable("id") Long id) throws IdNotFoundException {
        UserDTO userDTO =   userService.findById(id);
        return UserResponse.from(userDTO);
    }

    @GetMapping ("/searchemail/{email}")
    public UserResponse findByEmail(@PathVariable String email) throws EmailDontExistException {
        Optional<UserModel> optionalUserModel = this.repository.findByEmailContaining(email);
        if (!optionalUserModel.isPresent()) {
            throw new EmailDontExistException();
        }
        UserResponse userResponse = UserModel.userModelToUserResponse(optionalUserModel.get());
        return userResponse;
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<UserModel> updateUser(
            @PathVariable("id") Long id,
            @RequestParam("name") Optional<String> nome,
            @RequestParam("email") Optional<String> email,
            @RequestParam("password") Optional<String> password) throws Exception {

        UserDTO userDTO = new UserDTO();
        nome.ifPresent(userDTO::setName);
        email.ifPresent(userDTO::setEmail);
        password.ifPresent(userDTO::setPassword);

        return new ResponseEntity<>(userService.editUser(id, userDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUserById(@PathVariable("id") Long id) throws Exception {
        userService.deleteUser(id);
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

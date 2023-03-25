package com.example.bejv007.user;

import com.example.bejv007.account.AccountModel;
import com.example.bejv007.user.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody @Valid UserRequest userRequest) throws Exception {
        Optional<UserModel> optionalUserModel = this.repository.findByEmail(userRequest.getEmail());
        if (optionalUserModel.isPresent()) {
//            throw new DuplicatedEmailException("E-mail já cadastrado");
            throw new Exception();
        }
        UserModel userModel = this.repository.save(UserModel.from(userRequest));
        return new UserDTO(userModel);
    }

}

package com.example.bejv007.user;

import com.example.bejv007.user.repositories.UserJpaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserJpaRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody @Valid UserRequest userRequest) throws Exception {
        Optional<UserModel> optionalUserModel = this.repository.findByEmail(userRequest.getEmail());
        if (optionalUserModel.isPresent()) {
//            throw new DuplicatedEmailException("E-mail já cadastrado");
            throw new Exception("E-mail já cadastrado");
        }
        UserModel userModel = this.repository.save(UserModel.from(userRequest));
        return new UserDTO(userModel);
    }

    @GetMapping("/{id}")
    public Optional<UserModel> findById (@PathVariable("id") Long id) {

        return this.repository.findById(id);
    }
}

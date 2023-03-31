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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserRequest userRequest) throws Exception {
        return new ResponseEntity<>(userService.saveUserClient(UserDTO.from(userRequest)),HttpStatus.CREATED );
    }

}

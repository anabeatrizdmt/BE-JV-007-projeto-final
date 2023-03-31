package com.example.bejv007.user.controller.admin;

import com.example.bejv007.user.UserModel;
import com.example.bejv007.user.UserRequest;
import com.example.bejv007.user.dto.UserDTO;
import com.example.bejv007.user.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/caminhoSecreto")
@RequiredArgsConstructor
@Log4j2
public class UserAdminRestController {
    private final UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserRequest userRequest) throws Exception {
        return new ResponseEntity<>(userService.saveUserAdmin(UserDTO.from(userRequest)), HttpStatus.CREATED );
    }
}

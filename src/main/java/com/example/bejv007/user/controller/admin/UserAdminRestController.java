package com.example.bejv007.user.controller.admin;

import com.example.bejv007.mapper.UserMapper;
import com.example.bejv007.user.UserModel;
import com.example.bejv007.user.UserResponse;
import com.example.bejv007.user.request.UserRequest;
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
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) throws Exception {
        UserModel userModel = userService.saveUserAdmin(mapper.userRequestToUserDTO(userRequest));
        return new ResponseEntity<>(mapper.userModelToUserResponse(userModel), HttpStatus.CREATED );
    }
}

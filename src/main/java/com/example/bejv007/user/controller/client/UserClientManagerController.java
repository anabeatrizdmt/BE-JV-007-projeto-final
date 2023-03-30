package com.example.bejv007.user.controller.client;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client/users")
@PreAuthorize("hasRole('CLIENT')")
@RequiredArgsConstructor
public class UserClientManagerController {

    private final UserServiceImpl userService;





}

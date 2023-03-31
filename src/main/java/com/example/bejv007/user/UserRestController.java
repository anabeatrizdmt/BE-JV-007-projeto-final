package com.example.bejv007.user;

import com.example.bejv007.mapper.UserMapper;
import com.example.bejv007.user.request.UserRequest;
import com.example.bejv007.user.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UserRestController {

    private final UserServiceImpl userService;
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) throws Exception {

        UserModel userModel = userService.saveUserClient(mapper.userRequestToUserDTO(userRequest));

        return new ResponseEntity<>(mapper.userModelToUserResponse(userModel), HttpStatus.CREATED);
    }

}

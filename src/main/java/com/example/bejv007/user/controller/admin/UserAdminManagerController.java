package com.example.bejv007.user.controller.admin;

import com.example.bejv007.mapper.UserMapper;
import com.example.bejv007.user.UserModel;
import com.example.bejv007.user.UserResponse;
import com.example.bejv007.user.dto.UserDTO;
import com.example.bejv007.user.exceptions.user.EmailDontExistException;
import com.example.bejv007.user.exceptions.user.IdNotFoundException;
import com.example.bejv007.user.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class UserAdminManagerController {

    private final UserMapper mapper;
    private final UserServiceImpl userService;

    @GetMapping ("/searchemail/{email}")
    public UserResponse findByEmail(@PathVariable String email) throws EmailDontExistException {
        Optional<UserModel> optionalUserModel = userService.findByEmailContaining(email);
        if (optionalUserModel.isEmpty()) {
            throw new EmailDontExistException();
        }
        return mapper.userModelToUserResponse(optionalUserModel.get());
    }

    @GetMapping("/{id}")
    public UserResponse findById (@PathVariable("id") Long id) throws IdNotFoundException {
        UserDTO userDTO =   userService.findById(id);
        return mapper.userDtoToUserResponse(userDTO);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<UserModel> updateUser(
            @PathVariable("id") Long id,
            @RequestParam("name") Optional<String> nome,
            @RequestParam("email") Optional<String> email,
            @RequestParam("password") Optional<String> password) throws Exception {

        UserDTO userDTO = new UserDTO();
        nome.ifPresent(userDTO::setUsername);
        email.ifPresent(userDTO::setEmail);
        password.ifPresent(userDTO::setPassword);

        return new ResponseEntity<>(userService.editUser(id, userDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUserById(@PathVariable("id") Long id) throws Exception {
        userService.deleteUser(id);
    }



}

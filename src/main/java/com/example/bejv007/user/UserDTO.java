package com.example.bejv007.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {

    private String name;

    private String email;

    private String password;

    public static UserDTO from(UserRequest userRequest) {
        return new UserDTO(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
    }
    public static UserDTO userModelToUserDto (UserModel userModel){
        return new UserDTO(userModel.getName(), userModel.getEmail(), userModel.getPassword());
    }


}


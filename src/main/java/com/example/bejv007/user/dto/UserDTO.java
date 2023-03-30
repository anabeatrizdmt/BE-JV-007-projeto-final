package com.example.bejv007.user.dto;

import com.example.bejv007.user.UserModel;
import com.example.bejv007.user.UserRequest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {

    private String username;

    private String email;

    private String password;

    public static UserDTO from(UserRequest userRequest) {
        return new UserDTO(userRequest.getUsername(), userRequest.getEmail(), userRequest.getPassword());
    }
    public static UserDTO userModelToUserDto (UserModel userModel){
        return new UserDTO(userModel.getUsername(), userModel.getEmail(), userModel.getPassword());
    }


}


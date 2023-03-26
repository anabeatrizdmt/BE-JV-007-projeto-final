package com.example.bejv007.user;

public record UserDTO(String name, String email) {

    public UserDTO(UserModel userModel) {
        this(userModel.getName() , userModel.getEmail());
    }

}

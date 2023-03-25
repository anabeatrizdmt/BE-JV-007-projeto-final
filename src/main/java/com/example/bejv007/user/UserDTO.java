package com.example.bejv007.user;

public record UserDTO(String username, String email) {

    public UserDTO(UserModel userModel) {
        this(userModel.getName() , userModel.getEmail());
    }

}

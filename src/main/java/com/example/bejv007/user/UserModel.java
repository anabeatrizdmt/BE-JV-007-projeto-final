package com.example.bejv007.user;

import com.example.bejv007.user.entities.RoleEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String password;
    private boolean enabled;
    private boolean tokenExpired;

    public UserModel(String username, String email, String password) {
        this.name = username;
        this.email = email;
        this.password = password;
    }

    public static UserModel from(UserDTO userDTO) {
        return new UserModel(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());
    }

    public static UserResponse userModelToUserResponse(UserModel userModel){
        return new UserResponse(userModel.getName(), userModel.getEmail());
    }

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<RoleEntity> roles;

}

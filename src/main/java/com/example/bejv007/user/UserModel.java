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

    public UserModel(String name, String email, String password) {
    }

    public static UserModel from(UserRequest userRequest) {
        return new UserModel(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
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
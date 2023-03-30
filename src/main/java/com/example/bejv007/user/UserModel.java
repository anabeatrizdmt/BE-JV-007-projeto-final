package com.example.bejv007.user;

import com.example.bejv007.user.dto.UserDTO;
import com.example.bejv007.user.entities.RoleEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import java.util.Collection;



@Entity
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleENUM role;


    public UserModel(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static UserModel from(UserDTO userDTO) {
        return new UserModel(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword());
    }

    public static UserResponse userModelToUserResponse(UserModel userModel){
        return new UserResponse(userModel.getUsername(), userModel.getEmail());
    }

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<RoleEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

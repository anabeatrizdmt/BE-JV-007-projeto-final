package com.example.bejv007.user.entities;

import com.example.bejv007.user.UserModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<UserModel> users;

    public RoleEntity(String name) {
        this.name = name;
    }

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<PrivilegeEntity> privileges;
}

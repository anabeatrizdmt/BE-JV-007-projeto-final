package com.example.bejv007.user.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivilegeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<RoleEntity> roles;

    public PrivilegeEntity(String name) {
        this.name = name;
    }
}

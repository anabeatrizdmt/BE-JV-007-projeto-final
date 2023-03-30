package com.example.bejv007.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserRoleDTO {

    private Long idUser;
    private List<Long> idsRoles;

}

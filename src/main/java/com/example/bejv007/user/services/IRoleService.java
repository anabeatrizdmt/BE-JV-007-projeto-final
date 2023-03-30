package com.example.bejv007.user.services;

import com.example.bejv007.user.UserModel;
import com.example.bejv007.user.dto.UserRoleDTO;
import com.example.bejv007.user.exceptions.IdNotFoundException;

public interface IRoleService {

    UserModel createRole(UserRoleDTO userRoleDTO) throws IdNotFoundException;
}

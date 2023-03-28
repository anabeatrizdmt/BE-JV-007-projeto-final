package com.example.bejv007.user.services.impl;

import com.example.bejv007.user.UserModel;
import com.example.bejv007.user.dto.UserRoleDTO;
import com.example.bejv007.user.entities.RoleEntity;
import com.example.bejv007.user.exceptions.IdNotFoundException;
import com.example.bejv007.user.repositories.UserJpaRepository;
import com.example.bejv007.user.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final UserJpaRepository repository;

    @Override
    public UserModel createRole(UserRoleDTO userRoleDTO) throws IdNotFoundException {
        Optional<UserModel> userExist = repository.findById(userRoleDTO.getIdUser());
        if (userExist.isEmpty()){
            throw new IdNotFoundException();
        }
        List<RoleEntity> roles = userRoleDTO.getIdsRoles().stream().map(role -> new RoleEntity()).toList();
        UserModel userModel = userExist.get();
        userModel.setRoles(roles);

        repository.save(userModel);

        return userModel;
    }
}

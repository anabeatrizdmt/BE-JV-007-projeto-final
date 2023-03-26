package com.example.bejv007.user.services.impl;

import com.example.bejv007.account.AccountService;
import com.example.bejv007.user.UserDTO;
import com.example.bejv007.user.UserModel;
import com.example.bejv007.user.repositories.UserJpaRepository;
import com.example.bejv007.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserJpaRepository repository;
    private final AccountService accountService;
    @Override
    public List<UserModel> findAll() {
        return null;
    }

    @Override
    public Optional<UserModel> findByEmail() {
        return Optional.empty();
    }

    @Override
    public UserModel findById() {
        return null;
    }


    @Override
    public Optional<UserModel> findById(Long id) {

        return null;
    }

    @Override
    public UserModel saveUser(UserDTO userDTO) throws Exception {
        Optional<UserModel> optionalUserModel = repository.findByEmail(userDTO.getEmail());
        if (optionalUserModel.isPresent()) {
            throw new Exception("E-mail já cadastrado");
        }
        UserModel userModel = this.repository.save(UserModel.from(userDTO));
        accountService.createAccount(userModel);
        return userModel;
    }

    @Override
    public UserModel deleteUser() {
        return null;
    }
}

package com.example.bejv007.user.services;

import com.example.bejv007.account.AccountService;
import com.example.bejv007.user.UserDTO;
import com.example.bejv007.user.UserModel;
import com.example.bejv007.user.exceptions.IdNotFoundException;
import com.example.bejv007.user.repositories.UserJpaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
public interface UserService {
    public List<UserModel> findAll();
    public Optional<UserModel> findByEmail();

    public UserDTO findById(Long id) throws IdNotFoundException;


    public UserModel saveUser(UserDTO userDTO) throws Exception;

    public UserModel editUser(Long id, UserDTO userDTO) throws Exception;

    public void deleteUser(Long id) throws Exception;


    BigDecimal getBalance(Long id, String currency);
}

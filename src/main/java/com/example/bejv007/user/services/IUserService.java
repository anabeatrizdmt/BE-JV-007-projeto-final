package com.example.bejv007.user.services;

import com.example.bejv007.user.dto.UserDTO;
import com.example.bejv007.user.UserModel;
import com.example.bejv007.user.exceptions.IdNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
public interface IUserService {
    public List<UserModel> findAll();
    public Optional<UserModel> findByEmailContaining(String email);

    public UserDTO findById(Long id) throws IdNotFoundException;


    public UserModel    saveUserClient(UserDTO userDTO) throws Exception;
    public UserModel saveUserAdmin(UserDTO userDTO) throws Exception;

    public UserModel editUser(Long id, UserDTO userDTO) throws Exception;

    public void deleteUser(Long id) throws Exception;

    BigDecimal getBalance(Long id, String currency);

    void transactBtc(Long id, BigDecimal quantity) throws IdNotFoundException;

    void performBrlOperation(Long id, BigDecimal value) throws IdNotFoundException;
}

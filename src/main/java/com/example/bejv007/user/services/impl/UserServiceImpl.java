package com.example.bejv007.user.services.impl;

import com.example.bejv007.account.AccountModel;
import com.example.bejv007.account.AccountRepository;
import com.example.bejv007.account.AccountService;
import com.example.bejv007.user.UserDTO;
import com.example.bejv007.user.UserModel;
import com.example.bejv007.user.exceptions.IdNotFoundException;
import com.example.bejv007.user.repositories.UserJpaRepository;
import com.example.bejv007.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserJpaRepository repository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public List<UserModel> findAll() {
        return null;
    }

    @Override
    public Optional<UserModel> findByEmail() {
        return Optional.empty();
    }

    @Override
    public UserDTO findById(Long id) throws IdNotFoundException {
       UserModel userFound = repository.findById(id).orElseThrow(IdNotFoundException::new);
       return UserDTO.userModelToUserDto(userFound);
    }



    @Override
    public UserModel saveUser(UserDTO userDTO) throws Exception {
        Optional<UserModel> optionalUserModel = repository.findByEmail(userDTO.getEmail());
        if (optionalUserModel.isPresent()) {
            throw new Exception("E-mail já cadastrado");
        }
        UserModel userModel = this.repository.save(UserModel.from(userDTO));
        userModel.setPassword(passwordEncoder().encode(userModel.getPassword()));
        accountService.createAccount(userModel);
        return userModel;
    }

    @Override
    public UserModel editUser(Long id, UserDTO userDTO) throws Exception {
        Optional<UserModel> optionalUserModel = repository.findById(id);
        if (!optionalUserModel.isPresent()) {
            throw new Exception("Usuário não existe");
        }
        UserModel userModel = optionalUserModel.get();

        userModel.setName(userDTO.getName());
        userModel.setEmail(userDTO.getEmail());
        userModel.setPassword(userDTO.getPassword());

        UserModel UpdatedUserModel = this.repository.save(userModel);

        return UpdatedUserModel;
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        Optional<UserModel> optionalUserModel = this.repository.findById(id);
        if (optionalUserModel.isEmpty()) {
            throw new Exception("Usuário não encontrado");
        }
        AccountModel accountModel = accountRepository.findByUser(optionalUserModel);

        Long userAccountId = accountService.findAccountIdByUser(optionalUserModel);

        BigDecimal totalBalanceInBtc = accountModel.getBtcBalance();
        BigDecimal totalBalanceInBrl = accountModel.getBrlBalance();
        if (totalBalanceInBtc.compareTo(BigDecimal.ZERO) != 0 || totalBalanceInBrl.compareTo(BigDecimal.ZERO) != 0) {
            throw new Exception("A conta do usuário tem saldos disponível. Por favor, vender e/ou sacar os reais para poder encerrar a conta.");
        }

        accountRepository.delete(accountModel);

        UserModel userModel = optionalUserModel.orElseThrow(() -> new RuntimeException("User not found"));
        this.repository.delete(userModel);
    }
}

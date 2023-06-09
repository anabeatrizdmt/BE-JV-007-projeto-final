package com.example.bejv007.user.services.impl;

import com.example.bejv007.account.AccountModel;
import com.example.bejv007.account.AccountRepository;
import com.example.bejv007.account.AccountService;
import com.example.bejv007.mapper.UserMapper;
import com.example.bejv007.user.RoleENUM;
import com.example.bejv007.user.dto.UserDTO;
import com.example.bejv007.user.UserModel;
import com.example.bejv007.user.exceptions.account.CurrencyNotSupportedException;
import com.example.bejv007.user.exceptions.account.InsuffitientFundsException;
import com.example.bejv007.user.exceptions.user.EmailAlreadyExistsException;
import com.example.bejv007.user.exceptions.user.IdNotFoundException;
import com.example.bejv007.user.exceptions.user.UserNotFoundException;
import com.example.bejv007.user.exceptions.user.UsernameAlreadyExistsException;
import com.example.bejv007.user.repositories.UserJpaRepository;
import com.example.bejv007.user.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserJpaRepository repository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    private final UserMapper mapper;

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public List<UserModel> findAll() {
        return null;
    }

    @Override
    public Optional<UserModel> findByEmailContaining(String email) {
        return repository.findByEmailContaining(email);
    }



    @Override
    public UserDTO findById(Long id) throws IdNotFoundException {
       UserModel userFound = repository.findById(id).orElseThrow(IdNotFoundException::new);
       return mapper.userModelToUserDTO(userFound);
    }



    @Override
    public UserModel saveUserClient(UserDTO userDTO) throws Exception {
        Optional<UserModel> optionalUserModelUsername = repository.findByUsername(userDTO.getUsername());
        if (optionalUserModelUsername.isPresent()) {
            throw new UsernameAlreadyExistsException();
        }

        Optional<UserModel> optionalUserModelEmail = repository.findByEmail(userDTO.getEmail());
        if (optionalUserModelEmail.isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        UserModel userModel = this.repository.save(mapper.userDtoToUserModel(userDTO));
        userModel.setPassword(passwordEncoder().encode(userModel.getPassword()));
        userModel.setRole(RoleENUM.CLIENT);
        accountService.createAccount(userModel);
        return userModel;
    }

    @Override
    public UserModel saveUserAdmin(UserDTO userDTO) throws Exception {
        Optional<UserModel> optionalUserModelUsername = repository.findByUsername(userDTO.getUsername());
        if (optionalUserModelUsername.isPresent()) {
            throw new UsernameAlreadyExistsException();
        }

        Optional<UserModel> optionalUserModelEmail = repository.findByEmail(userDTO.getEmail());
        if (optionalUserModelEmail.isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        UserModel userModel = this.repository.save(mapper.userDtoToUserModel(userDTO));
        userModel.setPassword(passwordEncoder().encode(userModel.getPassword()));
        userModel.setRole(RoleENUM.ADMIN);
        accountService.createAccount(userModel);
        return userModel;
    }


    @Override
    public UserModel editUser(Long id, UserDTO userDTO) throws Exception {
        Optional<UserModel> optionalUserModel = repository.findById(id);
        if (optionalUserModel.isEmpty()) {
            throw new UserNotFoundException();
        }
        UserModel userModel = optionalUserModel.get();

        if (!Objects.equals(userModel.getUsername(), userDTO.getUsername()) && userDTO.getUsername() != null) userModel.setUsername(userDTO.getUsername());
        if (!Objects.equals(userModel.getEmail(), userDTO.getEmail()) && userDTO.getEmail() != null) userModel.setEmail(userDTO.getEmail());
        if (!Objects.equals(userModel.getPassword(), userDTO.getPassword()) && userDTO.getPassword() != null) userModel.setPassword(userDTO.getPassword());

        return this.repository.save(userModel);
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        Optional<UserModel> optionalUserModel = this.repository.findById(id);
        if (optionalUserModel.isEmpty()) {
            throw new UserNotFoundException();
        }
        UserModel userModel = optionalUserModel.get();
        AccountModel accountModel = accountRepository.findByUser(userModel);

        Long userAccountId = accountService.findAccountIdByUser(userModel);

        BigDecimal totalBalanceInBtc = accountModel.getBtcBalance();
        BigDecimal totalBalanceInBrl = accountModel.getBrlBalance();
        if (totalBalanceInBtc.compareTo(BigDecimal.ZERO) != 0 || totalBalanceInBrl.compareTo(BigDecimal.ZERO) != 0) {
            throw new Exception("A conta do usuário tem saldos disponível. Por favor, vender e/ou sacar os reais para poder encerrar a conta.");
        }

        accountRepository.delete(accountModel);

        this.repository.delete(userModel);
    }

    @Override
    public BigDecimal getBalance(Long id, String currency) throws IdNotFoundException, CurrencyNotSupportedException {
        Optional<UserModel> user = repository.findById(id);
        Long accountId = accountService.findAccountIdByUser(user.get());

        if (currency.equalsIgnoreCase("btc"))
            return accountService.getTotalBalanceInBtcById(accountId);

        if (currency.equalsIgnoreCase("brl"))
            return accountService.getTotalBalanceInBrlById(accountId);

        throw new CurrencyNotSupportedException();
    }

    @Override
    public void transactBtc(Long id, BigDecimal quantity) throws IdNotFoundException, InsuffitientFundsException {
        Optional<UserModel> user = repository.findById(id);
        Long accountId = accountService.findAccountIdByUser(user.get());
        accountService.transactBtc(accountId, quantity);
    }

    @Override
    public void performBrlOperation(Long id, BigDecimal value) throws IdNotFoundException, InsuffitientFundsException {
        Optional<UserModel> user = repository.findById(id);
        Long accountId = accountService.findAccountIdByUser(user.get());

        accountService.performBrlOperation(accountId, value);

    }
}

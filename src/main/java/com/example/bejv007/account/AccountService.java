package com.example.bejv007.account;

import com.example.bejv007.blockchain.BlockchainService;
import com.example.bejv007.user.UserModel;
import com.example.bejv007.user.exceptions.account.InsuffitientFundsException;
import com.example.bejv007.user.exceptions.user.IdNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class AccountService {
    private final AccountRepository repository;
    private final BlockchainService blockchainService;

    public AccountService(AccountRepository repository, BlockchainService blockchainService) {
        this.repository = repository;
        this.blockchainService = blockchainService;
    }

    public AccountModel createAccount(UserModel newUser) {
        AccountModel newAccount = new AccountModel();
        newAccount.setBrlBalance(BigDecimal.ZERO);
        newAccount.setBtcBalance(BigDecimal.ZERO);
        newAccount.setUser(newUser);
        return repository.save(newAccount);
    }

    public Long findAccountIdByUser(UserModel user) {
        return repository.findByUser(user).getId();
    }

    public AccountModel findAccountByUserModel(UserModel user) {
        return repository.findByUser(user);
    }

    public BigDecimal getTotalBalanceInBtcById(Long id) throws IdNotFoundException {
        AccountModel account = repository.findById(id).orElseThrow(IdNotFoundException::new);

        assert account != null;
        return account.getBtcBalance();
    }

    public BigDecimal getTotalBalanceInBrlById(Long id) {
        AccountModel account = repository.findById(id).orElse(null);

        assert account != null;
        return account.getBrlBalance();
    }

    public Boolean deleteAccountById(Long id) {
        AccountModel account = repository.findById(id).orElse(null);

        assert account != null;
        if (account.getBrlBalance().equals(BigDecimal.ZERO) &&
                account.getBtcBalance().equals(BigDecimal.ZERO)) {

            repository.deleteById(id);
            return true;
        }

        return false;
    }

    public void transactBtc(Long id, BigDecimal value) throws IdNotFoundException, InsuffitientFundsException {
        if (value.scale() > 8 || value.scale() < -8)
            throw new IllegalArgumentException("Não é possível transacionar frações de satoshis.");

        AccountModel account = repository.findById(id).orElseThrow(IdNotFoundException::new);

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            sellBtc(account, value.abs());
            return;
        }

        buyBtc(account, value.abs());
    }

    private void sellBtc(AccountModel account, BigDecimal valueInBtc) throws InsuffitientFundsException {
        if (account.getBtcBalance().compareTo(valueInBtc) < 0) {
            throw new InsuffitientFundsException();
        }
        account.setBtcBalance(account.getBtcBalance().subtract(valueInBtc));
        BigDecimal getBtcQuote = BigDecimal.ONE.divide(blockchainService.getBtcQuote(), 10, RoundingMode.HALF_UP);
        BigDecimal valueInBrl = getBtcQuote.multiply(valueInBtc);
        account.setBrlBalance(account.getBrlBalance().add(valueInBrl));
        repository.save(account);
    }

    private void buyBtc(AccountModel account, BigDecimal valueInBtc) throws InsuffitientFundsException {
        BigDecimal getBtcQuote = BigDecimal.ONE.divide(blockchainService.getBtcQuote(), 10, RoundingMode.HALF_UP);
        BigDecimal valueInBrl = getBtcQuote.multiply(valueInBtc);

        if (account.getBrlBalance().compareTo(valueInBrl) < 0)
            throw new InsuffitientFundsException();

        account.setBrlBalance(account.getBrlBalance().subtract(valueInBrl));
        account.setBtcBalance(account.getBtcBalance().add(valueInBtc));
        repository.save(account);
    }

    public void performBrlOperation(Long id, BigDecimal value) throws IdNotFoundException, InsuffitientFundsException {
        if (value.scale() > 2 || value.scale() < -2)
            throw new IllegalArgumentException("Não é possível transacionar frações de centavos.");

        AccountModel account = repository.findById(id).orElseThrow(IdNotFoundException::new);

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            withdraw(account, value.abs());
            return;
        }

        deposit(account, value.abs());
    }

    private void withdraw(AccountModel account, BigDecimal value) throws InsuffitientFundsException {
        if (account.getBrlBalance().compareTo(value) < 0)
            throw new InsuffitientFundsException();

        account.setBrlBalance(account.getBrlBalance().subtract(value));
        repository.save(account);
    }

    private void deposit(AccountModel account, BigDecimal value) {
        account.setBrlBalance(account.getBrlBalance().add(value));
        repository.save(account);
    }
}

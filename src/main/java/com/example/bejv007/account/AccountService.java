package com.example.bejv007.account;

import com.example.bejv007.blockchain.BlockchainService;
import com.example.bejv007.user.UserModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    public BigDecimal getTotalBalanceInBtcById(Long id) {
        AccountModel account = repository.findById(id).orElse(null);

        BigDecimal btcBalance = account.getBtcBalance();
        BigDecimal brlBalance = account.getBrlBalance();

        BigDecimal convertedBalance = btcBalance.add(blockchainService.getBtcQuote().divide(brlBalance));

        return convertedBalance;
    }

    public BigDecimal getTotalBalanceInBrlById(Long id) {
        AccountModel account = repository.findById(id).orElse(null);

        BigDecimal btcBalance = account.getBtcBalance();
        BigDecimal brlBalance = account.getBrlBalance();

        BigDecimal convertedBalance = brlBalance.add(blockchainService.getBtcQuote().multiply(btcBalance));

        return convertedBalance;
    }

    public Boolean deleteAccountById(Long id) {
        AccountModel account = repository.findById(id).orElse(null);

        if (account.getBrlBalance().equals(BigDecimal.ZERO) &&
            account.getBtcBalance().equals(BigDecimal.ZERO)) {

            repository.deleteById(id);
            return true;
        }

        return false;
    }

    public Boolean transactBtc(Long id, BigDecimal value) {
        if (value.scale() > 8 || value.scale() < -8)
            throw new IllegalArgumentException("Não é possível transacionar frações de satoshis.");

        AccountModel account = repository.findById(id).orElse(null);

        if (value.compareTo(BigDecimal.ZERO) < 0)
            return sellBtc(account, value.abs());

        return buyBtc(account, value.abs());
    }

    private Boolean sellBtc(AccountModel account, BigDecimal valueInBtc) {
        if (account.getBtcBalance().compareTo(valueInBtc) < 0)
            return false;

        account.setBtcBalance(account.getBtcBalance().subtract(valueInBtc));
        BigDecimal valueInBrl = blockchainService.getBtcQuote().multiply(valueInBtc);
        valueInBrl = valueInBrl.setScale(2);
        account.setBrlBalance(account.getBrlBalance().add(valueInBrl));
        return true;
    }

    private Boolean buyBtc(AccountModel account, BigDecimal valueInBtc) {
        BigDecimal valueInBrl = blockchainService.getBtcQuote().multiply(valueInBtc);
        valueInBrl = valueInBrl.setScale(2);

        if (account.getBrlBalance().compareTo(valueInBrl) < 0)
            return false;

        account.setBrlBalance(account.getBrlBalance().subtract(valueInBrl));
        account.setBtcBalance(account.getBtcBalance().add(valueInBtc));
        return true;
    }

    public Boolean performBrlOperation(Long id, BigDecimal value) {
        if (value.scale() > 2 || value.scale() < -2)
            throw new IllegalArgumentException("Não é possível transacionar frações de centavos.");

        AccountModel account = repository.findById(id).orElse(null);

        if (value.compareTo(BigDecimal.ZERO) < 0)
            return withdraw(account, value.abs());

        return deposit(account, value.abs());
    }

    private Boolean withdraw(AccountModel account, BigDecimal value) {
        if (account.getBrlBalance().compareTo(value) < 0)
            return false;

        account.setBrlBalance(account.getBrlBalance().subtract(value));
        return true;
    }

    private Boolean deposit(AccountModel account, BigDecimal value) {
        account.setBrlBalance(account.getBrlBalance().add(value));
        return true;
    }
}

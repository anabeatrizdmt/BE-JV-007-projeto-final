package com.example.bejv007.account;

import com.example.bejv007.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {
<<<<<<< HEAD
    public AccountModel findByUser(UserModel user);
=======
    AccountModel findByUser(UserModel user);

>>>>>>> 449cbe0976234a9a55f2b908ba3b286e10623d82
}

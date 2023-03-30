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
    AccountModel findByUser(UserModel user);

//    @Modifying
//    @Query("UPDATE AccountModel a SET a.brlBalance = a.brlBalance + :brlBalance WHERE a.id = :id_account")
//    void addBrlBalanceToAccount(@Param("id_account") Long id, @Param("brlBalance") BigDecimal brlBalance);
}

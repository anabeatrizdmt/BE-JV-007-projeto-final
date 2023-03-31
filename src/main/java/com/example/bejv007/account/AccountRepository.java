package com.example.bejv007.account;

import com.example.bejv007.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {

    AccountModel findByUser(UserModel user);


}

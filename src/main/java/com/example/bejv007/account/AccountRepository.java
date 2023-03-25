package com.example.bejv007.account;

import com.example.bejv007.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {
    public AccountModel findByUser(UserEntity user);
}

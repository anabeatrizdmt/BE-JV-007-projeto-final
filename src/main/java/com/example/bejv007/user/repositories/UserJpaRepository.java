package com.example.bejv007.user.repositories;

import com.example.bejv007.user.UserModel;
import com.example.bejv007.user.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserModel,Long> {


    Optional<UserModel> findById(Long id);

    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByEmailContaining(String email);

}

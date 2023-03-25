package com.example.bejv007.user.repositories;

import com.example.bejv007.user.entities.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity,Long> {

    PrivilegeEntity findByName (String name);

}

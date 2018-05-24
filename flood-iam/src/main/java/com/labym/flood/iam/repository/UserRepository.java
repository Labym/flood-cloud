package com.labym.flood.iam.repository;

import com.labym.flood.iam.model.po.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserPO, Long> {

    Optional<UserPO> findUserByLogin(String  login);
}

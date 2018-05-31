package com.labym.flood.uc.repository;

import com.labym.flood.uc.model.po.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserPO, Long> {

    Optional<UserPO> findOneUserByLogin(String  login);

    Optional<UserPO> findOneByActivationKey(String key);
}

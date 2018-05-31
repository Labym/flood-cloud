package com.labym.flood.uc.repository;

import com.labym.flood.uc.model.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneUserByLogin(String  login);

    Optional<User> findOneByActivationKey(String key);
}

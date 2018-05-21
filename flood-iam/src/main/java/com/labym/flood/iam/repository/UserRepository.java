package com.labym.flood.iam.repository;

import com.labym.flood.iam.model.po.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserPO, Long> {
}

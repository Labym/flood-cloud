package com.labym.flood.uc.repository;

import com.labym.flood.common.constant.ResourceType;
import com.labym.flood.uc.model.po.ResourcePO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends JpaRepository<ResourcePO,Long> {
    List<ResourcePO> findByType(ResourceType type);

    Optional<ResourcePO> findByName(String name);
}

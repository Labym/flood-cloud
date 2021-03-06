package com.labym.flood.uc.repository;

import com.labym.flood.common.constant.ResourceType;
import com.labym.flood.uc.model.po.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource,Long> {
    List<Resource> findByType(ResourceType type);

    Optional<Resource> findByName(String name);
}

package com.labym.flood.iam.repository;

import com.labym.flood.common.constant.ResourceType;
import com.labym.flood.iam.model.po.ResourcePO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<ResourcePO,Long> {
    List<ResourcePO> findByType(ResourceType type);

}

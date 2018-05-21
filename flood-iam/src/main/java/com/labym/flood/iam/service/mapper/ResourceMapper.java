package com.labym.flood.iam.service.mapper;

import com.labym.flood.common.service.EntityMapper;
import com.labym.flood.iam.model.dto.ResourceDTO;
import com.labym.flood.iam.model.po.ResourcePO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(
    componentModel = "spring"
)
@Service
public interface ResourceMapper extends EntityMapper<ResourceDTO, ResourcePO> {
  ResourcePO toEntity(ResourceDTO resourceDTO);

  ResourceDTO toDto(ResourcePO resource);
}

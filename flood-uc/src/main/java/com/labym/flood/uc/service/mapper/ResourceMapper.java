package com.labym.flood.uc.service.mapper;

import com.labym.flood.common.service.EntityMapper;
import com.labym.flood.uc.model.dto.ResourceDTO;
import com.labym.flood.uc.model.po.Resource;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(
    componentModel = "spring"
)
public interface ResourceMapper extends EntityMapper<ResourceDTO, Resource> {

  @Override
  Resource toEntity(ResourceDTO dto);

  @Override
  ResourceDTO toDto(Resource entity);
}

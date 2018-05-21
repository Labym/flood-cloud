package com.labym.flood.iam.service.mapper;

import com.labym.flood.common.service.EntityMapper;
import com.labym.flood.iam.model.dto.UserDTO;
import com.labym.flood.iam.model.po.UserPO;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring"
)
public interface UserMapper extends EntityMapper<UserDTO, UserPO> {
  UserPO toEntity(UserDTO userDTO);

  UserDTO toDto(UserPO user);
}

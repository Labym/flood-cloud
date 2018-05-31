package com.labym.flood.uc.service.mapper;

import com.labym.flood.common.service.EntityMapper;
import com.labym.flood.uc.model.dto.UserDTO;
import com.labym.flood.uc.model.po.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(
    componentModel = "spring"
)
public interface UserMapper extends EntityMapper<UserDTO, User> {
  User toEntity(UserDTO userDTO);

  UserDTO toDto(User user);
}

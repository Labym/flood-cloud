package com.labym.flood.iam.service;

import com.labym.flood.common.service.BaseService;
import com.labym.flood.iam.model.dto.UserDTO;
import com.labym.flood.iam.model.po.UserPO;

import java.util.Optional;

public interface UserService extends BaseService<UserPO,UserDTO,Long> {

    UserDTO register(String login,String password);

    boolean loginNameExist(String login);

    Optional<UserDTO> findByLogin(String name);
}

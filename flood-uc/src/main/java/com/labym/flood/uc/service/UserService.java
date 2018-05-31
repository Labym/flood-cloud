package com.labym.flood.uc.service;

import com.labym.flood.common.service.BaseService;
import com.labym.flood.uc.model.dto.UserDTO;
import com.labym.flood.uc.model.po.User;

import java.util.Optional;

public interface UserService extends BaseService<User,UserDTO,Long> {

    UserDTO register(String login,String password);

    boolean loginNameExist(String login);

    Optional<UserDTO> findByLogin(String name);

    Optional<User> activateRegistration(String key);
}

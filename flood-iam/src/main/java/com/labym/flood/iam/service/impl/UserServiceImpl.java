package com.labym.flood.iam.service.impl;

import com.labym.flood.common.service.impl.BaseServiceImpl;
import com.labym.flood.iam.model.dto.UserDTO;
import com.labym.flood.iam.model.po.UserPO;
import com.labym.flood.iam.repository.UserRepository;
import com.labym.flood.iam.service.UserService;
import com.labym.flood.iam.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserPO,UserDTO,Long> implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        super(userRepository, userMapper);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
}

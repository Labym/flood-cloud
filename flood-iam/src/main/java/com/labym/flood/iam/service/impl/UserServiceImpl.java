package com.labym.flood.iam.service.impl;

import com.labym.flood.common.constant.Language;
import com.labym.flood.common.exception.FloodErrorUtils;
import com.labym.flood.common.service.impl.BaseServiceImpl;
import com.labym.flood.iam.exception.UserAlreadyExistException;
import com.labym.flood.iam.model.dto.UserDTO;
import com.labym.flood.iam.model.po.UserPO;
import com.labym.flood.iam.repository.UserRepository;
import com.labym.flood.iam.service.UserService;
import com.labym.flood.iam.service.mapper.UserMapper;
import com.labym.flood.iam.util.UserUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserPO,UserDTO,Long> implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,PasswordEncoder passwordEncoder) {
        super(userRepository, userMapper);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder=passwordEncoder;
    }


    @Override
    public UserDTO register(String email, String password) {
        if(loginNameExist(email)){
            throw new UserAlreadyExistException(FloodErrorUtils.alreadyExists("email(%s) already exist",email));
        }
        UserPO user=new UserPO();
        user.setLogin(email);
        user.setEmail(email);
        user.setSalt(UUID.randomUUID().toString());
        password=password+user.getSalt();
        user.setPassword(passwordEncoder.encode(password));
        user.setActivated(false);
        user.setActivationKey(UUID.randomUUID().toString());
        user.setLangKey(Language.ZH_CN);
        user.setExpireAt(UserUtils.NEVER_EXPIRED);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public boolean loginNameExist(String login) {
        return false;
    }

    @Override
    public Optional<UserDTO> findByLogin(String name) {
        return Optional.empty();
    }
}

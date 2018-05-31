package com.labym.flood.uc.service.impl;

import com.labym.flood.common.constant.Language;
import com.labym.flood.common.exception.FloodErrorUtils;
import com.labym.flood.common.service.impl.BaseServiceImpl;
import com.labym.flood.uc.exception.UserAlreadyExistException;
import com.labym.flood.uc.model.dto.UserDTO;
import com.labym.flood.uc.model.po.User;
import com.labym.flood.uc.repository.UserRepository;
import com.labym.flood.uc.service.UserService;
import com.labym.flood.uc.service.mapper.UserMapper;
import com.labym.flood.uc.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserDTO, Long> implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        super(userRepository, userMapper);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDTO register(String email, String password) {
        if (loginNameExist(email)) {
            throw new UserAlreadyExistException(FloodErrorUtils.alreadyExists("email(%s) already exist", email));
        }
        User user = new User();
        user.setLogin(email);
        user.setEmail(email);
        user.setSalt(UUID.randomUUID().toString());
        password = password + user.getSalt();
        user.setPassword(passwordEncoder.encode(password));
        user.setActivated(false);
        user.setActivationKey(UUID.randomUUID().toString().replace("-", ""));
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

    @Override
    public Optional<User> activateRegistration(String key) {

        return userRepository.findOneByActivationKey(key).map(user -> {
            // activate given user for the registration key.
            user.setActivated(true);
            user.setActivationKey(null);
            log.debug("Activated user: {}", user);
            return user;
        });

    }
}

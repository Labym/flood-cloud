package com.labym.flood.iam.service.impl;

import com.labym.flood.iam.repository.UserRepository;
import com.labym.flood.iam.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
      this.userRepository = userRepository;
  }
}

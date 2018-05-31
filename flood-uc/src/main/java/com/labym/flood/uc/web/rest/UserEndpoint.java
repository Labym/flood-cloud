package com.labym.flood.uc.web.rest;

import  org.springframework.web.bind.annotation.RestController;
import com.labym.flood.uc.service.UserService;

@RestController
public class UserEndpoint {
  private final UserService userService;

  public UserEndpoint(UserService userService) {
      this.userService = userService;
  }

}

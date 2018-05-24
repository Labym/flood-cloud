package com.labym.flood.iam.web.rest;

import  org.springframework.web.bind.annotation.RestController;
import com.labym.flood.iam.service.UserService;

@RestController
public class UserEndpoint {
  private final UserService userService;

  public UserEndpoint(UserService userService) {
      this.userService = userService;
  }

}

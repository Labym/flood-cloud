package com.labym.flood.iam.web.rest;

import  org.springframework.web.bind.annotation.RestController;
import com.labym.flood.iam.service.ResourceService;

@RestController
public class ResourceEndpoint {
  private final ResourceService resourceService;

  public ResourceEndpoint(ResourceService resourceService) {
      this.resourceService = resourceService;
  }
}

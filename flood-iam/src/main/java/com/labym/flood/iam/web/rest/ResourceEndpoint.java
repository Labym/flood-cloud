package com.labym.flood.iam.web.rest;

import com.labym.flood.iam.model.dto.ResourceDTO;
import com.labym.flood.iam.service.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/resources")
public class ResourceEndpoint {
    private final ResourceService resourceService;

    public ResourceEndpoint(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/menus")
    public ResponseEntity menus() {
        return ResponseEntity.ok(resourceService.findMenusTree());
    }

    @PostMapping("/menus")
    public ResponseEntity createResource(ResourceDTO resourceDTO ){
        resourceService.create(resourceDTO);
        return ResponseEntity.created(URI.create("")).build();
    }


}

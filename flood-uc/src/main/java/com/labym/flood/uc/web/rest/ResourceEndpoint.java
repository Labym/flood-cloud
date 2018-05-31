package com.labym.flood.uc.web.rest;

import com.labym.flood.uc.model.dto.ResourceDTO;
import com.labym.flood.uc.service.ResourceService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/resources")
public class ResourceEndpoint {
    private final ResourceService resourceService;

    public ResourceEndpoint(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
    @GetMapping("/menus")
    public ResponseEntity menus() {
        return ResponseEntity.ok(resourceService.findMenusTree());
    }

    @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
    @PostMapping("/menus")
    public ResponseEntity createResource(@RequestBody ResourceDTO resourceDTO ){
        resourceService.create(resourceDTO);
        return ResponseEntity.created(URI.create("")).build();
    }

    @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
    @PostMapping("/menus")
    public ResponseEntity updateResource(@RequestBody ResourceDTO resourceDTO ){
        resourceService.create(resourceDTO);
        return ResponseEntity.created(URI.create("")).build();
    }


}

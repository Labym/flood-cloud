package com.labym.flood.iam.service.impl;

import com.labym.flood.common.service.impl.BaseServiceImpl;
import com.labym.flood.iam.model.dto.ResourceDTO;
import com.labym.flood.iam.model.po.ResourcePO;
import com.labym.flood.iam.repository.ResourceRepository;
import com.labym.flood.iam.service.ResourceService;
import com.labym.flood.iam.service.mapper.ResourceMapper;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl extends BaseServiceImpl<ResourcePO, ResourceDTO, Long> implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;

    public ResourceServiceImpl(ResourceRepository resourceRepository, ResourceMapper resourceMapper) {
        super(resourceRepository, resourceMapper);
        this.resourceRepository = resourceRepository;
        this.resourceMapper = resourceMapper;
    }
}

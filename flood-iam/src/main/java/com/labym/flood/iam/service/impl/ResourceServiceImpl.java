package com.labym.flood.iam.service.impl;

import com.labym.flood.common.constant.ResourceType;
import com.labym.flood.common.exception.FloodErrorUtils;
import com.labym.flood.common.exception.FloodException;
import com.labym.flood.common.security.SecurityUtil;
import com.labym.flood.common.service.impl.BaseServiceImpl;
import com.labym.flood.common.util.tree.Tree;
import com.labym.flood.iam.model.dto.ResourceDTO;
import com.labym.flood.iam.model.po.ResourcePO;
import com.labym.flood.iam.repository.ResourceRepository;
import com.labym.flood.iam.service.ResourceService;
import com.labym.flood.iam.service.mapper.ResourceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
public class ResourceServiceImpl extends BaseServiceImpl<ResourcePO, ResourceDTO, Long> implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;

    public ResourceServiceImpl(ResourceRepository resourceRepository, ResourceMapper resourceMapper) {
        super(resourceRepository, resourceMapper);
        this.resourceRepository = resourceRepository;
        this.resourceMapper = resourceMapper;
    }

    @Override
    public Tree<ResourceDTO, Long> findMenusTree() {
        List<ResourcePO> resources = resourceRepository.findByType(ResourceType.MENU);
        List<ResourceDTO> menus = resourceMapper.toDto(resources);
        return new Tree<>(menus);
    }

    @Override
    public void create(ResourceDTO resourceDTO) {
        if (resourceRepository.findByName(resourceDTO.getName()).isPresent()) {
            throw new FloodException(FloodErrorUtils.alreadyExists("resource name already exist"));
        }
        ResourcePO resourcePO = resourceMapper.toEntity(resourceDTO);
        resourcePO.setCreateAt(Instant.now());
        resourcePO.setCreateBy(SecurityUtil.currentUser());
        resourceRepository.save(resourcePO);
        log.info("created resource {}",resourcePO);
    }


}

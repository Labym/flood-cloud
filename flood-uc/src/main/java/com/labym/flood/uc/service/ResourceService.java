package com.labym.flood.uc.service;

import com.labym.flood.common.service.BaseService;
import com.labym.flood.common.util.tree.Tree;
import com.labym.flood.uc.model.dto.ResourceDTO;
import com.labym.flood.uc.model.po.Resource;

public interface ResourceService extends BaseService<Resource,ResourceDTO,Long> {

    Tree<ResourceDTO,Long> findMenusTree();
    void create(ResourceDTO resourceDTO);
    void update(ResourceDTO resourceDTO);

}

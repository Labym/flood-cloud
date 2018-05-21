package com.labym.flood.iam.service;

import com.labym.flood.common.service.BaseService;
import com.labym.flood.common.util.tree.Tree;
import com.labym.flood.iam.model.dto.ResourceDTO;
import com.labym.flood.iam.model.po.ResourcePO;

public interface ResourceService extends BaseService<ResourcePO,ResourceDTO,Long> {

        Tree<ResourceDTO,Long> findMenusTree();

    void create(ResourceDTO resourceDTO);

}

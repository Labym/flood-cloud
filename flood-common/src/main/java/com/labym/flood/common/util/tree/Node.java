package com.labym.flood.common.util.tree;

import java.io.Serializable;

public interface Node<ID extends Serializable> {

    ID id();
    ID parentId();
    boolean isRoot();
}

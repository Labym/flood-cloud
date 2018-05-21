package com.labym.flood.common.util.tree;

import lombok.Getter;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

public class Tree<T extends Node<ID>,ID extends Serializable> {

    @Getter
    private List<TreeNode<T,ID>> treeNodes= Lists.newArrayList();

    public Tree(){

    }

    public Tree(final List<T> nodes){
        init(nodes);
    }

    private void initRoot(T node,List<T> nodes) {
        if (null==node.parentId()) {
            TreeNode<T,ID> rootNode = new TreeNode<>(null, node);
            treeNodes.add(rootNode);
            initChildren(rootNode,nodes);
        }
    }

    private void initChildren(final  TreeNode<T,ID> parent,final List<T> nodes){
        nodes.forEach(node-> initChild(parent, nodes, node));
    }

    private void initChild(TreeNode<T, ID> parent, List<T> nodes, T node) {
        if(null==node.parentId()){
            return;
        }
        if(parent.id().equals(node.parentId())){
            TreeNode<T, ID> child = new TreeNode<>(parent, node);
            parent.add(child);
            initChildren(child,nodes);
        }
    }

    public void init(final List<T> nodes){
        nodes.forEach(node-> initRoot(node,nodes));
    }

    @Override
    public String toString() {
        return "Tree{" +
                "treeNodes=" + treeNodes +
                '}';
    }
}

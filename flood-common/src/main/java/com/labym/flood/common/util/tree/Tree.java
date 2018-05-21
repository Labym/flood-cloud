package com.labym.flood.common.util.tree;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

public class Tree<T extends Node<ID>,ID extends Serializable> {
    @Getter
    private TreeNode<T,ID> root;

    public Tree(){

    }

    public Tree(final List<T> nodes){
        init(nodes);
    }

    private void initRoot(T node,List<T> nodes) {
        if (node.isRoot()) {
            root = new TreeNode<>(null, node);
            initChildren(root,nodes);
        }
    }

    private void initChildren(final  TreeNode<T,ID> parent,final List<T> nodes){
        nodes.forEach(node-> initChild(parent, nodes, node));
    }

    private void initChild(TreeNode<T, ID> parent, List<T> nodes, T node) {
        if(node.isRoot()){
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
                "root=" + root +
                '}';
    }

}

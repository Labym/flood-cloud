package com.labym.flood.common.util.tree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TreeNode<T extends Node<ID>,ID extends Serializable>  {
    private T data;
    private List<TreeNode<T,ID>> children;
    @JsonIgnore
    private TreeNode<T,ID> parent;

    public void  add(TreeNode<T,ID> child){
        if(null==children){
            children=new ArrayList<>();
        }
        children.add(child);
    }


    public ID id() {
        return this.data.id();
    }


    public ID parentId() {
        if(isRoot()){
            return null;
        }
        return this.parent.id();
    }


    public boolean isRoot() {
        return null==parent;
    }

    public TreeNode(TreeNode<T,ID> parent, T value){
        this.parent=parent;
        this.data=value;
    }


    public String prettyPrint() {
        return "TreeNode{" +
                "value=" + data +
                ", children=" + children +
                ", parent=" + parent +
                '}';
    }
}

package com.lynjava.ddd.test.algorithm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 二叉树节点
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {
    public Integer val;

    public TreeNode left;

    public TreeNode right;

    public TreeNode(Integer val) {
        this.val = val;
    }

    public void addLeft(Integer val) {
        this.left = new TreeNode(val);
    }

    public void addRight(Integer val) {
        this.right = new TreeNode(val);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof TreeNode)){
            return false;
        }
        return this.val.equals(((TreeNode)obj).val);
    }
    @Override
    public int hashCode() {
        return this.val.hashCode();
    }
}
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
public class BTreeNode<T> {
    private T value;

    private BTreeNode<T> left;

    private BTreeNode<T> right;

    public BTreeNode(T value) {
        this.value = value;
    }

    public void addLeft(T value) {
        BTreeNode<T> left = new BTreeNode<>(value);
        this.left = left;
    }
    public void addRight(T value) {
        BTreeNode<T> right = new BTreeNode<>(value);
        this.right = right;
    }
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof BTreeNode)){
            return false;
        }
        return this.value.equals(((BTreeNode<?>)obj).value);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}
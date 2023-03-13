package com.lynjava.ddd.test.algorithm;

import com.lynjava.ddd.test.algorithm.model.BTreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 树的基本操作
 */
public class TreeOperateDemo {
    // 求树中节点个数
    public static <T> int getTreeNum(BTreeNode<T> root) {
        if (root == null) {
            return 0;
        }
        return getTreeNum(root.getLeft()) + getTreeNum(root.getRight()) + 1;
    }
    // 求第k层节点个数
    public static <T> int getNumForKlevel(BTreeNode<T> root, int k) {
        if (root == null || k < 1) {
            return 0;
        }
        if (k == 1) {
            return 1;
        }
        int leftNum = getNumForKlevel(root.getLeft(), k - 1);
        int rightNum = getNumForKlevel(root.getRight(), k - 1);
        return leftNum + rightNum;
    }
    // 求叶子节点个数
    public static <T> int getLeafNum(BTreeNode<T> root) {
        if (root == null) {
            return 0;
        }
        if (root.getLeft() == null && root.getRight() == null) {
            return 1;
        }
        int leftNum = getLeafNum(root.getLeft());
        int rightNum = getLeafNum(root.getRight());
        return leftNum + rightNum;
    }
    // 判断是否树的节点
    public static <T> boolean nodeIsChild(BTreeNode<T> root, BTreeNode<T> node) {
        if (root == null || node == null) {
            return false;
        }
        if (root == node) {
            return true;
        }
        boolean isFind = nodeIsChild(root.getLeft(), node);
        if (!isFind) {
            isFind = nodeIsChild(root.getRight(), node);
        }
        return isFind;
    }
    // 求树的深度
    public static <T> int getTreeDepth(BTreeNode<T> root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = getTreeDepth(root.getLeft()) + 1;
        int rightDepth = getTreeDepth(root.getRight()) + 1;
        return Math.max(leftDepth, rightDepth);
    }
    // 先序遍历
    public static <T> void preOrderTravel(BTreeNode<T> root) {
        if (root == null) {
            return;
        }
        System.out.println(root);
        preOrderTravel(root.getLeft());
        preOrderTravel(root.getRight());
    }
    // 中序遍历
    public static <T> void midOrderTravel(BTreeNode<T> root) {
        if (root == null) {
            return;
        }
        midOrderTravel(root.getLeft());
        System.out.println(root);
        midOrderTravel(root.getRight());
    }
    // 后续遍历
    public static <T> void backOrderTravel(BTreeNode<T> root) {
        if (root == null) {
            return;
        }
        backOrderTravel(root.getLeft());
        backOrderTravel(root.getRight());
        System.out.println(root);
    }
    // 层序遍历
    public static <T> void levelTravel(BTreeNode<T> root) {
        Queue<BTreeNode<T>> q = new LinkedList<BTreeNode<T>>();
        q.offer(root);
        while (!q.isEmpty()) {
            BTreeNode<T> temp = q.poll();
            System.out.println(temp);
            if (temp.getLeft() != null) {
                q.offer(temp.getLeft());
            }
            if (temp.getRight() != null) {
                q.offer(temp.getRight());
            }
        }
    }
    // 交换左右子树
    public static <T> BTreeNode<T> exchange(BTreeNode<T> root) {
        if (root == null) {
            return null;
        }
        BTreeNode<T> left = exchange(root.getLeft());
        BTreeNode<T> right = exchange(root.getRight());
        root.setLeft(right);
        root.setRight(left);
        return root;
    }
    // 寻找两节点的公共父节点
    public static <T> BTreeNode<T> findAllFatherNode(BTreeNode<T> root,
                                                     BTreeNode<T> lNode, BTreeNode<T> rNode) {
        if (lNode == root || rNode == root) {
            return root;
        }
        if (root == null || lNode == null || rNode == null) {
            return null;
        }
        // 如果lNode是左子树的节点
        if (nodeIsChild(root.getLeft(), lNode)) {
            if (nodeIsChild(root.getRight(), rNode)) {
                return root;
            } else {
                return findAllFatherNode(root.getLeft(), lNode, rNode);
            }
        } else {
            if (nodeIsChild(root.getLeft(), rNode)) {
                return root;
            } else {
                return findAllFatherNode(root.getRight(), lNode, rNode);
            }
        }
    }
    //根据前序和中序重建二叉树
    public static <T> BTreeNode<T> getTreeFromPreAndMid(List<T> pre, List<T> mid) {
        if (pre == null || mid == null || pre.size() == 0 || mid.size() == 0) {
            return null;
        }
        if (pre.size() == 1) {
            return new BTreeNode<T>(pre.get(0));
        }
        BTreeNode<T> root = new BTreeNode<T>(pre.get(0));
        // 找出根节点在中序中的位置
        int index = 0;
        while (!mid.get(index++).equals(pre.get(0))) {
        }
        // 构建左子树的前序和中序
        List<T> preLeft = new ArrayList<T>(index);
        List<T> midLeft = new ArrayList<T>(index);
        for (int i = 1; i < index; i++) {
            preLeft.add(pre.get(i));
        }
        for (int i = 0; i < index - 1; i++) {
            midLeft.add(mid.get(i));
        }
        // 重建左子树
        root.setLeft(getTreeFromPreAndMid(preLeft, midLeft));
        // 构建右子树的前序和中序
        List<T> preRight = new ArrayList<T>(pre.size() - index - 1);
        List<T> midRight = new ArrayList<T>(pre.size() - index - 1);
        for (int i = 0; i <= pre.size() - index - 1; i++) {
            preRight.add(pre.get(index + i));
        }
        for (int i = 0; i <= pre.size() - index - 1; i++) {
            midRight.add(mid.get(index + i));
        }
        // 重建右子树
        root.setRight(getTreeFromPreAndMid(preRight, midRight));
        return root;
    }
    // 比较两棵树是否相等
    public static <T> boolean equals(BTreeNode<T> node1, BTreeNode<T> node2) {
        if (node1 == null && node2 == null) {
            return true;
        } else if (node1 == null || node2 == null) {
            return false;
        }
        boolean isEqual = node1.getValue().equals(node2.getValue());
        boolean isLeftEqual = equals(node1.getLeft(), node2.getLeft());
        boolean isRightEqual = equals(node1.getRight(), node2.getRight());
        return isEqual && isLeftEqual && isRightEqual;
    }

    public static void main(String[] args) {
        BTreeNode<Integer> t = new BTreeNode<Integer>(1);
        t.addLeft(2);
        t.addRight(3);
        t.getLeft().addLeft(4);
        t.getLeft().addRight(5);
        System.out.println("中序遍历测试:");
        midOrderTravel(t);
        System.out.println("\n前序遍历测试:");
        preOrderTravel(t);
        System.out.println("\n后序遍历测试:");
        backOrderTravel(t);
        System.out.println("\n层次遍历测试:");
        levelTravel(t);
        System.out.println("\n树的深度:"+getTreeDepth(t));
        System.out.println("树的叶子个数:"+getLeafNum(t));
        System.out.println("树的节点个数:"+getTreeNum(t));
        System.out.println("第2层节点个数为:"+getNumForKlevel(t,2));
        List<Integer> pre = new ArrayList<Integer>();
        pre.add(1);
        pre.add(2);
        pre.add(4);
        pre.add(5);
        pre.add(3);
        List<Integer> mid = new ArrayList<Integer>();
        mid.add(4);
        mid.add(2);
        mid.add(5);
        mid.add(1);
        mid.add(3);
        BTreeNode<Integer> root = getTreeFromPreAndMid(pre, mid);
        System.out.println("\n通过前序和中序构建树测试：");
        levelTravel(root);
        System.out.println("\n构建的树比较测试:");
        System.out.println(equals(t,root));
    }
}


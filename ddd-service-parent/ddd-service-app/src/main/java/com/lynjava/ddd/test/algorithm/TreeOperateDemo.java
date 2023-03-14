package com.lynjava.ddd.test.algorithm;

import com.lynjava.ddd.test.algorithm.model.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 树的基本操作
 */
public class TreeOperateDemo {
    /**
     * 先序遍历: 递归
     * @param root
     */
    public static void preTravelRec(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }
        list.add(root);
        preTravelRec(root.left, list);
        preTravelRec(root.right, list);
    }

    /**
     * TODO 先序遍历: 栈 迭代
     * @param root
     */
    public static void preTravelStk(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }
    }

    /**
     * 中序遍历：递归
     * @param root
     * @param data
     */
    public static void midTravelRec(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }
        midTravelRec(root.left, list);
        list.add(root);
        midTravelRec(root.right, list);
    }

    /**
     * TODO 中序遍历：栈 迭代
     * @param root
     * @param data
     */
    public static void midTravelStk(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }
    }

    /**
     * 后序遍历：递归
     *
     * @param root
     * @param data
     */
    public static void postTravelRec(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }
        postTravelRec(root.left, list);
        postTravelRec(root.right, list);
        list.add(root);
    }

    /**
     * 层序遍历：先序+队列
     * @param root
     */
    public static void levelTravel(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode temp = q.poll();
            System.out.println(temp);
            if (temp.getLeft() != null) {
                q.offer(temp.getLeft());
            }
            if (temp.getRight() != null) {
                q.offer(temp.getRight());
            }
        }
    }

    /**
     * TODO 之字型遍历
     * @param root
     */
    public static void zigzagTravel(TreeNode root) {
    }

    // 求树中节点个数
    public static int getTreeNum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return getTreeNum(root.getLeft()) + getTreeNum(root.getRight()) + 1;
    }
    // 求第k层节点个数
    public static  int getNumForKlevel(TreeNode root, int k) {
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

    // 求树的深度
    public static int getTreeDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = getTreeDepth(root.getLeft()) + 1;
        int rightDepth = getTreeDepth(root.getRight()) + 1;
        return Math.max(leftDepth, rightDepth);
    }
    // 求叶子节点个数
    public static <T> int getLeafNum(TreeNode root) {
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
    public static boolean nodeIsChild(TreeNode root, TreeNode node) {
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
    // 交换左右子树
    public static TreeNode exchange(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = exchange(root.getLeft());
        TreeNode right = exchange(root.getRight());
        root.setLeft(right);
        root.setRight(left);
        return root;
    }
    // 寻找两节点的公共父节点
    public static  TreeNode findAllFatherNode(TreeNode root,
                                                    TreeNode lNode, TreeNode rNode) {
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
//    public static  TreeNode getTreeFromPreAndMid(List pre, List mid) {
//        if (pre == null || mid == null || pre.size() == 0 || mid.size() == 0) {
//            return null;
//        }
//        if (pre.size() == 1) {
//            return new TreeNode(pre.get(0));
//        }
//        TreeNode<T> root = new TreeNode<T>(pre.get(0));
//        // 找出根节点在中序中的位置
//        int index = 0;
//        while (!mid.get(index++).equals(pre.get(0))) {
//        }
//        // 构建左子树的前序和中序
//        List<T> preLeft = new ArrayList<T>(index);
//        List<T> midLeft = new ArrayList<T>(index);
//        for (int i = 1; i < index; i++) {
//            preLeft.add(pre.get(i));
//        }
//        for (int i = 0; i < index - 1; i++) {
//            midLeft.add(mid.get(i));
//        }
//        // 重建左子树
//        root.setLeft(getTreeFromPreAndMid(preLeft, midLeft));
//        // 构建右子树的前序和中序
//        List<T> preRight = new ArrayList<T>(pre.size() - index - 1);
//        List<T> midRight = new ArrayList<T>(pre.size() - index - 1);
//        for (int i = 0; i <= pre.size() - index - 1; i++) {
//            preRight.add(pre.get(index + i));
//        }
//        for (int i = 0; i <= pre.size() - index - 1; i++) {
//            midRight.add(mid.get(index + i));
//        }
//        // 重建右子树
//        root.setRight(getTreeFromPreAndMid(preRight, midRight));
//        return root;
//    }

    /**
     * 比较两棵树是否相等
     * @param node1
     * @param node2
     * @return
     */
    public static  boolean equals(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) {
            return true;
        } else if (node1 == null || node2 == null) {
            return false;
        }
        boolean isEqual = node1.getVal().equals(node2.getVal());
        boolean isLeftEqual = equals(node1.getLeft(), node2.getLeft());
        boolean isRightEqual = equals(node1.getRight(), node2.getRight());
        return isEqual && isLeftEqual && isRightEqual;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.addLeft(2);
        root.addRight(3);
        root.left.addLeft(4);
        root.right.addRight(5);
    }
}


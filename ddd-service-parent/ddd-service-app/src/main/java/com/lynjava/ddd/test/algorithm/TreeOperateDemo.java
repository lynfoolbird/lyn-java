package com.lynjava.ddd.test.algorithm;

import com.lynjava.ddd.test.algorithm.model.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

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

    /**
     * BM31 对称的二叉树
     * 方法一： 递归
     * @param pRoot
     * @return
     */
    public static boolean isSymmetrical(TreeNode pRoot) {
        return recursion(pRoot, pRoot);
    }
    private static boolean recursion(TreeNode root1, TreeNode root2){
        //可以两个都为空
        if(root1 == null && root2 == null)
            return true;
        //只有一个为空或者节点值不同，必定不对称
        if(root1 == null || root2 == null || root1.val != root2.val)
            return false;
        //每层对应的节点进入递归比较
        return recursion(root1.left, root2.right) && recursion(root1.right, root2.left);
    }

    /**
     * BM31 对称的二叉树
     * 方法二： 借助层序遍历，两个队列，一个从左到右，一个从右到左
     * @param pRoot
     * @return
     */
    public static boolean isSymmetrical2(TreeNode pRoot) {
        //空树为对称的
        if(pRoot == null)
            return true;
        //辅助队列用于从两边层次遍历
        Queue<TreeNode> q1 = new LinkedList<TreeNode>();
        Queue<TreeNode> q2 = new LinkedList<TreeNode>();
        q1.offer(pRoot.left);
        q2.offer(pRoot.right);
        while(!q1.isEmpty() && !q2.isEmpty()){
            //分别从左边和右边弹出节点
            TreeNode left = q1.poll();
            TreeNode right = q2.poll();
            //都为空暂时对称
            if(left == null && right == null)
                continue;
            //某一个为空或者数字不相等则不对称
            if(left == null || right == null || left.val != right.val)
                return false;
            //从左往右加入队列
            q1.offer(left.left);
            q1.offer(left.right);
            //从右往左加入队列
            q2.offer(right.right);
            q2.offer(right.left);
        }
        //都检验完都是对称的
        return true;
    }

    /**
     * BM33 二叉树的镜像
     * 方法一：递归，后序遍历
     * @param pRoot
     * @return
     */
    public static TreeNode mirror1(TreeNode pRoot) {
        //空树返回
        if(pRoot == null)
            return null;
        //先递归子树
        TreeNode left = mirror1(pRoot.left);
        TreeNode right = mirror1(pRoot.right);
        //交换
        pRoot.left = right;
        pRoot.right = left;
        return pRoot;
    }

    /**
     * BM33 二叉树的镜像
     * 方法二：利用栈
     * @param pRoot
     * @return
     */
    public static TreeNode mirror2(TreeNode pRoot) {
        //空树
        if(pRoot == null)
            return null;
        //辅助栈
        Stack<TreeNode> s = new Stack<TreeNode>();
        //根节点先进栈
        s.push(pRoot);
        while (!s.isEmpty()){
            TreeNode node = s.pop();
            //左右节点入栈
            if(node.left != null)
                s.push(node.left);
            if(node.right != null)
                s.push(node.right);
            //交换左右
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
        }
        return pRoot;
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


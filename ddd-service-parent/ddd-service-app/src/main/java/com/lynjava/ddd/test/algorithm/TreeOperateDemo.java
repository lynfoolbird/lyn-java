package com.lynjava.ddd.test.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeOperateDemo {

    // 将tree转成list
    public static void treeBean2list(TreeDataNode root,List<TreeDataNode> list, TreeDataNode parent){
        if (root == null) {
            return ;
        }
        root.setParentNode(parent);
        list.add(root);
        if (CollectionUtils.isEmpty(root.getChilds())){
            return;
        }
        for (TreeDataNode sub:root.getChilds()){
            treeBean2list(sub, list, root);
        }
    }


    // 求树中节点个数
    public static <T> int getTreeNum(TreeNode<T> root) {
        if (root == null) {
            return 0;
        }
        return getTreeNum(root.getLeftChild()) + getTreeNum(root.getRightChild()) + 1;
    }
    // 求第k层节点个数
    public static <T> int getNumForKlevel(TreeNode<T> root, int k) {
        if (root == null || k < 1) {
            return 0;
        }
        if (k == 1) {
            return 1;
        }
        int leftNum = getNumForKlevel(root.getLeftChild(), k - 1);
        int rightNum = getNumForKlevel(root.getRightChild(), k - 1);
        return leftNum + rightNum;
    }
    // 求叶子节点个数
    public static <T> int getLeafNum(TreeNode<T> root) {
        if (root == null) {
            return 0;
        }
        if (root.getLeftChild() == null && root.getRightChild() == null) {
            return 1;
        }
        int leftNum = getLeafNum(root.getLeftChild());
        int rightNum = getLeafNum(root.getRightChild());
        return leftNum + rightNum;
    }
    // 判断是否树的节点
    public static <T> boolean nodeIsChild(TreeNode<T> root, TreeNode<T> node) {
        if (root == null || node == null) {
            return false;
        }
        if (root == node) {
            return true;
        }
        boolean isFind = nodeIsChild(root.getLeftChild(), node);
        if (!isFind) {
            isFind = nodeIsChild(root.getRightChild(), node);
        }
        return isFind;
    }
    // 求树的深度
    public static <T> int getTreeDepth(TreeNode<T> root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = getTreeDepth(root.getLeftChild()) + 1;
        int rightDepth = getTreeDepth(root.getRightChild()) + 1;
        return Math.max(leftDepth, rightDepth);
    }
    // 先序遍历
    public static <T> void preOrderTravel(TreeNode<T> root) {
        if (root == null) {
            return;
        }
        System.out.println(root);
        preOrderTravel(root.getLeftChild());
        preOrderTravel(root.getRightChild());
    }
    // 中序遍历
    public static <T> void midOrderTravel(TreeNode<T> root) {
        if (root == null) {
            return;
        }
        midOrderTravel(root.getLeftChild());
        System.out.println(root);
        midOrderTravel(root.getRightChild());
    }
    // 后续遍历
    public static <T> void backOrderTravel(TreeNode<T> root) {
        if (root == null) {
            return;
        }
        backOrderTravel(root.getLeftChild());
        backOrderTravel(root.getRightChild());
        System.out.println(root);
    }
    // 层序遍历
    public static <T> void levelTravel(TreeNode<T> root) {
        Queue<TreeNode<T>> q = new LinkedList<TreeNode<T>>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode<T> temp = q.poll();
            System.out.println(temp);
            if (temp.getLeftChild() != null) {
                q.offer(temp.getLeftChild());
            }
            if (temp.getRightChild() != null) {
                q.offer(temp.getRightChild());
            }
        }
    }
    // 交换左右子树
    public static <T> TreeNode<T> exchange(TreeNode<T> root) {
        if (root == null) {
            return null;
        }
        TreeNode<T> left = exchange(root.getLeftChild());
        TreeNode<T> right = exchange(root.getRightChild());
        root.setLeftChild(right);
        root.setRightChild(left);
        return root;
    }
    // 寻找两节点的公共父节点
    public static <T> TreeNode<T> findAllFatherNode(TreeNode<T> root,
                                                    TreeNode<T> lNode, TreeNode<T> rNode) {
        if (lNode == root || rNode == root) {
            return root;
        }
        if (root == null || lNode == null || rNode == null) {
            return null;
        }
        // 如果lNode是左子树的节点
        if (nodeIsChild(root.getLeftChild(), lNode)) {
            if (nodeIsChild(root.getRightChild(), rNode)) {
                return root;
            } else {
                return findAllFatherNode(root.getLeftChild(), lNode, rNode);
            }
        } else {
            if (nodeIsChild(root.getLeftChild(), rNode)) {
                return root;
            } else {
                return findAllFatherNode(root.getRightChild(), lNode, rNode);
            }
        }
    }
    //根据前序和中序重建二叉树
    public static <T> TreeNode<T> getTreeFromPreAndMid(List<T> pre, List<T> mid) {
        if (pre == null || mid == null || pre.size() == 0 || mid.size() == 0) {
            return null;
        }
        if (pre.size() == 1) {
            return new TreeNode<T>(pre.get(0));
        }
        TreeNode<T> root = new TreeNode<T>(pre.get(0));
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
        root.setLeftChild(getTreeFromPreAndMid(preLeft, midLeft));
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
        root.setRightChild(getTreeFromPreAndMid(preRight, midRight));
        return root;
    }
    // 比较两棵树是否相等
    public static <T> boolean equals(TreeNode<T> node1, TreeNode<T> node2) {
        if (node1 == null && node2 == null) {
            return true;
        } else if (node1 == null || node2 == null) {
            return false;
        }
        boolean isEqual = node1.getValue().equals(node2.getValue());
        boolean isLeftEqual = equals(node1.getLeftChild(), node2.getLeftChild());
        boolean isRightEqual = equals(node1.getRightChild(), node2.getRightChild());
        return isEqual && isLeftEqual && isRightEqual;
    }

    public static void main(String[] args) {
        TreeNode<Integer> t = new TreeNode<Integer>(1);
        t.addLeft(2);
        t.addRight(3);
        t.getLeftChild().addLeft(4);
        t.getLeftChild().addRight(5);
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
        TreeNode<Integer> root = getTreeFromPreAndMid(pre, mid);
        System.out.println("\n通过前序和中序构建树测试：");
        levelTravel(root);
        System.out.println("\n构建的树比较测试:");
        System.out.println(equals(t,root));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TreeNode<T> {
        private T value;

        private TreeNode<T> leftChild;

        private TreeNode<T> rightChild;

        public TreeNode(T value) {
            this.value = value;
        }

        public void addLeft(T value) {
            TreeNode<T> left = new TreeNode<>(value);
            this.leftChild = left;
        }
        public void addRight(T value) {
            TreeNode<T> right = new TreeNode<>(value);
            this.rightChild = right;
        }
        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof TreeNode)){
                return false;
            }
            return this.value.equals(((TreeNode<?>)obj).value);
        }

        @Override
        public int hashCode() {
            return this.value.hashCode();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TreeDataNode {
        private String id;
        private String code;
        private String type;
        private List<TreeDataNode> childs;
        private TreeDataNode parentNode;

        // 复写tostring方法：注意去除parentNode循环依赖
        @Override
        public String toString() {
            return "TreeNode{" +
                    "id='" + id + '\'' +
                    ", code='" + code + '\'' +
                    ", type='" + type + '\'' +
                    ", childs=" + childs +
                    '}';
        }
    }
}


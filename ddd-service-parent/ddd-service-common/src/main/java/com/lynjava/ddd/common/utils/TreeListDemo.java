package com.lynjava.ddd.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 树和list转换
 */
public class TreeListDemo {
    public static void main(String[] args) {
        TreeDataNode rot = new TreeDataNode();
        rot.setId("0");
        rot.setChilds(new ArrayList<>());
        TreeDataNode node1 = new TreeDataNode();
        node1.setId("1");
        node1.setChilds(new ArrayList<>());
        rot.getChilds().add(node1);
        TreeDataNode node2 = new TreeDataNode();
        node2.setId("2");
        node1.getChilds().add(node2);
        TreeDataNode node3 = new TreeDataNode();
        node3.setId("3");
        rot.getChilds().add(node3);
        List listTree01 = new ArrayList();
        rot.toListTree(listTree01);
        List listTree02 = new ArrayList();
        rot.toListTree(listTree02, new ArrayList<>());
        System.out.println("==================");
    }
    // 将tree转成list
    public static void treeBean2list(TreeDataNode root, List<TreeDataNode> list, TreeDataNode parent){
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

    // 将list转成tree
    public static List<TreeDataNode> list2tree(List<TreeDataNode> list) {
        List<TreeDataNode> resList = new ArrayList<>();
        for (TreeDataNode t1:list){
            boolean isRoot = true;
            for (TreeDataNode t2:list){
                if (t1.getParentNode()!=null && t1.getParentNode().getId().equals(t2.getId())) {
                    isRoot = false;
                    if (t2.getChilds()==null){
                        t2.setChilds(new ArrayList<>());
                    }
                    t2.getChilds().add(t1);
                    break;
                }
            }
            if (isRoot) {
                resList.add(t1);
            }
        }
        return resList;
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

        // 将树转换成list形式
        public void toListTree(List<TreeDataNode> list) {
            list.add(this);
            if (CollectionUtils.isEmpty(this.childs)) {
                return;
            }
            this.childs.forEach(treeDataNode -> treeDataNode.toListTree(list));
        }

        // 获取树的所有路径
        public void toListTree(List<List<TreeDataNode>> list, List<TreeDataNode> t) {
            t.add(this);
            if (CollectionUtils.isEmpty(this.childs)) {
                return;
            }
            for (TreeDataNode treeDataNode : this.childs) {
                List<TreeDataNode> tmp = new ArrayList<>();
                tmp.addAll(t);
                treeDataNode.toListTree(list, tmp);
//                if (CollectionUtils.isEmpty(treeDataNode.getChilds())) {
//                    list.add(tmp);
//                }
            }
        }
    }
}

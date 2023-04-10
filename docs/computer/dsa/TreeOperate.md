
# 二叉树遍历

## 先序遍历

## 中序遍历

## 后序遍历

## 层序遍历

```java
    public ArrayList<ArrayList<Integer>> levelOrder (TreeNode root) {
        ArrayList<ArrayList<Integer> > res = new ArrayList();
        if (root == null)
            //如果是空，则直接返回空数组
            return res;
        //队列存储，进行层次遍历
        Queue<TreeNode> q = new ArrayDeque<TreeNode>();
        q.add(root);
        while (!q.isEmpty()) {
            //记录二叉树的某一行
            ArrayList<Integer> row = new ArrayList();
            int n = q.size();
            //因先进入的是根节点，故每层节点多少，队列大小就是多少
            for (int i = 0; i < n; i++) {
                TreeNode cur = q.poll();
                row.add(cur.val);
                //若是左右孩子存在，则存入左右孩子作为下一个层次
                if (cur.left != null)
                    q.add(cur.left);
                if (cur.right != null)
                    q.add(cur.right);
            }
            //每一层加入输出
            res.add(row);
        }
        return res;
    }
```

## 之字遍历



# BM28 二叉树最大深度

方法一：递归

```java
public int maxDepth (TreeNode root) {
   if (root == null) {
       return 0;
   }
   return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
}
```

方法二：层序遍历

```java
   public int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        // 队列，每次while循环保存当前层的所有结点
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        int res = 0;
        queue.add(root);
        // 遍历每一层
        while (!queue.isEmpty()) {
            int size = queue.size();
            // 遍历当前层每个结点
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
            // 记录层数
            res++;
        }
        return res;
    }
```


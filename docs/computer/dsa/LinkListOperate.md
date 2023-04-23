# BM12 单链表排序

方法一：归并排序，分治，将单链表从中间节点断开，转化成两个有序链表的合并问题，递归

```java
    //合并两段有序链表
    ListNode merge(ListNode pHead1, ListNode pHead2) {
        //一个已经为空了，直接返回另一个
        if(pHead1 == null)
            return pHead2;
        if(pHead2 == null)
            return pHead1;
        //加一个表头
        ListNode head = new ListNode(0);
        ListNode cur = head;
        //两个链表都要不为空
        while(pHead1 != null && pHead2 != null){
            //取较小值的节点
            if(pHead1.val <= pHead2.val){
                cur.next = pHead1;
                //只移动取值的指针
                pHead1 = pHead1.next;
            }else{
                cur.next = pHead2;
                //只移动取值的指针
                pHead2 = pHead2.next;
            }
            //指针后移
            cur = cur.next;
        }
        //哪个链表还有剩，直接连在后面
        if(pHead1 != null)
            cur.next = pHead1;
        else
            cur.next = pHead2;
        //返回值去掉表头
        return head.next;
    }
     
    public ListNode sortInList (ListNode head) {
        //链表为空或者只有一个元素，直接就是有序的
        if(head == null || head.next == null)
            return head;
        ListNode left = head;
        ListNode mid = head.next;
        ListNode right = head.next.next;
        //右边的指针到达末尾时，中间的指针指向该段链表的中间
        while(right != null && right.next != null){
            left = left.next;
            mid = mid.next;
            right = right.next.next;
        }
        //左边指针指向左段的左右一个节点，从这里断开
        left.next = null;
        //分成两段排序，合并排好序的两段
        return merge(sortInList(head), sortInList(mid));
    }
```

方法二：转换成数组，对数组排序后，重建链表

# BM11 链表相加二

方法一：翻转链表

```java
public ListNode addInList (ListNode head1, ListNode head2) {
        // 进行判空处理
        if(head1 == null)
            return head2;
        if(head2 == null){
            return head1;
        }
        // 反转h1链表
        head1 = reverse(head1);
        // 反转h2链表
        head2 = reverse(head2);
        // 创建新的链表头节点
        ListNode head = new ListNode(-1);
        ListNode nHead = head;
        // 记录进位的数值
        int tmp = 0;
        while(head1 != null || head2 != null){
            // val用来累加此时的数值（加数+加数+上一位的进位=当前总的数值）
            int val = tmp;
            // 当节点不为空的时候，则需要加上当前节点的值
            if (head1 != null) {
                val += head1.val;
                head1 = head1.next;
            }
            // 当节点不为空的时候，则需要加上当前节点的值
            if (head2 != null) {
                val += head2.val;
                head2 = head2.next;
            }
            // 求出进位
            tmp = val/10;
            // 进位后剩下的数值即为当前节点的数值
            nHead.next = new ListNode(val%10);
            // 下一个节点
            nHead = nHead.next;
 
        }
        // 最后当两条链表都加完的时候，进位不为0的时候，则需要再加上这个进位
        if(tmp > 0){
            nHead.next = new ListNode(tmp);
        }
        // 重新反转回来返回
        return reverse(head.next);
    }
 
    // 反转链表
    ListNode reverse(ListNode head){
        if(head == null)
            return head;
        ListNode cur = head;
        ListNode node = null;
        while(cur != null){
            ListNode tail = cur.next;
            cur.next = node;
            node = cur;
            cur = tail;
        }
        return node;
    }
```

方法二：借助栈

```java
public ListNode addInList (ListNode head1, ListNode head2) {      
        if(head1 == null)
            return head2;
        if(head2 == null){
            return head1;
        }
        // 使用两个辅助栈，利用栈先进后出，相当于反转了链表
        Stack<ListNode> stack1 = new Stack<>();
        Stack<ListNode> stack2 = new Stack<>();
        ListNode p1=head1;
        ListNode p2=head2;
        // 将两个链表的结点入栈
        while(p1!=null){
            stack1.push(p1);
            p1=p1.next;
        }
        while(p2!=null){
            stack2.push(p2);
            p2=p2.next;
        }
        // 进位
        int tmp = 0;
        // 创建新的链表头节点
        ListNode head = new ListNode(-1);
        ListNode nHead = head.next;
        while(!stack1.isEmpty()||!stack2.isEmpty()){
            // val用来累加此时的数值（加数+加数+上一位的进位=当前总的数值）
            int val = tmp;
            // 栈1不为空的时候，弹出结点并累加值
            if (!stack1.isEmpty()) {
                val += stack1.pop().val;
            }
            // 栈2不为空的时候，弹出结点并累加值
            if (!stack2.isEmpty()) {
                val += stack2.pop().val;
            }
            // 求出进位
            tmp = val/10;
            // 进位后剩下的数值即为当前节点的数值
            ListNode node = new ListNode(val%10);
            // 将结点插在头部
            node.next = nHead;
            nHead = node;
        }
        if(tmp > 0){
            // 头插
            ListNode node = new ListNode(tmp);
            node.next = nHead;
            nHead = node;
        }
        return nHead;
}
```


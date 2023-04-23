package com.lynjava.ddd.test.algorithm;

import com.lynjava.ddd.test.algorithm.model.ListNode;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 链表基本操作
 */
public class LinkListOperateDemo {
    public static void main(String[] args) {
        ListNode node4 = new ListNode(4, null);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);
//        print(node1);
//        SListNode rev = reverse(node1);
//        print(rev);

//        SListNode node6 = new SListNode(5, null);
//        SListNode node5 = new SListNode(4, node6);
//        SListNode node4 = new SListNode(3, node5);
//        intersectionOfOrderedLinkList(node1, node4);
//        System.out.println(circleExist(node1));
    }

    /**
     * 递归翻转链表
     * @param head
     * @return
     */
    private static ListNode reverse1(ListNode head) {
        if (head==null || head.next==null) {
            return head;
        }
        ListNode last = reverse1(head.next);
        head.next.next = head;
        head.next = null;
        return last;
    }

    /**
     * 使用栈
     * @param head
     * @return
     */
    private static ListNode reverse2(ListNode head) {
        if (head==null || head.next==null) {
            return head;
        }
        Stack<ListNode> stack = new Stack<>();
        ListNode p = head;
        while (p != null) {
            stack.push(p);
            p = p.next;
        }
        ListNode nHead = stack.pop();
        ListNode np = nHead;
        while (!stack.isEmpty()) {
            np.next = stack.pop();
            np = np.next;
        }
        np.next = null;
        return nHead;
    }

    /**
     * 递归翻转链表
     * @param head
     * @return
     */
    public static ListNode reverse3(ListNode head) {
        if (head==null || head.next==null) {
            return head;
        }
        //pre指针：用来指向反转后的节点，初始化为null
        ListNode pre = null;
        //当前节点指针
        ListNode cur = head;
        //循环迭代
        while(cur!=null){
            //Cur_next 节点，永远指向当前节点cur的下一个节点
            ListNode Cur_next = cur.next;
            //反转的关键：当前的节点指向其前一个节点(注意这不是双向链表，没有前驱指针)
            cur.next = pre;
            //更新pre
            pre = cur;
            //更新当前节点指针
            cur = Cur_next ;
        }
        //为什么返回pre？因为pre是反转之后的头节点
        return pre;
    }
    /**
     * 判断链表是否有环：快慢指针，若能相等则存在环
     * @param head
     * @return
     */
    private static boolean circleExist1(ListNode head) {
        if (head==null || head.next==null) {
            return false;
        }
        ListNode p1 = head;
        ListNode p2 = head;
        while (p2 != null && p2.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
            if (p1 == p2) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断链表是否有环：hash表判断地址是否已存在
     * @param head
     * @return
     */
    private static boolean circleExist2(ListNode head) {
        if (head==null || head.next==null) {
            return false;
        }
        Set<ListNode> set= new HashSet<>();
        ListNode p = head;
        while (p != null) {
            if (set.contains(p)) {
                return true;
            }
            p = p.next;
        }
        return false;
    }

    /**
     * TODO 求环的起始节点: 快慢指针
     * @param head
     */
    private static void circleFront(ListNode head) {
    }

    /**
     * 判断链表是否回文
     * 方法一：遍历链表，节点值存到数组中，然后用首尾双指针判断
     * 方法二：寻找中间节点，然后对后半段链表逆序，再比较
     * @param head
     * @return
     */
    private static boolean isPail(ListNode head) {
        ListNode q= head, p= head;
        //通过快慢指针找到中点
        while (q != null && q.next != null) {
            q = q.next.next;
            p = p.next;
        }
        //如果q不为空，说明链表的长度是奇数个
        if (q != null) {
            p = p.next;
        }
        //反转后半部分链表
        p = reverse3(p);

        q = head;
        while (p != null) {
            //然后比较，判断节点值是否相等
            if (q.val != p.val)
                return false;
            q = q.next;
            p = p.next;
        }
        return true;
    }

    /**
     * 返回链表倒数第K个节点
     * 方法一：遍历保存到数组，然后直接索引获取
     * 方法二：遍历压到栈中，然后出栈
     * 方法三：计算链表长度，然后正数
     * 方法四：快慢指针
     * @param pHead
     * @param k
     * @return
     */
    public ListNode findKthToTail (ListNode pHead, int k) {
        if (null == pHead) {
            return null;
        }
        // write code here
        ListNode fast = pHead;
        for (int i=0; i<k; i++) {
            if (fast != null) {
                fast = fast.next;
            } else {
                return null;
            }
        }
        ListNode cur = pHead;
        while (fast!=null) {
            fast = fast.next;
            cur = cur.next;
        }
        return cur;
    }

    /**
     * 删除链表倒数第n个节点，n不大于链表长度
     * 方法一：计算链表长度len，倒数第n个即是正数第len-n+1个节点
     * 方法二：快慢指针，快指针先走n步，然后和慢指针一块出发，快指针到末尾时慢指针指向需要删除的节点
     *       需要记录慢指针的前一个节点用于删除目标节点
     * 定义虚节点方便处理边界问题，比如头结点的删除等
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd (ListNode head, int n) {
        // 定义假节点指向头结点
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        // 快指针先走n步
        ListNode fast = dummy;
        for (int i=0; i<n; i++) {
            fast = fast.next;
        }
        ListNode cur = dummy;
        ListNode curPre = dummy;
        while (fast != null) {
            fast = fast.next;
            curPre = cur;
            cur = cur.next;
        }
        // 删除目标节点
        curPre.next = cur.next;
        return dummy.next;
    }
    /**
     * 两个有序链表求交集
     * @param list1
     * @param list2
     */
    private static void intersectionOfOrderedLinkList(ListNode list1, ListNode list2) {
        if (list1==null || list2==null) {
            return;
        }
        ListNode p1 = list1;
        ListNode p2 = list2;
        while (p1!=null && p2!=null) {
            if (p1.val == p2.val) {
                System.out.println(p1.val);
                p1 = p1.next;
                p2 = p2.next;
            } else if (p1.val > p2.val) {
                p2 = p2.next;
            } else {
                p1 = p1.next;
            }
        }
    }

    /**
     * 合并有序链表(循环实现/递归实现)
     * @param list1
     * @param list2
     */
    public static ListNode mergeOfOrderedLinkList(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        ListNode p1 = list1;
        ListNode p2 = list2;
        // 虚节点
        ListNode list = new ListNode(-1, null);
        ListNode p = list;
        while (p1!=null && p2!=null) {
            if (p1.val >= p2.val) {
                p.next = p2;
                p2 = p2.next;
            } else {
                p.next = p1;
                p1 = p1.next;
            }
            p = p.next;
        }
        if (p1 != null) {
            p.next = p1;
        }
        if (p2 != null) {
            p.next = p2;
        }
        return list.next;
    }


    /**
     * 两个链表的第一个公共节点
     * 方法一：双指针，遍历到表尾时从另一个表的表头遍历
     * 方法二：先计算长度，m，n；长度长的先走m-n步，然后另一指针走
     * 方法三：先遍历其一，存集合中，再遍历另一个判断是否存在集合中
     *
     * @param list1
     * @param list2
     */
    public static ListNode findFirstCommonNode(ListNode list1, ListNode list2) {
        ListNode p1 = list1;
        ListNode p2 = list2;
        while (p1 != p2) {
            p1 = p1==null ? list2 : list1.next;
            p2 = p2==null ? list1 : list2.next;
        }
        return p1;
    }

    /**
     * BM15 删除有序链表的重复元素-I(重复元素取一)
     * @param head
     * @return
     */
    public static ListNode deleteDuplicates1(ListNode head) {
        if (head==null || head.next==null) {
            return head;
        }
        ListNode p1 = head;
        while (p1!=null && p1.next!=null) {
            if (p1.val == p1.next.val) {
                p1.next = p1.next.next;
            } else {
                p1=p1.next;
            }
        }
        return head;
    }

    /**
     * BM12 删除有序链表的重复元素-II(重复元素都删除)
     * 方法一：增加内循环，遍历删除所有重复
     * 方法二：哈希表，遍历存储所有节点出现次数，删除出现次数超过1的节点
     * @param head
     * @return
     */
    public static ListNode deleteDuplicates2(ListNode head) {
        if (head==null || head.next==null) {
            return head;
        }
        //在链表前加一个表头
        ListNode res = new ListNode(0);
        res.next = head;
        ListNode cur = res;
        while (cur.next != null && cur.next.next != null) {
            //遇到相邻两个节点值相同
            if (cur.next.val == cur.next.next.val) {
                int temp = cur.next.val;
                //将所有相同的都跳过
                while (cur.next != null && cur.next.val == temp)
                    cur.next = cur.next.next;
            } else
                cur = cur.next;
        }
        //返回时去掉表头
        return res.next;
    }
    //合并两段有序链表
    public static ListNode merge(ListNode pHead1, ListNode pHead2) {
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

    /**
     * BM12 单链表排序
     * 方法一：归并排序，分治，将单链表从中间节点断开，转化成两个有序链表的合并问题，递归
     * 方法二：转换成数组，对数组排序后，重建链表
     *
     * @param head
     * @return
     */
    public static ListNode sortInList (ListNode head) {
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

    /**
     * BM11 链表相加二
     * 方法一：翻转链表
     * 方法二：借助栈
     *
     * @param head1
     * @param head2
     * @return
     */
    public static ListNode addInList (ListNode head1, ListNode head2) {
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

    private static void print(ListNode head) {
        ListNode p = head;
        while (p != null) {
            System.out.print(p.val + ", ");
            p = p.next;
        }
        System.out.println();
    }
}



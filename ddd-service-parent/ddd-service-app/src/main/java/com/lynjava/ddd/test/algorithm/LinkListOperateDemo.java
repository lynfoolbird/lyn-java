package com.lynjava.ddd.test.algorithm;

import com.lynjava.ddd.test.algorithm.model.ListNode;

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
        System.out.println(circleExist(node1));
    }

    /**
     * 递归翻转链表
     * @param head
     * @return
     */
    private static ListNode reverse(ListNode head) {
        if (head==null || head.next==null) {
            return head;
        }
        ListNode last = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return last;
    }

    /**
     * 判断链表是否有环：快慢指针，若能相等则存在环
     * @param head
     * @return
     */
    private static boolean circleExist(ListNode head) {
        if (head==null || head.next==null) {
            return false;
        }
        ListNode p1 = head;
        ListNode p2 = head;
        while (p2 != null) {
            p1 = p1.next;
            p2 = p2.next.next;
            if (p1 == p2) {
                return true;
            }
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

    private static void print(ListNode head) {
        ListNode p = head;
        while (p != null) {
            System.out.print(p.val + ", ");
            p = p.next;
        }
        System.out.println();
    }
}



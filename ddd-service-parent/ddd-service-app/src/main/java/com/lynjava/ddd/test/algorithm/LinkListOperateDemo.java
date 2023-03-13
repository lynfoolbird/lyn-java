package com.lynjava.ddd.test.algorithm;

import com.lynjava.ddd.test.algorithm.model.SListNode;

/**
 * 链表基本操作
 */
public class LinkListOperateDemo {
    public static void main(String[] args) {
        SListNode node4 = new SListNode(4, null);
        SListNode node3 = new SListNode(3, node4);
        SListNode node2 = new SListNode(2, node3);
        SListNode node1 = new SListNode(1, node2);
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
    private static SListNode reverse(SListNode head) {
        if (head==null || head.next==null) {
            return head;
        }
        SListNode last = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return last;
    }

    /**
     * 判断链表是否有环：快慢指针，若能相等则存在环
     * @param head
     * @return
     */
    private static boolean circleExist(SListNode head) {
        if (head==null || head.next==null) {
            return false;
        }
        SListNode p1 = head;
        SListNode p2 = head;
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
     * 求环的起始节点: 快慢指针
     * @param head
     */
    private static void circleFront(SListNode head) {
    }
    /**
     * 两个有序链表求交集
     * @param list1
     * @param list2
     */
    private static void intersectionOfOrderedLinkList(SListNode list1, SListNode list2) {
        if (list1==null || list2==null) {
            return;
        }
        SListNode p1 = list1;
        SListNode p2 = list2;
        while (p1!=null && p2!=null) {
            if (p1.value == p2.value) {
                System.out.println(p1.value);
                p1 = p1.next;
                p2 = p2.next;
            } else if (p1.value > p2.value) {
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
    public static SListNode mergeOfOrderedLinkList(SListNode list1, SListNode list2) {
        if (list1==null || list2==null) {
            return null;
        }
        SListNode p1 = list1;
        SListNode p2 = list2;
        SListNode list = new SListNode(-1, null);
        SListNode p = list;
        while (p1!=null && p2!=null) {
            if (p1.value >= p2.value) {
                p.next = p2;
                p2 = p2.next;
            } else {
                p.next = p1;
                p1 = p1.next;
            }
            p = p.next;
        }
        if (p1 != null) {
            p.setNext(p1);
        }
        if (p2 != null) {
            p.setNext(p2);
        }
        return list;
    }

    private static void print(SListNode head) {
        SListNode p = head;
        while (p != null) {
            System.out.print(p.value + ", ");
            p = p.next;
        }
        System.out.println();
    }
}



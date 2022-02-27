package com.lynjava.ddd.test.algorithm;

import java.util.Objects;

public class LinkListOperateDemo {
    public static void main(String[] args) {
        Node node5 = new Node(7, null);
        Node node4 = new Node(6, node5);
        Node node3 = new Node(3, node4);
        Node node2 = new Node(2, node3);
        Node header1 = new Node(1, node2);

        Node nodet2 = new Node(4, null);
        Node header2 = new Node(2, nodet2);
//        jiaojiOfLinkList(header2, header1);
        mergeOfLinkList(header1, header2);

    }

    // 两个有序链表的交集
    public static void jiaojiOfLinkList(Node list1, Node list2) {
        if (list1==null || list2==null) {
            return;
        }
        Node p1 = list1;
        Node p2 = list2;
        while (p1!=null && p2!=null) {
            Integer value1 = p1.getValue();
            Integer value2 = p2.getValue();
            if (Objects.equals(value1, value2)) {
                System.out.println(value1);
                p1 = p1.getNext();
                p2 = p2.getNext();
            } else if (value1 > value2) {
                p2 = p2.getNext();
            } else {
                p1 = p1.getNext();
            }
        }
    }

    // 合并有序链表(循环实现/递归实现)
    public static void mergeOfLinkList(Node list1, Node list2) {
        if (list1==null || list2==null) {
            return;
        }
        Node p1 = list1;
        Node p2 = list2;
        Node list = new Node(-1, null);
        Node p = list;
        while (p1!=null && p2!=null) {
            int value1 = p1.getValue();
            int value2 = p2.getValue();
            if (value1 >= value2) {
                p.setNext(p2);
                p2 = p2.getNext();
            } else {
                p.setNext(p1);
                p1 = p1.getNext();
            }
            p = p.getNext();
        }
        if (p1 != null) {
            p.setNext(p1);
        }
        if (p2 != null) {
            p.setNext(p2);
        }
        printLinkList(list);
    }

    private static void printLinkList(Node list) {
        Node p = list;
        while (p != null) {
            System.out.print(p.getValue() + ", ");
            p = p.getNext();
        }
        System.out.println();
    }
}


class Node {
    private Integer value;
    private Node next;

    public Node(Integer value, Node next) {
        this.value = value;
        this.next = next;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
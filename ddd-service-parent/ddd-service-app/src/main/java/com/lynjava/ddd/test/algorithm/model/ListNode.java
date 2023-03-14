package com.lynjava.ddd.test.algorithm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单链表节点
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListNode {
    public Integer val;
    public ListNode next;

    public ListNode(Integer val) {
        this.val = val;
    }
    public void addNext(Integer val) {
        this.next = new ListNode(val);
    }
}

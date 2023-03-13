package com.lynjava.ddd.test.algorithm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 双向链表节点
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DListNode {
    public Integer value;
    public DListNode prev;
    public DListNode next;
}

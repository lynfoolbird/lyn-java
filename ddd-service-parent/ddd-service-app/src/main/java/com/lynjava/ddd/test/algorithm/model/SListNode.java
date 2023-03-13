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
public class SListNode {
    public Integer value;
    public SListNode next;
}

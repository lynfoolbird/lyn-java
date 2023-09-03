package com.lynjava.ddd.test.architecture.designpattern.chain.list;

import com.lynjava.ddd.test.architecture.designpattern.chain.link.AbstractApproveHandler;

public class ApproveChainBuilder {
    private AbstractApproveHandler head;

    private AbstractApproveHandler tail;

    public ApproveChainBuilder addHandler(AbstractApproveHandler handler) {
        if (this.head == null)  {
            this.head = handler;
        } else {
            this.tail.setNext(handler);
        }
        this.tail = handler;
        return this;
    }

    public AbstractApproveHandler build() {
        return this.head;
    }
}


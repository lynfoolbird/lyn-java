package com.lynjava.ddd.test.architecture.designpattern.chain.link;

import com.lynjava.ddd.test.architecture.designpattern.chain.ApplyContext;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public abstract class AbstractApproveHandler {
    /**
     * 下一个节点
     */
    @Getter
    @Setter
    private AbstractApproveHandler next;

    public void handler(ApplyContext context) throws Exception {
        // 若上层节点未执行成功，直接返回
        Boolean prevResult = context.getSuccess();
        if (Objects.nonNull(prevResult) && !context.getSuccess()) {
            return;
        }
        // 当前节点处理
        process(context);
        // 交给后续节点处理
        if (Objects.nonNull(this.next)) {
            this.next.handler(context);
        }
    }

    public abstract void process(ApplyContext context) throws Exception;

    public static class Builder {
        private AbstractApproveHandler head;

        private AbstractApproveHandler tail;

        public Builder addHandler(AbstractApproveHandler handler) {
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
}

package com.lynjava.ddd.test.architecture.designpattern.chain.list.base;

import com.lynjava.ddd.test.architecture.designpattern.chain.GatewayContext;
import com.lynjava.ddd.test.architecture.designpattern.chain.InvokeResult;

import java.util.List;

public interface IPlugin {
    InvokeResult invoke(GatewayContext context, Chain chain) throws Exception;

    interface Chain {

        InvokeResult proceed() throws Exception;
    }

    class PluginChain implements Chain {

        private final int index;

        private final GatewayContext context;

        private final List<IPlugin> plugins;

        public PluginChain(int index, GatewayContext context, List<IPlugin> plugins) {
            this.index = index;
            this.context = context;
            this.plugins = plugins;
        }

        @Override
        public InvokeResult proceed() throws Exception {
            if (index >= plugins.size()) {
                throw new IllegalStateException();
            }
            PluginChain next = new PluginChain(index + 1, context, plugins);
            IPlugin plugin = plugins.get(index);
            return plugin.invoke(context, next);
        }
    }
}

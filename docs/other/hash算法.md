从简单hash到hash slot

[一致性Hash原理与实现](https://www.jianshu.com/p/528ce5cd7e8f)
https://blog.csdn.net/qq_33945246/article/details/105113417

## 如何分配请求？

大多数网站背后肯定不是只有一台服务器提供服务，因为单机的并发量和数据量都是有限的，所以都会用多台服务器构成集群来对外提供服务。

但是问题来了，现在有那么多个节点（后面统称服务器为节点，因为少一个字），要如何分配客户端的请求呢？

# hash算法

hash冲突

开放地址法

拉链法

再hash法

# 一致性hash

# hash slot


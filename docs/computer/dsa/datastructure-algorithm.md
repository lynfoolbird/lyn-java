贪心、分治、动态规划、穷举、回溯、BFS DFS

排序算法、查找算法

数据结构：跳表 红黑树  B树  B+树 链表 栈 队列 树 图 hash表 数组

hash算法：常规hash、一致性hash、hash slot 、冲突解决方法

缓存算法：LRU、LFU

限流算法：固定窗口(计数器)、滑动窗口、漏桶、令牌桶

分布式一致性算法：raft、zab、paxos

bitmap  

bloom过滤器、布谷鸟过滤器



大厂面试编程题套路

https://learning.snssdk.com/feoffline/toutiao_wallet_bundles/toutiao_learning_wap/online/article.html?item_id=6954744518226739493&app_name=news_article



算法思维

labuladong的算法小抄  https://github.com/labuladong/fucking-algorithm

大师级算法宝典 pdf  剑指offer

OD算法培训 题目

递归：LeetCode70、112、509
分治：LeetCode23、169、240
单调栈：LeetCode84、85、739、503
并查集：LeetCode547、200、684
滑动窗口：LeetCode209、3、1004、1208
前缀和：LeetCode724、560、437、1248
差分：LeetCode1094、121、122
拓扑排序：LeetCode210
字符串：LeetCode5、20、43、93
二分查找：LeetCode33、34
BFS：LeetCode127、139、130、529、815
DFS&回溯：：LeetCode934、685、1102、531、533、113、332、337
动态规划：LeetCode213、123、62、63、361、1230
贪心算法：LeetCode55、435、621、452
字典树：LeetCode820、208、648

牛客算法必刷101题

https://www.nowcoder.com/exam/oj?page=1&tab=%E7%AE%97%E6%B3%95%E7%AF%87&topicId=295

编程练习

牛客网：https://www.nowcoder.com/ta/huawei

leetcode



输入读取练习

Scanner，存入二维数组，树



前缀和  差分

单调栈  单调队列  

双指针、快慢指针、头尾指针

滑动窗口 hash表

链表

二叉树 字典树

BFS DFS

贪心 回溯 动态规划

查找 二分查找

排序

堆

图





算法思维修炼

使用hash表、前缀和

数组类

1 先排序再处理，或许题目会变得简单起来

2 数组可以快速根据索引找到对应的值，当需要根据值获取索引时，需要将数组转为map，key为值，value为索引

3 有序数组可以结合二分查找优化时间复杂度

4 数组遍历：双指针遍历经常用到，注意明确指针初始状态、移动规则、结束条件，如头尾指针

链表类

1 基础的插入、查找、删除操作要掌握扎实

2 设置伪头节点，可以让边界处理变得更简单

3 注意判断Node是否为null，防止空指针异常

4 注意考虑代码是否存在指针丢失的问题，快慢指针，判断是否有环、入环节点、快速找到倒数第N个元素

栈和队列

1 记住栈的特点，后进先出LIFO，需要倒序的时候可能用到

2 二叉树的前中后序遍历的循环实现，需要用到栈；表达式求值、括号是否合法需要用栈解决



树



图



排序算法



查找算法
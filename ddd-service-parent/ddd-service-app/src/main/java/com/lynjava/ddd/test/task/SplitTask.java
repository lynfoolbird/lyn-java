package com.lynjava.ddd.test.task;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 大任务拆分成子任务用多线程执行
 * 使用guava工具类拆分
 * 某些场景下可以使用JDK forkjoin框架
 */
public class SplitTask {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();
        int size = 1099;
        for (int i = 0; i < size; i++) {
            list.add("hello-" + i);
        }
        // 大集合里面包含多个小集合
        List<List<String>> temps = split(list, 100);
        int j = 0;
        // 对大集合里面的每一个小集合进行操作
        for (List<String> obj : temps) {
            System.out.println(String.format("row:%s -> size:%s,data:%s", ++j, obj.size(), obj));
        }
    }

    public static <T> void threadMethod(List<T> totalList) throws Exception {
        // 初始化线程池, 参数一定要一定要一定要调好！！！！
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(20, 50,
                4, TimeUnit.SECONDS, new ArrayBlockingQueue(10), new ThreadPoolExecutor.AbortPolicy());
        // 大集合拆分成N个小集合, 这里集合的size可以稍微小一些（这里我用100刚刚好）, 以保证多线程异步执行, 过大容易回到单线程
        List<List<T>> splitNList = split(totalList, 100);
        // 使用guava工具类拆分更方便
        List<List<T>> splitList2 = Lists.partition(totalList, 100);
        // 记录单个任务的执行次数
        CountDownLatch countDownLatch = new CountDownLatch(splitNList.size());
        // 对拆分的集合进行批量处理, 先拆分的集合, 再多线程执行
        for (List<T> singleList : splitNList) {
            // 线程池执行
            threadPool.execute(new Thread(() -> {
                // 处理子表
            }));
            countDownLatch.countDown();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new Exception("failed");
        }
    }

    /**
     * 拆分集合
     *
     * @param <T> 泛型对象
     * @param resList 需要拆分的集合
     * @param subListLength 每个子集合的元素个数
     * @return 返回拆分后的各个集合组成的列表
     * 代码里面用到了guava和common的结合工具类
     **/
    public static <T> List<List<T>> split(List<T> resList, int subListLength) {
        if (CollectionUtils.isEmpty(resList) || subListLength <= 0) {
            return Lists.newArrayList();
        }
        List<List<T>> ret = Lists.newArrayList();
        int size = resList.size();
        if (size <= subListLength) {
            // 数据量不足 subListLength 指定的大小
            ret.add(resList);
        } else {
            int pre = size / subListLength;
            int last = size % subListLength;
            // 前面pre个集合，每个大小都是 subListLength 个元素
            for (int i = 0; i < pre; i++) {
                List<T> itemList = Lists.newArrayList();
                for (int j = 0; j < subListLength; j++) {
                    itemList.add(resList.get(i * subListLength + j));
                }
                ret.add(itemList);
            }
            // last的进行处理
            if (last > 0) {
                List<T> itemList = Lists.newArrayList();
                for (int i = 0; i < last; i++) {
                    itemList.add(resList.get(pre * subListLength + i));
                }
                ret.add(itemList);
            }
        }
        return ret;
    }
}

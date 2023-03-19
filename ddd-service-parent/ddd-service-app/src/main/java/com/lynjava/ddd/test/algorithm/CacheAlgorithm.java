package com.lynjava.ddd.test.algorithm;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 缓存算法：LRU、LFU
 * TODO LFU
 */
public class CacheAlgorithm {
    public static void main(String[] args) {
        LRUCache2 cache2 = new LRUCache2(1);
        cache2.put("one", "one");
        cache2.put("two", "two");
        System.out.println(cache2);
    }

    /**
     * 用双链表+hash表实现
     */
    public static class LRUCache {
        private int capacity;
        private int size;
        private Map<String, DLinkedNode> cache = new HashMap<>();
        private DLinkedNode head;
        private DLinkedNode tail;
        public LRUCache(int capacity) {
            this.capacity = capacity;
            // 表头表尾用伪节点方便处理
            DLinkedNode head = new DLinkedNode();
            DLinkedNode tail = new DLinkedNode();
            head.next = tail;
            tail.prev = head;
        }

        public String get(String key) {
            DLinkedNode node = cache.get(key);
            if (null == node) {
                return "-1";
            }
            // key存在，将对应节点移动到头节点
            moveToHead(node);
            return node.value;
        }

        public void put(String key, String value) {
            DLinkedNode node = cache.get(key);
            if (null == node) {
                DLinkedNode newNode = new DLinkedNode(key, value);
                cache.put(key, newNode);
                addToHead(newNode);
                ++size;
                if (size > capacity) {
                    DLinkedNode tal = delTail();
                    cache.remove(tal.key);
                    --size;
                }
            } else {
                node.value = value;
                moveToHead(node);
            }
        }

        // 表头插入节点
        private void addToHead(DLinkedNode node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }
        // 移动节点到表头
        private void moveToHead(DLinkedNode node) {
            delNode(node);
            addToHead(node);
        }
        // 删除节点
        private void delNode(DLinkedNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        // 淘汰尾节点
        private DLinkedNode delTail() {
            DLinkedNode res = tail.prev;
            delNode(res);
            return res;
        }
    }


    /**
     * 继承LinkedHashMap
     */
    public static class LRUCache2 extends LinkedHashMap {
        private int cacheSize;

        public LRUCache2(int size) {
            super((int) Math.ceil(size/0.75f+1),0.75f, true);
            this.cacheSize = size;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return this.size() > cacheSize;
        }
    }

    /**
     * 使用LinkedHashMap
     */
    public static class LRUCache3 {
        private LinkedHashMap<Integer, Integer> cacheMap = new LinkedHashMap<>();
        private int cap;

        public LRUCache3(int cap) {
            this.cap = cap;
        }

        public int get(int key) {
            if (!cacheMap.containsKey(key)) {
                return -1;
            }
            Integer val = cacheMap.get(key);
            //先删除，再添加
            cacheMap.remove(key);
            cacheMap.put(key, val);
            return val;
        }

        public void put(int key, int val) {
            if (cacheMap.containsKey(key)) {
                //先删除，再添加
                cacheMap.remove(key);
                cacheMap.put(key, val);
                return;
            }
            //达到容量后，删除第一个元素
            if (cacheMap.size() == cap) {
                Integer firstKey = cacheMap.keySet().iterator().next();
                cacheMap.remove(firstKey);
            }
            cacheMap.put(key, val);
        }
    }

    @Data
    @NoArgsConstructor
    public static class DLinkedNode {
        String key;
        String value;
        DLinkedNode prev;
        DLinkedNode next;

        public DLinkedNode(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}

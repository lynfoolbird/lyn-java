[实现LRU缓存机制](https://www.toutiao.com/i7005048942614315531/?tt_from=weixin&utm_campaign=client_share&wxshare_count=1&timestamp=1631290826&app=news_article&utm_source=weixin&utm_medium=toutiao_android&use_new_style=1&req_id=20210911002026010212205224011A2945&share_token=5519b469-4034-43a8-98cd-b61481b7c0b9&group_id=7005048942614315531)

# 基本概念

LRU, Least Recently Used，即最近最少使用，当容量不足时，优先淘汰最久未使用的数据。

# 核心方法

用一个队列来存储数据，最近使用的数据存在队尾，最久未使用的数据存在队首。

## 1、get (key)

当对某个 key 进行访问时，将数据移至队尾。

## 2、put (key, value)

当添加一个元素时

- 如果 key 存在，则覆盖 value ，并将数据移至队尾
- 如果 key 不存在，则直接插入到队尾
- 如果容量不足时，则删除队首的数据

# 实现方法

## 方法1，双链表 + 哈希表

```java
public class LruCache {
    private HashMap<Integer, Node> map;
    private DoubleList cache;
    private final int cap;
    
    public LruCache(int cap) {
        this.map = new HashMap<>();
        this.cache = new DoubleList();
        this.cap = cap;
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        // 将元素移至队尾
        makeRecently(key);
        return node.val;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            //先删除，再添加
            deleteKey(key);
            addRecently(key, value);
            return;
        }
        //如果达到容量最大值，则删除队首元素
        if (cache.size() == cap) {
            removeLatestRecently();
        }
        addRecently(key, value);
    }

    private void makeRecently(int key) {
        Node node = map.get(key);
        //删除当前元素
        cache.remove(node);
        //将元素加至队尾
        cache.addLast(node);
    }

    /**
     * 新增一个元素，加至队尾
     */
    private void addRecently(int key, int value) {
        Node node = new Node(key, value);
        map.put(key, node);
        cache.addLast(node);
    }

    private void deleteKey(int key) {
        Node node = map.get(key);
        map.remove(key);
        cache.remove(node);
    }

    private void removeLatestRecently() {
        Node node = cache.removeFirst();
        map.remove(node.key);
    }
}

class DoubleList {
    /**
     * 两个首位虚节点
     */
    Node head, tail;
    int size;

    public DoubleList() {
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.pre = head;
        size = 0;
    }

    /**
     * 队尾是新元素
     */
    public void addLast(Node node) {
        node.next = tail;
        node.pre = tail.pre;
        tail.pre.next = node;
        tail.pre = node;
        size++;
    }
    /**
     * 删除节点
     */
    public void remove(Node node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
        size--;
    }
    /**
     * 删除队首最老的元素
     */
    public Node removeFirst() {
        if (head.next == tail) {
            return null;
        }
        Node first = head.next;
        remove(first);
        size--;
        return first;
    }

    public int size() {
        return size;
    }
}

class Node {
    int key;
    int val;
    Node pre;
    Node next;

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }
}
```

## 方法2，借助 LinkedHashMap

```java
public class LinkedLruCache {
    private LinkedHashMap<Integer, Integer> cacheMap = new LinkedHashMap<>();
    private int cap;

    public LinkedLruCache(int cap) {
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
```

## 方式3，继承 LinkedHashMap

需要设置 accessOrder = true，在元素被访问后会被移至队尾。

```java
public class LinkedHashMapCache extends LinkedHashMap {
    private int cap;

    public LinkedHashMapCache(int cap) {
        super(16, 0.75f, true);
        this.cap = cap;
    }

    @Override
    public boolean removeEldestEntry(Map.Entry eldest) {
        return this.cap < size();
    }
}
```
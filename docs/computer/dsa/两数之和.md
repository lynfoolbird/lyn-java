哈希表 是一种使用 哈希函数 组织数据，以支持快速插入和搜索的数据结构。

有两种不同类型的哈希表：哈希集合和哈希映射。

哈希集合 是 集合 的实现方式之一，用于存储 非重复值。
哈希映射 是 映射 的实现之一，用于存储 (key, value) 键值对。
在 标准模板库 的帮助下，哈希表是 易于使用的。大多数常见语言（如 Java，C ++ 和 Python）都支持哈希集合和哈希映射。

通过选择合适的哈希函数，哈希表可以在插入和搜索方面展现出 出色的性能。

# 两数之和

问题说明：

给定一个整数数组numbers，从数组中找出两个数满足相加之和等于目标数target，假设每个输入只对应唯一的答案，而且不可以重复使用相同的元素，返回两数的下标，以数组形式返回。

## 场景一：数组是无序数组

### 解法一：穷举法
```java
// 穷举法，时间复杂度O(n^2)，空间复杂度O(1)
public static int[] twoSum1(int[] numbers, int target) {
        for (int i=0; i<numbers.length; i++) {
            for (int j=i+1; j<numbers.length; j++) {
                if (numbers[i]+numbers[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }
```
### 解法二：以空间换时间
```java
// 以空间换时间，时间复杂度O(n),空间复杂度O(n)
    public static int[] twoSum2(int[] numbers, int target) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i=0; i<numbers.length; i++) {
            if (map.containsKey(target-numbers[i])) {
                return new int[]{map.get(target-numbers[i]), i};
            }
            map.put(numbers[i], i);
        }
        return new int[0];
    }

```

## 场景二：数组是有序数组

### 解法一：二分查找
```java
// 二分查找法
    public static int[] twoSum3binarySearch(int[] numbers, int target) {
        for (int i=0; i<numbers.length; i++) {
            int low = i;
            int high = numbers.length - 1;
            while (low <= high) {
                int mid = (high - low) / 2 + low;
                if (numbers[mid]==target-numbers[i] ) {
                    return new int[]{i, mid};
                } else if (numbers[mid]>target-numbers[i]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
        }

        return new int[0];
    }
```

### 解法二：双指针思想
```java
// 双指针思路
    public static int[] twoSum4twoPointer(int[] numbers, int target) {
        int low = 0;
        int high = numbers.length - 1;
        while (low < high) {
            int sum = numbers[low] + numbers[high];
            if (sum == target) {
                return new int[]{low, high};
            } else if (sum<target) {
                low++;
            } else {
                high--;
            }
        }
        return new int[0];
    }
```






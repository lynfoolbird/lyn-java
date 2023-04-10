# 1 二分查找算法

应用场景：有序数组的查找、部分有序考虑使用

方法一：双指针实现

```java
    public int binarySearch_1 (int[] nums, int target) {
        if (nums == null || nums.length==0) {
            return -1;
        }
        int low = 0;
        int high = nums.length - 1;
        int middle = 0;
        if (target < nums[low] || target > nums[high] || low > high) {
            return -1;
        }
        while (low <= high) {
//            middle = (low + high) / 2; // 不推荐，可能溢出
            middle = low + (high - low / 2);
            if (nums[middle] == target) {
                return middle;
            } else if (nums[middle] < target) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return -1;
    }
```

方法二：递归式

```java
    public static int binarySearch_2(int key, int[] array,int low, int high){
        //防越界
        if (key < array[low] || key > array[high] || low > high) {
            return -1;
        }
        int middle = (low + high) / 2;
        if(array[middle] > key){
            //大于关键字
            return  binarySearch_2(key, array, low, middle-1);
        } else if(array[middle]<key){
            //小于关键字
            return binarySearch_2(key, array, middle+1, high);
        } else {
            return middle;
        }
    }
```

# 2 编程练习

## 2.1  寻找峰值

[牛客网题目链接](https://www.nowcoder.com/practice/fcf87540c4f347bcb4cf720b5b350c76?tpId=295&tqId=2227748&ru=/exam/oj&qru=/ta/format-top101/question-ranking&sourceUrl=%2Fexam%2Foj)

给定一个长度为n的数组nums，请你找到峰值并返回其索引。数组可能包含多个峰值，在这种情况下，返回任何一个所在位置即可。

1.峰值元素是指其值严格大于左右相邻值的元素。严格大于即不能有等于

2.假设 nums[-1] = nums[n] = −∞−∞

3.对于所有有效的 i 都有 nums[i] != nums[i + 1]

4.你可以使用O(logN)的时间复杂度实现此问题吗？

如输入[2,4,1,2,7,8,4]时，会形成两个山峰，一个是索引为1，峰值为4的山峰，另一个是索引为5，峰值为8的山峰
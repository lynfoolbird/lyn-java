# 双指针



典例：合并有序数组，有序链表合并



## 合并有序数组

A和B都为升序数组且A空间可以容下B；升序数组合并从后向前遍历；

```java
    public void merge(int A[], int m, int B[], int n) {
        int i = m-1;
        int j = n-1;
        int k = m + n-1;
        while(i>=0 && j>=0) {
            if (A[i]>B[j]) {
                A[k--] = A[i--];
            } else {
                A[k--]=B[j--];
            }
        }
        if (i < 0) {
            while (j>=0) {
                A[k--] = B[j--];
            }
        }
    }
```
## BM93 盛水最多的容器

给定一个数组height，长度为n，每个数代表坐标轴中的一个点的高度，height[i]是在第i点的高度，请问，从中选2个高度与x轴组成的容器最多能容纳多少水

```java
 public int maxArea (int[] height) {
        //排除不能形成容器的情况
        if(height.length < 2)  return 0;
        int res = 0;
        //双指针左右界
        int left = 0;
        int right = height.length - 1;
        //共同遍历完所有的数组
        while(left < right){
            //计算区域水容量
            int capacity = Math.min(height[left], height[right]) * (right - left);
            //维护最大值
            res = Math.max(res, capacity);
            //优先舍弃较短的边
            if(height[left] < height[right])
                left++;
            else
                right--;
         }
        return res;
    }
```

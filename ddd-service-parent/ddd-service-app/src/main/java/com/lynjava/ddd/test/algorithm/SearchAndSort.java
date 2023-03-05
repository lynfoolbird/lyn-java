package com.lynjava.ddd.test.algorithm;

import java.util.Arrays;

/**
 * 查找与排序 算法
 */
public class SearchAndSort {
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5,6};
        int key = 5;
        System.out.println(binarySearch_1(key, arr));
        System.out.println(binarySearch_2(key, arr, 0, arr.length-1));

        int[] arr2 = {1,3,5,4};
        bubbleSort(arr2);
        System.out.println(Arrays.toString(arr2));
    }

    // 二分查找，适用顺序存储，有序
    /**
     * 循环实现二分算法
     */
    public static int binarySearch_1(int key, int[] array) {
        int low = 0; //第一个下标
        int high = array.length - 1;//最后一个下标
        int middle = 0;
        //防越界
        if (key < array[low] || key > array[high] || low > high) {
            return -1;
        }
        while (low <= high) {
//            middle = (low + high) / 2;
            middle = low + (high - low / 2);
            if (array[middle] == key) {
                return middle;
            } else if (array[middle] < key) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return -1;
    }

    /**
     * 递归实现二分算法
     */
    public static int binarySearch_2(int key,int[] array,int low,int high){
        //防越界
        if (key < array[low] || key > array[high] || low > high) {
            return -1;
        }
        int middle = (low+high)/2;
        if(array[middle]>key){
            //大于关键字
            return  binarySearch_2(key,array,low,middle-1);
        }else if(array[middle]<key){
            //小于关键字
            return binarySearch_2(key,array,middle+1,high);
        }else{
            return middle;
        }
    }

    // 应用：部分有序数组，不重复，先升序后降序，求最大值
    // 方法一：可以用快排，时间复杂度是O(nlogn)
    // 方法二：借鉴二分查找算法，只要比较中间元素与其下一个元素大小即可
    private static int findLargestNumInArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) / 2);
            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return nums[left];
    }

    public static void bubbleSort(int[] arr) {
        boolean flag = false;
        int temp = 0;
        for(int i=0;i<arr.length-1;i++){//冒泡趟数，n-1趟
            for(int j=0;j<arr.length-i-1;j++){
                if(arr[j+1]<arr[j]) {
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    flag = true;
                }
            }
            if(!flag){
                break;//若果没有发生交换，则退出循环
            }
        }
    }
}

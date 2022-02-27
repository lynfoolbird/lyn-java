package com.lynjava.ddd.test.algorithm.leecode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyanan on 2017/3/22.
 */
public class TwoSum {

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5,6};
        System.out.println(Arrays.toString(twoSum1qiongju(nums, 10)));
        System.out.println(Arrays.toString(twoSum2(nums, 10)));
        System.out.println(Arrays.toString(twoSum3binarySearch(nums, 10)));
        System.out.println(Arrays.toString(twoSum4twoPointer(nums, 10)));

    }

    // 穷举法，时间复杂度O(n^2)，空间复杂度O(1)
    public static int[] twoSum1qiongju(int[] numbers, int target) {
        for (int i=0; i<numbers.length; i++) {
            for (int j=i+1; j<numbers.length; j++) {
                if (numbers[i]+numbers[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }
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

    // 双指针思路，时间复杂度O(1)，空间复杂度O(1)  最优解
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

}



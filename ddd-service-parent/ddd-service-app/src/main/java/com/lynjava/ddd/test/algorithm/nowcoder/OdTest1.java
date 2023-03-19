package com.lynjava.ddd.test.algorithm.nowcoder;

import lombok.Data;

import java.util.Arrays;
import java.util.Objects;

public class OdTest1 {

    public static void main(String[] args) {
        Node node5 = new Node(5, null);
        Node node4 = new Node(4, node5);
        Node node3 = new Node(3, node4);
        Node node2 = new Node(2, node3);
        Node header1 = new Node(1, node2);

        Node nodet2 = new Node(4, null);
        Node header2 = new Node(2, nodet2);
        jiaojiOfLinkList(header2, header1);

        int[] arr = new int[]{7,1,5,4,3,1};
        System.out.println(teset(arr, arr.length));
        System.out.println(maxProfit(arr));
    }

    /**
     * OD二面：股票最大收益
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        if(prices.length == 0 )
            return 0;
        int[] dp = new int [prices.length +  2];
        Arrays.fill(dp,0);
        int min = prices[0];
        for( int i=1; i<prices.length; i++){
            if(prices[i-1] < min) {
                min = prices[i-1];
            }
            dp[i] = prices[i] - min;
        }
        int ans = 0 ;
        for(int i=0; i<prices.length; i++){
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    private static int teset(int[] arr, int n) {
        int tmp = 0;
        for (int i=0;i<arr.length-1;i++) {
            for (int j=i+1;j<arr.length;j++) {
                if (arr[j] -arr[i] > tmp) {
                    tmp = arr[j] - arr[i];
                }
            }
        }
        return tmp;
    }

    /**
     * OD一面：两个有序链表的交集
     * @param list1
     * @param list2
     */
    public static void jiaojiOfLinkList(Node list1, Node list2) {
        if (list1==null || list2==null) {
            return;
        }
        Node p1 = list1;
        Node p2 = list2;
        while (p1!=null && p2!=null) {
            Integer value1 = p1.getValue();
            Integer value2 = p2.getValue();
            if (Objects.equals(value1, value2)) {
                System.out.println(value1);
                p1 = p1.getNext();
                p2 = p2.getNext();
            } else if (value1 > value2) {
                p2 = p2.getNext();
            } else {
                p1 = p1.getNext();
            }
        }
    }

    // 合并有序链表
    public static void mergeOfLinkList(Node list1, Node list2) {

    }

    /**
     * OD上机题目3：二维数组，值为0或1，若为1则每天可感染上下左右值为1，求数组值全为0或全为1时需要多少天
     * @param arr
     * @param n
     */
    private static void ganran2(char[][] arr, int n) {
        int j= 0;
        int cnt = 0;
        for (int i=0; i<n; i+=2) {
            if (isValidArr(arr, n)) {
                break;
            }
            cnt++;
            if (arr[i][j] != '1') {
                continue;
            }
            if (j-1>=0) {
                arr[i][j - 1] = '1';
            }
            if (j+1<n) {
                arr[i][j + 1] = '1';
            }
            if (i-1>=0) {
                arr[i - 1][j] = '1';
            }
            if (i+1<n) {
                arr[i + 1][j] = '1';
            }
            j += 2;
        }
        System.out.println(cnt);
    }

    private static boolean isValidArr(char[][] arr, int n) {
        char ch = arr[0][0];
        for (int i=0; i<n; i++) {
            for (int j=0;j<n;j++) {
                if (arr[i][j] != ch) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * OD上级题目2：n个连续数字，其和为sum，打印这n个连续数字
     * @param sum
     * @param n
     */
    private static void findSequence(int sum, int n) {
        if ((sum<=0||sum>=100000) || (n<=0 || n>=100000)) {
            System.out.println(-1);
            return;
        }
        if (sum < n) {
            System.out.println(-1);
            return;
        }
        int a1 = (sum - n*(n-1)/2)/n;
        int sn = n*a1 + n*(n-1)/2;
        if (sn == sum) {
            for (int i=0; i<n;i++) {
                System.out.print((a1+i) +" ");
            }
            System.out.println();
            return;
        }
        System.out.println(-1);
    }

    /**
     * OD上级题目1：字符串消消乐，若有连续字符相同则删除直到最后没有相同连续字符为止，打印最后的字符串
     * @param sb
     * @return
     */
    private static String test2(StringBuilder sb) {
        if (sb.length()<2) {
            return sb.toString();
        }
        while (!isMatch(sb.toString())) {
            for (int i=1;i<sb.length();i++) {
                if (sb.charAt(i) == sb.charAt(i-1)) {
                    sb.deleteCharAt(i);
                    sb.deleteCharAt(i-1);
                }
            }
        }
        return sb.toString();
    }

    private static boolean isMatch(String str) {
        if (str==null || str.length()<=1) {
            return true;
        }
        char[] arr = str.toCharArray();
        for (int i=0; i<str.length()-1;i++) {
            if (arr[i] == arr[i+1]) {
                return false;
            }
        }
        return true;
    }

    @Data
    public static class Node {
        private Integer value;
        private Node next;

        public Node(Integer value, Node next) {
            this.value = value;
            this.next = next;
        }
    }
}

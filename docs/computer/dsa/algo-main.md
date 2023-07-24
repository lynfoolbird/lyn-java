算法：

labuladong的算法小抄 算法框架思维

牛客算法必刷101题，华为OD高频题，牛客+力扣+其他(剑指offer等)
https://www.nowcoder.com/exam/oj

华为机考经验贴~仅供参考！非官方！

机试政策

机试总共三题，前两题每题100分，第三题200分。             机考时长：2.5小时
机试可选语言：C/C++、java、python、js、go等
答题平台： 牛客。收到机考链接后，一周内完成答题，提前准备带有摄像头的电脑（练题，体验机试很重要！！很多人选习惯了力扣的刷题模式，没有提前适应牛客会考0分！）

刷题攻略

在 牛客 刷题：   https://www.nowcoder.com/ta/huawei

在 力扣 刷题：  https://leetcode-cn.com/problemset/all/

在力扣上找高频出现的中级难度的题目
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

二叉树：先序中序后序，递归及迭代方式实现， 重建二叉树；层序，之序；...  路径 BFS DFS  回溯法 递归思想

简单正则：
```java
String reg = "[WASD][0-9]{1,2}";
```

# 水仙花数

```java
 public static void shuixianhua() {
        for (int i=100; i<1000; i++) {
            int bai = i / 100;
            int shi = (i % 100) / 10;
            int ge = i % 10;
            if (Math.pow(bai, 3) + Math.pow(shi,3) +Math.pow(ge, 3) == i) {
                System.out.println("水仙花数：" + i);
            }
        }
    }
```

# 字符串分隔

```java
// 补零   
public static void stringsp() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String st = sc.nextLine();
            StringBuilder sb = new StringBuilder();
            sb.append(st);
            int sz = st.length();
            int needZero = 8 - sz % 8;
            while (needZero>0) {
                sb.append("0");
                needZero--;
            }
            int t = 0;
            for (int j=0;j<sb.length()/8 ; j++) {
                String ss = sb.substring(t, t + 8);
                System.out.println(ss);
                t += 8;
            }
        }
        sc.close();
    }
```

# OD上机题目1:字符串消消乐，若有连续字符相同则删除直到最后没有相同连续字符为止，打印最后的字符串

```java
 // OD上级题目1：字符串消消乐，若有连续字符相同则删除直到最后没有相同连续字符为止，打印最后的字符串
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
```





# OD上机题目2: n个连续数字，其和为sum，打印这n个连续数字

```java
// OD上级题目2：n个连续数字，其和为sum，打印这n个连续数字
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
```

# OD上机题目3:二维数组，值为0或1，若为1则每天可感染上下左右值为1，求数组值全为0或全为1时需要多少天

```java
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            String[] strArr = str.split(",");
            int len = strArr.length;
            int n = Double.valueOf(Math.sqrt(len)).intValue();
            if (n<1 || n>200 || n*n!=len) {
                System.out.println(-1);
                break;
            }
            List list = Arrays.asList("0", "1");
            char[][] arr = new char[n][n];
            for (int t=0; t<len; t++) {
                if (!list.contains(strArr[t])) {
                    System.out.println("-1");
                    break;
                }
                int i = t / n;
                int j = t % n;
                arr[i][j] = strArr[t].toCharArray()[0];
            }
            if (isValidArr(arr, n)) {
                System.out.println("-1");
                break;
            }
            ganran2(arr, n);
        }
        scanner.close();
    }

    // OD上机题目3：二维数组，值为0或1，若为1则每天可感染上下左右值为1，求数组值全为0或全为1时需要多少天
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
}

```


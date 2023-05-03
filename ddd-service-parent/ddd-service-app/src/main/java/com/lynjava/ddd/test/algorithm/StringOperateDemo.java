package com.lynjava.ddd.test.algorithm;

import java.util.Stack;

public class StringOperateDemo {

    /**
     * BM83 字符串变形
     * 方法一：先整体翻转，再按单词翻转
     * 方法二：借助栈
     *
     * @param s
     * @param n
     * @return
     */
    public static String trans(String s, int n) {
    if (n == 0) return s;
    StringBuffer res = new StringBuffer();
    for (int i = 0; i < n; i++) {
        //大小写转换
        if (s.charAt(i) <= 'Z' && s.charAt(i) >= 'A')
            res.append((char)(s.charAt(i) - 'A' + 'a'));
        else if (s.charAt(i) >= 'a' && s.charAt(i) <= 'z')
            res.append((char)(s.charAt(i) - 'a' + 'A'));
        else
            //空格直接复制
            res.append((char)(s.charAt(i)));
    }
    Stack<String> temp = new Stack<String>();
    for (int i = 0; i < n; i++) {
        int j = i;
        //以空格为界，分割单词
        while (j < n && res.charAt(j) != ' ')
            j++;
        //单词进栈
        temp.push((String)(res.substring(i, j)));
        i = j;
    }
    //排除结尾空格的特殊情况
    if (s.charAt(n - 1) == ' ')
        res = new StringBuffer(" ");
    else
        res = new StringBuffer();
    //栈遵循先进后厨，单词顺序是反的
    while (!temp.empty()) {
        res.append(temp.peek());
        temp.pop();
        if (!temp.empty())
            res.append(" ");
    }
    return res.toString();
    }

    /**
     * BM86 大数加法
     * @param s
     * @param t
     * @return
     */
    public static String bigNumAdd (String s, String t) {
        //若是其中一个为空，返回另一个
        if (s.length() <= 0)
            return t;
        if (t.length() <= 0)
            return s;
        //让s为较长的，t为较短的
        if (s.length() < t.length()) {
            String temp = s;
            s = t;
            t = temp;
        }
        int carry = 0; //进位标志
        char[] res = new char[s.length()];
        //从后往前遍历较长的字符串
        for (int i = s.length() - 1; i >= 0; i--) {
            //转数字加上进位
            int temp = s.charAt(i) - '0' + carry;
            //转较短的字符串相应的从后往前的下标
            int j = i - s.length() + t.length();
            //如果较短字符串还有
            if (j >= 0)
                //转数组相加
                temp += t.charAt(j) - '0';
            //取进位
            carry = temp / 10;
            //去十位
            temp = temp % 10;
            //修改结果
            res[i] = (char)(temp + '0');
        }
        String output = String.valueOf(res);
        //最后的进位
        if (carry == 1)
            output = '1' + output;
        return output;
    }
}

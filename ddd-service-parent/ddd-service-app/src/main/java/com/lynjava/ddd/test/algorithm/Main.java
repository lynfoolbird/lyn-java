package com.lynjava.ddd.test.algorithm;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
//        validIp();

        Integer i = 10;
        System.out.println(Integer.toBinaryString(10));
        System.out.println("helo".toCharArray());

//        String st = "123456";
//        System.out.println(st.substring(5, 6));
//        Scanner sc = new Scanner(System.in);
//        String str =sc.nextLine().toLowerCase();
//        String s = sc.nextLine();
//        char[] strArr = str.toCharArray();
//        int cnt = 0;
//        for (int i=0;i<str.length(); i++) {
//            if (s.equalsIgnoreCase(String.valueOf(strArr[i]))) {
//                cnt++;
//            }
//        }
//        System.out.print(cnt);
//        shuixianhua();
    }

    public static void validIp() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String st = sc.nextLine();
            String[] strArr = st.split("\\.");
            boolean flag = true;
           if (strArr.length == 4) {
               for (String s : strArr) {
                   int num = Integer.parseInt(s);
                   if (num<0 || num>255) {
                       flag = false;
                       break;
                   }
               }
           } else {
               flag = false;
           }
            System.out.println(flag ? "YES" : "NO");
        }
        sc.close();
    }

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
}

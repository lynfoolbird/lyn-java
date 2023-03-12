package com.lynjava.ddd.test.algorithm.nowcoder;

import java.io.File;

/**
 * 目录树操作
 */
public class ContentsTree {
    public static void main(String[] args) {
        File dir = new File("d:/a");
        System.out.println(dir.getName());
        listChilds(dir,1);
    }

    public static void listChilds(File f,int level) {
        String prefix = "";
        for(int i=0;i<level;i++) {
            prefix = "|  " + prefix;
        }
        File[] files = f.listFiles();
        for (File file : files) {
            if(file.isDirectory()) {
                System.out.println(prefix + file.getName());
                listChilds(file,level+1);
            } else {
                System.out.println(prefix + file.getName());
            }
        }
    }

    public static void rm(File f) {
        if(!f.exists()){
            System.out.println("file not found!");
            return;
        } else if(f.isFile()) {
            f.delete();
            return;
        }

        File[] dir = f.listFiles();
        for(File file : dir) {
            rm(file);
        }
        f.delete();
    }
}

# 简单使用

1、使用jps命令查看java进程

```
jps -m|findstr AppApplication
```

2、启动arthas进程，输入上面进程id对应的序号，然后执行sc、watch、trace、shutdown等命令，按q退回到命令行界面。

```
java -jar arthas-boot.jar
```

![img](images/arthas-demo.png)
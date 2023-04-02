[JDK的前世今生：细数 Java5 - 15 的那些经典特性](https://www.toutiao.com/a6874008727758832131/?channel=&source=search_tab)

各版本主要特性

协程、虚拟线程

LTS版本有8、11、17

# 语法糖

语法糖（Syntactic sugar），也译为糖衣语法，是由英国计算机科学家彼得·兰丁发明的一个术语，指计算机语言中添加的某种语法，这种语法对语言的功能没有影响，但是更方便程序员使用。语法糖让程序更加简洁，有更高的可读性。

java中的语法糖只存在于编译期, 在编译器将 .java 源文件编译成 .class 字节码时, 会进行解语法糖操作, 还原最原始的基础语法结构。

这些语法糖包含条件编译、断言、Switch语句与枚举及字符串结合、可变参数、自动装箱/拆箱、枚举、内部类、泛型擦除、增强for循环、lambda表达式、try-with-resources语句、JDK10的局部变量类型推断等等。

举例：

```java
//字符串拼接
public void stringBuilderTest(int end) {
    char[] foo = new char[]{'@', 'a', '*'};
    char ch;
    int x = 0;
    while ((ch = foo[++x]) != '*') {
        System.out.println("" + x + ": " + ch);
    }
}
```

命令行: java -jar cfr_0_132.jar CFRDecompilerDemo.class --stringbuilder false

![img](images/syntactic-sugar-example.png)

从反编译后的代码中能看出, 当我们使用+号进行字符串拼接操作时, 编译时会自动创建一个StringBuilder对象。所以当在循环中拼接字符串时, 应避免使用+号操作, 否则每次循环都会创建一个StringBuilder对象再回收, 造成较大的开销。

[[浅析java中的语法糖](https://www.cnblogs.com/qingshanli/p/9375040.html)](https://www.cnblogs.com/qingshanli/p/9375040.html)

# JDK19

[虚拟线程](https://zhuanlan.zhihu.com/p/579732019)

平台线程Thread的一些特点：

- Java 中的线程是对操作系统线程的一个简单包装，线程的创建，调度和销毁等都是由操作系统完成；（用户态，内核态切换）
- Java线程和系统线程是一一对应的关系；
- 线程切换需要消耗 CPU 时间，这部分时间是与业务无关的；
- 线程的性能直接受操作系统处理能力的影响；

因此，通过线程的特点可以发现线程是一种重量级的资源，作为 Java 程序员应该深有体会。为了更好的管理线程，Java 采用了池化（线程池）的方式进行管理线程，避免线程频繁创建和销毁带来的开销。尽管线程池避免了线程大部分创建和销毁的开销，但是线程的调度还是直接受操作系统的影响，那么有没有更好的方式来打破这种限制呢？因此，虚拟线程就孕育而生。

在现有的线程模型下，一个 Java 线程相当于一个操作系统线程，多个虚拟线程需要挂载在一个平台线程（载体线程）上，每个平台线程和系统线程一一对应。因此，VirtualThread 是属于 JVM 级别的线程，由 JVM 调度，它是非常轻量级的资源，使用完后立即被销毁，因此就不需要像平台线程一样使用池化（线程池）。虚拟线程在执行到 IO 操作或 Blocking 操作时，会自动切换到其他虚拟线程执行，从而避免当前线程等待，可以高效通过少数线程去调度大量虚拟线程，最大化提升线程的执行效率。

- 虚拟线程是将 Thread 作为载体线程，它并没有改变原来的线程模型；
- 虚拟线程是 JVM 调度的，而不是操作系统调度；
- 使用虚拟线程可以显著提高程序吞吐量；
- 虚拟线程适合 并发任务数量很高 或者 IO 密集型的场景，对于 计算密集型任务还需通过过增加 CPU 核心解决，或者利用分布式计算资源来来解决；



# JDK17

switch模式匹配

内部强封装

增强伪随机数生成器

弃用安全管理器

# JDK16

instanceof模式匹配转正

record关键字转正

jpackage打包工具转正

弹性元空间

ZGC改进

Vector矢量计算API

# JDK15

准备禁用偏向锁

ZGC转正

Sealed Class封闭类

Hidden Class隐藏类

文本功能转正

Record二次预览

# JDK14 

switch表达式标准化

instanceof模式匹配预览

Record类型预览

删除CMS垃圾收集器

# JDK11

标准化httpclient

ZGC可伸缩低延迟垃圾收集器

# JDK10

局部变量类型推断 var关键字；

G1垃圾收集器

# JDK9

java模块系统moduleinfo

接口支持私有方法

Stream、Optional、try-with-resource改进

JShell

# JDK8

拉姆达表达式；函数式接口；方法引用

stream API; Optional

并发包优化: HashMap/ConcurrentHashMap

接口支持静态方法默认方法

JVM新特性：元空间

# JDK7

try-with-resouce；catch多个异常；ForkJoin框架；switch支持String

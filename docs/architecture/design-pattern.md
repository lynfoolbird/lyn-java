设计原则/设计模式/重构

https://blog.csdn.net/weixin_43122090/article/details/105462226

[设计模式常见面试题汇总](https://www.cnblogs.com/dailyprogrammer/articles/12272717.html)

面试资料：csdn 115085887  设计模式速成版 ，csdn 120454098

书籍：设计模式之禅

常用设计模式

Spring中设计模式

jdk中设计模式：如IO流中使用的装饰者模式

代理模式与装饰者模式区别、工厂模式区别

如何用自己的语言表达出来



# 设计原则

OOP  AOP

SOLIDCD

单一职责原则、开闭原则、里式替换原则（基类能出现的地方均可替换成子类）、接口隔离原则、依赖倒置原则、合成复用原则（优先使用组合聚合关系其次才考虑继承关系）、迪米特法则（降低类间依赖、最少知识）

高内聚低耦合

# 设计模式

创建型、结构型、行为型

常用：单例、工厂、代理、模板方法、观察者、建造者、策略模式、适配器模式

## 单例模式

一般有五种写法，懒汉式、恶汉式、DCL、枚举、内部类。一般情况下直接使用饿汉式就行，如果明确要求要懒加载会倾向于使用静态内部类，如果涉及到反序列化创建对象时会试着使用枚举的方式来实现单例。

```java
// 恶汉式
public class Singleton {
    // 类加载时就初始化
    private static final Singleton instance = new Singleton();    
    private Singleton(){} // 构造器私有

    public static Singleton getInstance(){
        return instance;
    }
}
// 懒汉式
public class Singleton {
    private static Singleton instance;
    private Singleton (){}

    public synchronized static Singleton getInstance() {
     if (instance == null) {
         instance = new Singleton();
     }
     return instance;
    }
}
// 双检锁
public class Singleton {
    private volatile static Singleton instance; //声明成 volatile，禁止指令重排
    private Singleton (){}

    public static Singleton getSingleton() {
        if (instance != null) {
            return instance;
        }
        synchronized (Singleton.class) {
            if (instance == null) {       
                instance = new Singleton();
            }
        }
        return instance;
    }   
}
// 静态内部类
public class Singleton {  
    private static class SingletonHolder {  
        private static final Singleton INSTANCE = new Singleton();  
    }  
    private Singleton (){}  
    public static final Singleton getInstance() {  
        return SingletonHolder.INSTANCE; 
    }  
}
// 枚举式
public enum EasySingleton{
    INSTANCE;
}
```

## 工厂模式

简单工厂、工厂方法、抽象工厂

简单工厂：用来生产同一等级结构中任意产品，不支持拓展增加产品

工厂方法：用来生产同一等级结构中的固定产品，支持拓展增加产品

抽象工厂：用来生产不同产品族的全部产品，不支持拓展增加产品，支持增加产品族

### 简单工厂

![img](images/factory-pattern-simple-factory.png)

```java
public interface Phone {
    void make();
}
public class MiPhone implements Phone {
    public MiPhone() {
        this.make();
    }
    @Override
    public void make() {       
        System.out.println("make xiaomi phone!");
    }
}
public class IPhone implements Phone {
    public IPhone() {
        this.make();
    }
    @Override
    public void make() {       
        System.out.println("make iphone!");
    }
}
public class PhoneFactory {
    public Phone makePhone(String phoneType) {
        if(phoneType.equalsIgnoreCase("MiPhone")){
            return new MiPhone();
        }
        else if(phoneType.equalsIgnoreCase("iPhone")) {
            return new IPhone();
        }
        return null;
    }
}
public class Demo {
    public static void main(String[] arg) {
        PhoneFactory factory = new PhoneFactory();
        Phone miPhone = factory.makePhone("MiPhone");            // make xiaomi phone!
        IPhone iPhone = (IPhone)factory.makePhone("iPhone");    // make iphone!
    }
}
```

### 工厂方法

![img](images/factory-pattern-factory-method.png)

```java
public interface AbstractFactory {
    Phone makePhone();
}
public class XiaoMiFactory implements AbstractFactory{
    @Override
    public Phone makePhone() {
        return new MiPhone();
    }
}
public class AppleFactory implements AbstractFactory {
    @Override
    public Phone makePhone() {
        return new IPhone();
    }
}
public class Demo {
    public static void main(String[] arg) {
        AbstractFactory miFactory = new XiaoMiFactory();
        AbstractFactory appleFactory = new AppleFactory();
        miFactory.makePhone();            // make xiaomi phone!
        appleFactory.makePhone();        // make iphone!
    }
}
```

### 抽象工厂

![img](images/factory-pattern-abstract-factory.png)

```java
public interface AbstractFactory {
    Phone makePhone();
    PC makePC();
}
public class XiaoMiFactory implements AbstractFactory{
    @Override
    public Phone makePhone() {
        return new MiPhone();
    }
    @Override
    public PC makePC() {
        return new MiPC();
    }
}
public class AppleFactory implements AbstractFactory {
    @Override
    public Phone makePhone() {
        return new IPhone();
    }
    @Override
    public PC makePC() {
        return new MAC();
    }
}
public class Demo {
    public static void main(String[] arg) {
        AbstractFactory miFactory = new XiaoMiFactory();
        AbstractFactory appleFactory = new AppleFactory();
        miFactory.makePhone();            // make xiaomi phone!
        miFactory.makePC();                // make xiaomi PC!
        appleFactory.makePhone();        // make iphone!
        appleFactory.makePC();            // make MAC!
    }
}
```


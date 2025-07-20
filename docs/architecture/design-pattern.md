设计原则/设计模式/重构

https://blog.csdn.net/weixin_43122090/article/details/105462226

[设计模式常见面试题汇总](https://www.cnblogs.com/dailyprogrammer/articles/12272717.html)

面试资料：csdn 115085887  设计模式速成版 ，csdn 120454098

书籍：设计模式之禅

常用设计模式

Spring中设计模式

jdk中设计模式：如IO流中使用的装饰者模式

代理模式与装饰者模式区别、工厂模式区别

项目开发中使用的设计模式

如何用自己的语言表达出来



# 设计原则

OOP  AOP

SOLIDCD

单一职责原则、开闭原则、里式替换原则（基类能出现的地方均可替换成子类）、接口隔离原则、依赖倒置原则、合成复用原则（优先使用组合聚合关系其次才考虑继承关系）、迪米特法则（降低类间依赖、最少知识）

复用原则、简单原则

高内聚低耦合

# 设计模式

创建型、结构型、行为型

常用：单例、工厂、代理、模板方法、观察者、建造者、策略模式、适配器模式、责任链模式

## 单例模式

一般有五种写法，懒汉式、饿汉式、DCL、枚举、内部类。一般情况下直接使用饿汉式就行，如果明确要求要懒加载会倾向于使用静态内部类，如果涉及到反序列化创建对象时会试着使用枚举的方式来实现单例。

饿汉式在类加载时初始化，懒汉式在首次使用时创建对象，懒汉式如果不加synchronized修饰，DCL如果不加volatile修饰均可能存在线程安全问题。

```java
// 饿汉式
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

简单工厂：所有的产品都由一个工厂生产。如果你想加产品，就改源代码。

工厂方法：有多个工厂，但一个工厂只生产一种产品。如耐克厂只生产耐克鞋，阿迪厂只生产阿
迪鞋。如果你想加产品，那就加工厂类。用来生产同一等级结构中的固定产品，支持拓展增加产品

抽象工厂：有多个工厂，每个工厂可以生产多类产品。如耐克厂可以生产耐克鞋和耐克衣服，阿
迪厂可以生产阿迪鞋和阿迪衣服。但如果你想生产帽子，那么就需要在抽象工厂里加抽象方法，
然后修改所有的子类。 用来生产不同产品族的全部产品，不支持拓展增加产品，支持增加产品族

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



# 装饰器模式和代理模式的区别
共同点：
效果类似：装饰器模式和代理模式都可以在方法的前后增加逻辑
可以嵌套：代理可以层层代理，装饰也可以层层装饰

区别：
实现方式不同：代理类和原有类之间并没有什么直接关联。就像我找中介帮我卖房子，我和中介
之间并没有什么直接关系，我出钱他办事，我是我，他是他。但是装饰类和被装饰类之间就有关
系，他们有共同的父类。比如BufferReader装饰了FileReader，他们有共同的父类Reader。

应用场景不同：代理模式主要用来做AOP，适用于同时对好多方法做切面。就像中介也不可能只
帮我一个人卖房子。 而装饰器只专注于装饰一个类，BufferReader只专注于装饰Reader。 

代理模式中，目标类对于客户端是透明的，由代理类隐藏其具体信息并响应客户端请求，用于实现访问控制；而装饰者中客户端对特定的目标类对象进行增强。所以，代理类与真实对象的关系常在编译时就确定，而装饰者在运行时递归构造。

# 装饰者模式

装饰模式提供了一种在运行时动态地为对象添加新功能的方法，通过创建一个装饰类来包装原始类。装饰类具有与原始类相同的接口，它内部包含一个指向原始对象的引用，并且可以根据需要包装额外的功能。这样，你可以通过组合不同的装饰类来构建出具有不同功能组合的对象。装饰模式的优点包括避免了类爆炸问题，因为你可以通过组合少量的装饰类来实现各种功能组合。它也使得功能的增加和修改更加灵活，不会影响到其他部分的代码。然而，装饰模式可能会导致增加很多小型的类，从而增加了代码的复杂性。为类动态增加功能，比继承更灵活。

```java
// IO流 装饰器模式为FileReader增加了Buffer功能
BufferedReader in = new BufferedReader(new FileReader("filename.txt"));
String s = in.readLine();

// 芒果-珍珠-双份柠檬-红茶
Beverage beverage = new Mongo(new Pearl(new Lemon(new Lemon(new BlackTea()))));
```

# 策略模式

策略模式是一种行为设计模式，它使你能在运行时改变算法。在Spring Boot应用中，利用策略模式可以灵活地切换不同的业务逻辑实现，而无需修改代码。本文将介绍如何在Spring Boot项目中实现策略模式，并通过ID来决定调用哪个具体的服务类。

**优点**：

1. 策略模式使得算法的变化独立于使用算法的客户，可以灵活地更换或添加新的算法。
2. 避免了使用多重条件语句，提高了代码的可读性和可维护性。

**缺点**：

1. 如果策略类很多，可能会导致客户端代码中出现许多策略类的引用，增加了系统的复杂性。
2. 客户端必须了解不同的策略类，并选择合适的策略。

# 模版方法

模板模式（Template Method Pattern）是一种行为设计模式，它定义了一个算法的框架，并允许子类在不改变算法结构的情况下重新定义算法的某些步骤。模板模式通过将算法的通用部分放在父类中，而将可变部分留给子类来实现。

#### 代码复用

模板模式的核心是将算法的通用部分放在父类中实现，而将可变的部分留给子类去实现。通过这种方式，模板模式实现了**代码复用**，同时提供了灵活性。

#### 符合开闭原则

符合开闭原则，开闭原则（Open/Closed Principle）要求软件实体（类、模块、函数等）对扩展开放，对修改关闭。**模板模式通过将可变的部分抽象出来，允许子类扩展算法的某些步骤，而无需修改父类的代码。** 这符合开闭原则，提高了系统的可维护性和可扩展性。 示例： 在 Java 的 `HttpServlet` 中，`service()` 方法定义了处理 HTTP 请求的框架，但具体的 `doGet()` 和 `doPost()` 方法由子类实现。如果需要支持新的 HTTP 方法，只需添加新的方法，而无需修改 `service()` 方法。

# 适配器模式

适配器模式通过引入一个适配器类来充当中间人，将一个接口转换成另一个接口，使得两个不兼容的对象能够协同工作。适配器类包含一个对不兼容接口的引用，并实现了你期望的目标接口。这样，当你需要使用目标接口的时候，可以通过适配器来调用原本不兼容的类的方法。适配器模式的应用可以使得现有的代码与新代码能够无缝协同工作，从而提高了代码的可重用性。它允许你将不同系统、库或组件整合在一起，而无需对现有代码进行大量修改。然而，适配器模式也可能引入一些复杂性，因为你需要维护适配器类和处理不同接口之间的映射关系。

# 责任链模式

责任链模式，简而言之，就是将多个操作组装成一条链路进行处理。请求在链路上传递，链路上的每一个节点就是一个处理器，每个处理器都可以对请求进行处理，或者传递给链路上的下一个处理器处理。责任链模式主要是解耦了请求与处理，客户只需将请求发送到链上即可，无需关心请求的具体内容和处理细节，请求会自动进行传递直至有节点对象进行处理。


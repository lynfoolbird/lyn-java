反射 类加载 ClassLoader、Class、Constructor、Method、Field

泛型

注解原理

动态代理：静态代理、动态代理 jdk、cglib 比较 性能; 应用AOP

SPI：jdk spi、spring spi、dubbo spi

Spring扩展

https://www.zhihu.com/question/485969873/answer/2337075116

字节码技术 javaassist



# 注解反射

ClassLoader、Class、Constructor、Field、Method、ReflectionUtils

spring boot注解：条件注解、复合注解

# 动态代理

## JDK动态代理
demo代码

```java
// 外部类，用于演示mybatis场景（Dao接口原理）
public class Session {
    public List<String> select() {
        return Arrays.asList("one", "two", "three");
    }
}
// 接口类
public interface ITestPrint {
    List<String> sayHello(String str);
}
// JDK动态代理需要实现InvocationHandler
public class TestProxy<T> implements InvocationHandler {
    private Session session; // 用于演示mybatis场景
    private String methodName; // 用于演示mybatis场景
    /**
     * 若是代理接口，则target字段为Class对象；
     * 若是代理接口的实现类，则target字段值为实现类实例
     */
    private T target; // 目标对象

    public TestProxy(T target, Session session, String methodName) {
        this.target = target;
        this.session = session;
        this.methodName = methodName;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("pre handler..."); // 前置处理逻辑
        Object obj = null;
        String fullMethodName = method.getDeclaringClass().getName()+"."+method.getName();
        System.out.println("method's fullName=" + fullMethodName);
        System.out.println("method's params=" + Arrays.toString(args));
        System.out.println("method's returnType=" + method.getReturnType());
        if (target instanceof Class) {
            if ("SELECT".equals(this.methodName)) {
                obj = this.session.select();
            }
        } else { // 调用目标对象方法
            obj = method.invoke(target, args);
        }
        System.out.println(obj);
        System.out.println("post handler");  // 后置处理逻辑
        return obj;
    }
}
// 代理工厂类
public class ProxyFactory {
    // 生成代理对象（目标是接口类场景）
    public static  <T> T newInstance(Class<T> clazz, Session session) {
        TestProxy<T> testProxy = new TestProxy(clazz, session, "SELECT");
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},testProxy);
    }
    // 生成代理对象（目标是接口类的实例对象场景）
    public static Object newInstance(Object obj, Session session) {
        TestProxy testProxy = new TestProxy(obj, session, "SELECT");
        return  Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),testProxy);
    }
}
// 测试
public class Client {
    public static void main(String[] args) {
        Session session = new Session();
        ITestPrint print = ProxyFactory.newInstance(ITestPrint.class, session);
        print.sayHello("pp");

        ITestPrint pIns = new ITestPrint() {
            public List<String> sayHello(String str) {
                System.out.println(str);
                return Arrays.asList(str);
            }
        };
        ITestPrint print2 = (ITestPrint) ProxyFactory.newInstance(pIns, session);
        print2.sayHello("abcdefg");
    }
}

```

## Cglib动态代理

# SPI
SPI，简单来说，就是service provider interface，说白了是什么意思呢，比如你有个接口，现在这个接口有3个实现类，那么在系统运行的时候对这个接口到底选择哪个实现类呢？这就需要spi了，需要根据指定的配置或者是默认的配置，去找到对应的实现类加载进来，然后用这个实现类的实例对象。

接口A -> 实现A1，实现A2，实现A3

配置一下，接口A = 实现A2

在系统实际运行的时候，会加载你的配置，用实现A2实例化一个对象来提供服务。

比如说你要通过jar包的方式给某个接口提供实现，然后你就在自己jar包的META-INF/services/目录下放一个跟接口同名的文件，里面指定接口的实现里是自己这个jar包里的某个类。ok了，别人用了一个接口，然后用了你的jar包，就会在运行的时候通过你的jar包的那个文件找到这个接口该用哪个实现类。

 这是jdk提供的一个功能。

比如说你有个工程A，有个接口A，接口A在工程A里是没有实现类的 -> 系统在运行的时候，怎么给接口A选择一个实现类呢？

 你就可以自己搞一个jar包，META-INF/services/，放上一个文件，文件名就是接口名，接口A，接口A的实现类=com.zhss.service.实现类A2。让工程A来依赖你的这个jar包，然后呢在系统运行的时候，工程A跑起来，对接口A，就会扫描自己依赖的所有的jar包，在每个jar里找找，有没有META-INF/services文件夹，如果有，在里面找找，有没有接口A这个名字的文件，如果有在里面找一下你指定的接口A的实现是你的jar包里的哪个类？

SPI机制，一般来说用在哪儿？插件扩展的场景，比如说你开发的是一个给别人使用的开源框架，如果你想让别人自己写个插件，插到你的开源框架里面来，扩展某个功能。 

经典的思想体现，大家平时都在用，比如说jdbc，java定义了一套jdbc的接口，但是java是没有提供jdbc的实现类。但是实际上项目跑的时候，要使用jdbc接口的哪些实现类呢？一般来说，我们要根据自己使用的数据库，比如msyql，你就将mysql-jdbc-connector.jar，引入进来；oracle，你就将oracle-jdbc-connector.jar，引入进来。在系统跑的时候，碰到你使用jdbc的接口，他会在底层使用你引入的那个jar中提供的实现类。

## JDK SPI



## Dubbo SPI

Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension(); 

Protocol接口，dubbo要判断一下，在系统运行的时候，应该选用这个Protocol接口的哪个实现类来实例化对象来使用呢？他会去找一个你配置的Protocol，他就会将你配置的Protocol实现类，加载到jvm中来，然后实例化对象，就用你的那个Protocol实现类就可以了 

微内核，可插拔，大量的组件，Protocol负责rpc调用的东西，你可以实现自己的rpc调用组件，实现Protocol接口，给自己的一个实现类即可。 

这行代码就是dubbo里大量使用的，就是对很多组件，都是保留一个接口和多个实现，然后在系统运行的时候动态根据配置去找到对应的实现类。如果你没配置，那就走默认的实现好了，没问题。
```java
@SPI("dubbo")  
public interface Protocol {        
    int getDefaultPort();  
  
    @Adaptive  
    <T> Exporter<T> export(Invoker<T> invoker) throws RpcException;  
  
    @Adaptive  
    <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException;  

    void destroy();  
}
```

在dubbo自己的jar里，在/META_INF/dubbo/internal/com.alibaba.dubbo.rpc.Protocol文件中：
dubbo=com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol
http=com.alibaba.dubbo.rpc.protocol.http.HttpProtocol
hessian=com.alibaba.dubbo.rpc.protocol.hessian.HessianProtocol

所以说，这就看到了dubbo的spi机制默认是怎么玩儿的了，其实就是Protocol接口，@SPI(“dubbo”)说的是，通过SPI机制来提供实现类，实现类是通过dubbo作为默认key去配置文件里找到的，配置文件名称与接口全限定名一样的，通过dubbo作为key可以找到默认的实现了就是com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol。dubbo的默认网络通信协议，就是dubbo协议，用的DubboProtocol。如果想要动态替换掉默认的实现类，需要使用@Adaptive接口，Protocol接口中，有两个方法加了@Adaptive注解，就是说那俩接口会被代理实现。

啥意思呢？

比如这个Protocol接口搞了俩@Adaptive注解标注了方法，在运行的时候会针对Protocol生成代理类，这个代理类的那俩方法里面会有代理代码，代理代码会在运行的时候动态根据url中的protocol来获取那个key，默认是dubbo，你也可以自己指定，你如果指定了别的key，那么就会获取别的实现类的实例了。

通过这个url中的参数不通，就可以控制动态使用不同的组件实现类

好吧，那下面来说说怎么来自己扩展dubbo中的组件

自己写个工程，要是那种可以打成jar包的，里面的src/main/resources目录下，搞一个META-INF/services，里面放个文件叫：com.alibaba.dubbo.rpc.Protocol，文件里搞一个my=com.zhss.MyProtocol。自己把jar弄到nexus私服里去。

然后自己搞一个dubbo provider工程，在这个工程里面依赖你自己搞的那个jar，然后在spring配置文件里给个配置：

<dubbo:protocol name=”my” port=”20000” />

这个时候provider启动的时候，就会加载到我们jar包里的my=com.zhss.MyProtocol这行配置里，接着会根据你的配置使用你定义好的MyProtocol了，这个就是简单说明一下，你通过上述方式，可以替换掉大量的dubbo内部的组件，就是扔个你自己的jar包，然后配置一下即可。

dubbo里面提供了大量的类似上面的扩展点，就是说，你如果要扩展一个东西，只要自己写个jar，让你的consumer或者是provider工程，依赖你的那个jar，在你的jar里指定目录下配置好接口名称对应的文件，里面通过key=实现类。

然后对对应的组件，用类似<dubbo:protocol>用你的哪个key对应的实现类来实现某个接口，你可以自己去扩展dubbo的各种功能，提供你自己的实现。

## Spring SPI



## SPI打破双亲委派模型



# Spring扩展

在Spring中用**BeanDefinition**对象来描述需要实例化的对象，包含了这个bean的名称，class，是否是抽象，是否为单例等信息，然后通过**preInstantiateSingletons**实例化到ioc容器中。我们要使用的对象的时候，就从ioc容器中去获取相应的对象即可。

在Spring Bean的实例化过程中，Spring设计了很多拦截点，可以在动态的改变实例化对象的相关信息。达到在ioc容器中的对象和最开始注册到BeanDefinition中的信息不同。

[Springboot启动扩展点超详细教程小结](http://www.zhano.cn/index.php/Java/5791.html)

FactoryBean、外部对象加入容器、动态添加bean

BeanPostProcessor、BeanFactoryPostProcessor、Aware接口

https://my.oschina.net/wang5v/blog/3019778

mybatis dao接口原理

https://www.iteye.com/blog/wx1568534408-2458821

自动配置、starter

Spring注解大全

@Import

https://baijiahao.baidu.com/s?id=1693401682875623144&wfr=spider&for=pc

## FactoryBean

FactoryBean是一种特殊的Bean，允许用户自定义Bean的创建过程。不同于普通Bean的是：它是实现了FactoryBean<T>接口的Bean。
特殊性质：
根据该Bean的ID从BeanFactory中获取的实际上是FactoryBean的getObject()返回的对象，而不是FactoryBean本身，如果要获取FactoryBean对象，请在id前面加一个&符号来获取。

```java
/**  
 * MyFactoryBean
 * 代理一个类，拦截该类的所有方法，在方法的调用前后进行日志的输出 
 */  
public class MyFactoryBean implements FactoryBean<Object>, InitializingBean, DisposableBean {  
  
    private static final Logger logger = LoggerFactory.getLogger(MyFactoryBean.class);   
    private String interfaceName;   
    private Object target;   
    private Object proxyObj;   
    @Override  
    public void destroy() throws Exception {  
        logger.debug("destroy......");  
    }  
    @Override  
    public void afterPropertiesSet() throws Exception {  
        proxyObj = Proxy.newProxyInstance(  
                this.getClass().getClassLoader(),   
                new Class[] { Class.forName(interfaceName) },   
                new InvocationHandler() {   
            @Override  
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {  
                logger.debug("invoke method......" + method.getName());  
                logger.debug("invoke method before......" + System.currentTimeMillis());  
                Object result = method.invoke(target, args);  
                logger.debug("invoke method after......" + System.currentTimeMillis());  
                return result; }   
        });  
        logger.debug("afterPropertiesSet......");  
    }  
  
    @Override  
    public Object getObject() throws Exception {  
        logger.debug("getObject......");  
        return proxyObj;  
    }  
  
    @Override  
    public Class<?> getObjectType() {  
        return proxyObj == null ? Object.class : proxyObj.getClass();  
    }  
  
    @Override  
    public boolean isSingleton() {  
        return true;  
    }    
    // getter、setter
}
```

```xml
<bean id="fbHelloWorldService" class="com.ebao.xxx.MyFactoryBean">  
   <property name="interfaceName" value="com.ebao.xxx.HelloWorldService" />  
   <property name="target" ref="helloWorldService" />  
</bean>
```

## BeanFactoryPostProcessor

BeanFactoryPostProcessor可以在对象实例化到ioc容器之前对原有的beanDefinition的一些属性进行设置更新。

```java
@Component("testBean")
public class TestBean {}

public class TestBean1 {}

@Component
public class MyFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //获取testBean的beanDefinition
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition)beanFactory.getBeanDefinition("testBean");
        //更改class为TestBean1
        beanDefinition.setBeanClass(TestBean1.class);
    }
}
```

## ImportBeanDefinitionRegistrar

ImportBeanDefinitionRegistrar可以动态将自己的对象注册到BeanDefinition，然后会spring的bean实例化流程，生成实例对象到ioc容器。

简单模拟Mybaitis中的动态代理Mapper接口执行sql

```java
public interface MyDao {
    @Select("SELECT * FROM T1")
    void query();
}

public class MyFactoryBean implements FactoryBean {
    private Class classzz;
    public MyFactoryBean(Class classzz){
        this.classzz = classzz;
    }
   @Override
    public Object getObject() throws Exception {
        //利用动态代理生成实例对象
        Object instance = Proxy.newProxyInstance(MyFactoryBean.class.getClassLoader(), new Class[]{classzz.class}, new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String value = method.getDeclaredAnnotation(Select.class).value();
                System.out.println(value);
                System.out.println(”执行业务逻辑“);
                return null;
            }
        });
        return instance;
    }
    @Override
    public Class<?> getObjectType() {
        return this.classzz;
    }
}

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        //这里将数组写死了。我们可以定义一个包，扫描包下的所有接口class，这里就不做实现了，这里为了演示效果，多定义了一个接口MyDao1，跟MyDao定义相同的，代码就不贴出来了。
        Class[] classes = {MyDao.class,MyDao1.class};
        for (Class aClass : classes) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MyFactoryBean.class);
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition)builder.getBeanDefinition();
            //调用刚刚定义的MyFactoryBean有参构造函数
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(aClass.getTypeName());
            beanDefinitionRegistry.registerBeanDefinition(aClass.getName(),beanDefinition);
        }
    }
}
// 自定义@Select注解，这个注解就是用在Dao接口方法定义上的,value为sql语句
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Select {
    String value() default "";
}
// 自定义注解@MyScan，并通过@Import导入MyImportBeanDefinitionRegistrar。这样就会被spring扫描到
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({MyImportBeanDefinitionRegistrar.class})
public @interface MyScan {
}
```

有没有感觉到有点类似mybatis了，接口Mapper，没有任何实现，但是可以直接@Autowired进行调用。不过，我们自己定义的@MyScan注解，它的是@MapperScan注解，后面参数为Mapper的包路径，我们这里就没有实现类，因为我们在MyImportBeanDefinitionRegistrar中定义数组来模拟包路径扫描class了。

最后一步，在项目启动类加上@MyScan并编写测试。调用Mydao，到这里就将动态代理的类交由ioc管理了。

```
MyDao myDao = SpringContextUtils.getBean(MyDao.class);
myDao.execute();  //打印出”执行业务逻辑“
```

## BeanPostProcessor

注意：TestBeanPostProcessor也需要是Bean加入到容器中；容器加载每个bean时均会执行其中方法。

```java
@Component
public class TestBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("lyn post before:" + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("lyn post after:" + beanName);
        return bean;
    }
}
```

## InitializingBean、DisposableBean

@PostConstruct、@PreDestroy

## Aware接口

BeanNameAware、BeanFactoryAware、ApplicationContextAware等

## 容器事件

ContextRefreshEvent、

## 项目启动后执行逻辑

三种方式：CommandLineRunner、ApplicationRunner、ApplicationListener

```java
@Component
@Order(1)
public class TestCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("TestCommandLineRunner run args:" + args);
        System.out.println("TestCommandLineRunner run execute...");
    }
    @PostConstruct
    public void init() {
        System.out.println("TestCommandLineRunner init execute...");
    }
    @PreDestroy
    public void destry() {
        System.out.println("TestCommandLineRunner destroy execute...");
    }
}

@Component
public class TestApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("TestApplicationRunner run args:" + args);
        System.out.println("TestApplicationRunner run execute...");
    }
}

@Component
public class TestApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("TestApplicationListener onApplicationEvent execute...");
    }
}
```




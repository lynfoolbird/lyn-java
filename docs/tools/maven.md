
https://mp.weixin.qq.com/s/Q8QykLRI1Z2Sb4SBRdETLw

## profile
```xml
    <!--根据profile指定不同的依赖 -->
    <!--应用：工程依赖接口，不同实现封装到不同jar中，通过profile分别依赖 -->
    <profiles>
       <profile>
          <id>blue</id>
          <properties>
            <!--传递给脚本的参数值-->
            <activeProfile>blue</activeProfile>
          </properties>
          <activation>
              <!-- 默认激活-->
            <activeByDefault>true</activeByDefault>
          </activation>
           <dependencies>
               <dependency>
                   <groupId>com.thoughtworks.xstream</groupId>
                   <artifactId>xstream</artifactId>
                   <version>1.4.18</version>
               </dependency>
           </dependencies>
       </profile>
        <profile>
           <id>red</id>
           <properties>
              <activeProfile>red</activeProfile>
           </properties>
           <dependencies>
               <dependency>
                   <groupId>com.thoughtworks.xstream</groupId>
                   <artifactId>xstream</artifactId>
                   <version>1.4.17</version>
               </dependency>
           </dependencies>
        </profile>
    </profiles>
```

## 本地安装jar

当需要的jar包在中央仓库找不到或者是想把自己生成的jar包放到的Maven仓库中时，可以使用Maven install命令来安装。
以下为操作步骤(前提是安装好Maven并配置好环境变量) 示例
1、将需要安装的jar包放入到D盘

2、打开命令行，输入如下命令：

```xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>3.2.3</version>
</dependency>
```

```
mvn install:install-file -Dfile=jar包的位置 -DgroupId=上面的groupId -DartifactId=上面的artifactId -Dversion=上面的version -Dpackaging=jar
```
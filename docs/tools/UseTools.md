maven/gradle   git/svn   idea
代码生成：mybatis-generator、easy-code、swagger-codegen
mybatis-plus

# build

## maven

### 本地安装jar

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

# develop

## idea

依赖jar

# test

## postman

上传下载

## jmeter

# system
## windows

### 查看端口占用进程
netstat -ano|findstr 8080
tasklist pid
taskkill /F

## linux

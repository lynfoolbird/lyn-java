maven/gradle   git/svn   idea
代码生成：mybatis-generator、easy-code、swagger-codegen
mybatis-plus

# build

## maven

# develop

## idea

依赖jar、插件：lombok、mybatis、arthas、mavenhelper、keyprompterx、one dark theme

# test

## postman

上传下载

## jmeter

# system
## windows

### 查看端口占用进程
netstat -ano|findstr 8080

tasklist pid

taskkill /F /pid 123456

命令后台执行
start /b nginx.exe -c ./conf/nginx.conf

jinfo pid 查看进程运行时JVM参数

## linux

查看端口占用的进程 lsof -i:9080
后台执行脚本 nohup java -jar mvntest-1.0-SNAPSHOT.jar &
网关脚本执行问题 dos2unix xx.sh
sh xx.sh和./xx.sh是在子shell中执行；source xx.sh和. xx.sh方式是在当前shell中执行；
当前shell中export变量可使变量在子shell中可见
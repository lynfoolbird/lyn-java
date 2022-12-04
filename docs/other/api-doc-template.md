# 接口名称-API Name

接口功能说明

## 1 方法&请求url-Request URL

```
POST  /open/routes/{id}?subParam=123456
```

| 环境名 | 地址                         | 备注 |
| ------ | ---------------------------- | ---- |
| dev    | http://test-dev.lynjava.com/ |      |
| prd    | http://test.lynjava.com/     |      |

**路径参数-Path Param**

| 参数名称 | 类型   | 约束                 | 是否必填 | 描述   |
| -------- | ------ | -------------------- | -------- | ------ |
| id       | string | 长度、正则、枚举值等 | 是       | 集群id |
|          |        |                      |          |        |

**查询参数-Query Param**
| 参数名称 | 类型   | 约束                 | 是否必填 | 描述   |
| -------- | ------ | -------------------- | -------- | ------ |
| subParam | string | 长度、正则、枚举值等 | 是       | 属性名 |
|          |        |                      |          |        |

## 2 请求header-Request Header

| 参数名称      | 类型   | 约束                 | 是否必填 | 描述   |
| ------------- | ------ | -------------------- | -------- | ------ |
| Authorization | string | 长度、正则、枚举值等 | 是       | 属性名 |
| timestamp     | int    |                      |          |        |
| sign     | string    | 加密签名 |          |        |


## 3 请求body-Request Body

```json
{
    "name":"testName"
}
```

| 参数名称 | 类型   | 约束                 | 是否必填 | 描述     |
| -------- | ------ | -------------------- | -------- | -------- |
| name     | string | 长度、正则、枚举值等 | 是       | 集群名称 |
|          |        |                      |          |          |

## 4 正常响应体-Normal-Response Body

```json
{
    "code":"200",
    "success":true,
    "data":{
        "id":"123456",
        "name":"test"
    },
    "message":"success"
}
```

## 4 异常响应体-Error Response Body

```json
{
    "code":"P0001",
    "message":"参数错误"
}
```


## 5 异常码说明

| 异常码code | 描述     |
| ---------- | -------- |
| P0001      | 参数错误 |
|            |          |

## 6 Postman文件
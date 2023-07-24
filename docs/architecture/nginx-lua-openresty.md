Nginx Lua OpenResty Redis操作lua

获取数据优先从Nginx共享内存中获取，获取不到再连接redis获取

通过定时器或其他方式将redis中数据同步到Nginx共享内存，增量同步，全量同步

Nginx

静态Web服务器 动静分离

反向代理

负载均衡 7层

网关

限流



获取客户端IP

$remote_addr: 如果未使用代理，配置的输出结果为客户端IP。如果使用了代理，配置的输出结果为最后一个代理服务器的IP。
$proxy_add_x_forwarded_for: 代表请求链。每经过一个反向代理就在请求头X-Forwarded-For后追加反向代理IP，用逗号+空格分隔。标准格式如下：X-Forwarded-For: clientIP, proxyIP1, proxyIP2（ps. 最左边的clientIp即为客户端真实IP）

正式灰度集群

```
upstream pro_group {
    server 192.168.1.6:3000;
}
 
upstream gray_group {
    server 192.168.1.6:3001;
}

set $forward_group "pro_group";
# 根据cookie中信息转发灰度
if ($http_cookie ~* "version=1.0"){
    set $forward_group gray_group;
}

location ^~ /api {
    rewrite ^/api/(.*)$ /$1 break;
    proxy_pass http://$forward_group;
}
```





openresty

http://openresty.org/cn

openresty最佳实践

https://www.lanqiao.cn/library/openresty-best-practices/openresty/log_response

openresty实现缓存限流

https://blog.csdn.net/qq_43409401/article/details/128601398



动态负载均衡

```lua
upstream backend {
    server 0.0.0.0;
    balancer_by_lua_block {
        local balancer = require "ngx.balancer"
        local host = {"192.168.1.111", "192.168.1.112"}
        local backend = ""
        local port = ngx.var.server_port
        local remote_ip = ngx.var.remote_addr
        local key = remote_ip..port
        local hash = ngx.crc32_long(key);
        hash = (hash % 2) + 1
        backend = host[hash]
        ngx.log(ngx.DEBUG, "ip_hash=", ngx.var.remote_addr, " hash=", hash, " up=", backend, ":", port)
        local ok, err = balancer.set_current_peer(backend, port)
        if not ok then
            ngx.log(ngx.ERR, "failed to set the current peer: ", err)
            return ngx.exit(500)
        end
        ngx.log(ngx.DEBUG, "current peer ", backend, ":", port)
    }
}

server {
	listen 80;
	listen 8080;
	listen 7777;
	server_name *.x.com
	location / {
		proxy_pass http://backend;
	}

```


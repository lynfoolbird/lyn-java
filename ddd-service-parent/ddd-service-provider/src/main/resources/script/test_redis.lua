ngx.header.content_type="application/json;charset=utf8"
local redis = require("resty.redis")
local cjson = require("cjson")
local mysql = require("resty.mysql")

local uri_args = ngx.req.get_uri_args()
local id = uri_args["id"]

-- mysql连接
local db = mysql:new()
db:set_timeout(1000)
local props = {
    host = "192.168.169.140",
    port = 3306,
    database = "changgou_content",
    user = "root",
    password = "123456"
}
local res = db:connect(props)
local select_sql = "select url,pic from tb_content where status ='1' and category_id="..id.." order by sort_order"
res = db:query(select_sql)
db:close()

-- redis连接
local red = redis:new()
red:set_timeout(2000)
local ip ="127.0.0.1"
local port = 6379
local ok, err = red:connect(ip, port)
if not ok then
  ngx.log(ngx.ERR, "connect redis failed:", ip, port)
  return
end 
local exists, err = red:sismember("black_list", ngx.var.remote_addr)
if err then
  return
end 
if exists == 1 then
  return ngx.exit(ngx.HTTP_FORBIDDEN)
end
red:set("content_"..id, cjson.encode(res))
red:close()

ngx.say("{flag:true}")
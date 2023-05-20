-- 秒杀库存扣减lua

-- 库存未预热
if (redis.call('exists', KEYS[2]) == 1) then
    return -9;
end;
-- 秒杀商品库存存在
if (redis.call('exists', KEYS[1]) == 1) then
   local stock = tonumber(redis.call('get', KEYS[1]));
   local num = tonumber(ARGV[1]);
   -- 剩余库存少于请求数量量
   if (stock < num) then
      return -3
   end;
   -- 扣减库存
   if (stock >= num) then
      redis.call('incrby', KEYS[1], 0 - num);
      -- 扣减成功
      return 1
   end;
   return -2;
end;
-- 秒杀商品库存不存在
return -1;
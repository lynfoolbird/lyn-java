数据库规范

sql脚本

文件命名: 01_create_table_tpl_user_t_lyn.sql

文件内容：

表名、字段约束(非空、默认值；类型、长度)、主键、外键、索引、业务主键，唯一性，防重表、注释、授权、存储引擎、schema等；

执行前评审、数据备份、回退、删除字段时用重命名以便快速回退

适当冗余存储字段、减少关联查询 比如企业id等

一对一  一对多 映射 右边对象引用左边id，比如集群与实例关系
多对多映射 建关系表保存

命名规范 domain.ec2.gateway   子域/聚合 作前缀

企业多租(多db/schema/表字段区分)

alb_properties_t 企业多租配置表设计
id property  value group分组 type(平台、租户) enterprise_id description is_deleted created_by created_date last_updated_by last_updated_date

order_no 排序号；top 置顶号（整数，每次置顶时值+1）；删除状态：若涉及业务唯一性索引，可以用整数，0表示有效，非零为删除，每次删除时值+1

apply表：id、status（状态：初始化、执行中、成功、失败）、category
log表：apply_id、status、step（步骤名称）、request（请求参数）、response（响应参数）、result、message、tries（重试次数）

operation_log id  batch_no  一个动作涉及多个操作，用批次号聚合

建议优先用自增主键



mysql插入
insert into、insert select、replace into、insert into  where、on duplicate key、insert into select
insert into daily_hit_counter (day, slot, cnt) values ('2017-11-19', 1, 1) ON DUPLICATE KEY UPDATE cnt = cnt+1

INSERT INTO TABLE_NAME values (MyField1,MyField2) WHERE NOT EXISTS(select 1 from TABLE_NAME where MyId=xxxx);

merge into

# 分组排序

```sql
-- mysql: 按照name、age分组取最新时间 同时对其他字段汇总求和
select 
  t1.*, t2.create_date
from (
  select u.name,u.age,sum(u.id) from users u group by u.name,u.age
) t1
join (
  select a.name,a.age,a.create_date,sum(a.id) 
  from users a left join users b on a.name=b.name and a.age=b.age 
  and a.create_date<=b.create_date
  group by a.name,a.age,a.create_date
  having count(a.id)<=1
  order by a.name,a.age,a.create_date desc
) t2 on t1.name=t2.name and t1.age=t2.age

select 
   t.* 
from 
(select * from table_name order by last_updated_date desc limit 100000000) t 
group by t.enterprise_id, t.app_id, t.source_id
```

## 去重取一

场景描述：同一group下host和port应唯一，即不能存在group host port相同的记录，删除重复记录只保存一条的sql 

```sql
-- db2
delete from
  ( select 
      sm.id,sm.server_group_id,sm.hostname,sm.port,
      row_number() over (partition by sm.server_group_id,sm.hostname,sm.port order by sm.updated_date desc) as row_num
	from 
	  hic_alb_app_server_member sm 
	where 
	  sm.is_deleted='N'
  ) t
  where t.row_num>1;
```

## 查询最高成绩记录

 场景描述：一个考试每个用户考多次查询其最高成绩对应的记录

```sql
-- oracle
select
  id_mln_attempt "attemptId",
  emp_name "empName",
  decode(status,'Y','通过','未通过') "status",
  score "score"
from 
 (
   select 
     t1.*,rownum rno
   from
     ( 
       select * 
       from (select a.id_mln_attempt,a.test_id,a.emp_name,a.status,a.score,a.enterprise_name,a.completedate,
               row_number() over(partition by a.test_id,a.user_id,a.enterprise_id order by a.score desc) rn 
       	  from 
       	    mln_attempt a left join mln_enterprise e on a.enterprise_id=e.enterprise_id where a.test_id=#examId#) t0
       where t0.rn<=1 order by t0.score desc,t0.completedate asc
	  ) t1 where rownum<=#numPerPage# * #curPage# 
  ) t2 where t2.rno>#numPerPage# * (#curPage# -1 )
```

  场景描述：rule表context字段为/c1或/c1;/c2等，需将其分离插入到domain_context表

```
insert into hic_alb_domain_context
select
substr(k.context,1,locate(';',k.context)-1) from hic_alb_rule where k.context like '%;%'
union
substr(k.context,locate(';',k.context)+1) from hic_alb_rule where k.context like '%;%'
union
from hic_alb_rule where k.context not like '%;%'
```

# 行转列

# 列转行
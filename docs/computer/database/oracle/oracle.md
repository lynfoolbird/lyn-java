# 序列
```sql
create sequence mlearndata.seq_live_room_id
minvalue 1
maxvalue 9999999999
increment by 1
start with 10000
cache 10
noorder nocycle
```
使用
```sql
select SEQ_LIVE_ROOM_ID.Nextval AS id from dual;
```

# 1 树结构表设计
建表模板:表结构(字段约束 非空 默认值)、表名及字段注释、主键及索引、同义词、权限赋予；脚本可重复执行；字段：
classify_id  parent_classify_id classif_name org_id enterprise_id is_del order_number (classify_level fullPath fullPathName)
created_by created_date updated_by updated_date is_leaf虚列：是否叶子节点
+ id选择：UUID、oracle序列、sys_guid()  
+ is_del：标示该条记录已删除而不是真正用delete删除记录便于恢复
+ order_number：指定排序号
+ level：指定该节点层级
+ fullPath：标示该节点全路径名(从根到该节点)需通过存储过程更新维护

# 2 查找
树递归查询语法：select * from tablename start with cond1 connect by cond2 where cond3
cond1标示查询起始位置，这样理解：start with prior标示当前记录，如connect by prior id=parent_id意为当前记录的id是下一条记录的父id，故向下搜索；connect by id=prior parent_id意为当前记录的父id是下一条记录的id，所以向上搜索；cond3用于对返回的所有记录过滤；
## (1)递归向下查找
使用场景：查找以XX为根的所有子孙节点
```sql
select * from live_type_info t start with t.parent_type_id='1' connect by prior t.type_id=t.parent_type_id;  --不含根

select t.type_id from live_type_info t start with t.type_id='2' connect by prior t.type_id=t.parent_type_id;  --含根
```
注：递归搜索结果where筛选
## (2)递归向上查找
使用场景：由子组织找到所属一级组织，查找节点全路径
```sql
select * from live_type_info t start with t.type_id='9' connect by t.type_id= prior t.parent_type_id;
```
后面加上where level过滤即可
## (3)按orgid查找
使用场景：查找某公司的全部分类再将列表转换成树型结构
```sql
select * from live_type_info tt where tt.orgId=0
```
## (4)查找某节点的子节点
使用场景：逐层展开树
```sql
select * from live_type_info tt where tt.parent_type_id='4'
```
# 2 删除
删除记录（递归删除）只修改属性is_del 实现伪删除 以便于恢复 定义定时任务或触发器周期删除is_del为1的记录 

# 3 增加
传入父节点id，orgid，enterpriseid等参数(集团或公司下增加是typeid传成orgid或typeid为空)，代码生成新typeid，然后插入UUIDUtils.getID() 或利用数据库函数sys_guid()

# 4 修改
```sql
update tablename t set t.xx=xx where t.xx=xx
```
# 5 转换
## (1) excel树转insert sql
http://blog.csdn.net/qq_29929059/article/details/70476671
## (2) json树转insert sql
http://blog.csdn.net/qq_29929059/article/details/70477812
## (3) list转树bean
树Bean定义
```java
public class TreeDTO {
	private String id;
	private String parentId;
	private String name;
	private List<TreeDTO> children;
	//构造器 setter getter
}
```
demo代码
```java
public static void list2treeDTO(){		
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		list.add(new TreeDTO("2","1","广东"));
		list.add(new TreeDTO("3","2","广州"));
		list.add(new TreeDTO("4","2","深圳"));
		list.add(new TreeDTO("6","3","天河"));
		list.add(new TreeDTO("7","4","福田"));
		list.add(new TreeDTO("10","1","河南"));

		List<TreeDTO> resList = new ArrayList<TreeDTO>();
		for (TreeDTO t1:list){
			boolean isRoot = true;
			for (TreeDTO t2:list){
				if (t1.getParentId()!=null&&t1.getParentId().equals(t2.getId())){
					isRoot = false;
					if (t2.getChildren()==null){
						t2.setChildren(new ArrayList<TreeDTO>());
					}
					t2.getChildren().add(t1);
					break;
				}
			}
			if (isRoot){
				resList.add(t1);
			}
		}
	}
```
## (4) 树转list
```java
public static void treeBean2list(TreeBean root,List<TreeRecordDO> list,String parentId){
    if (root == null) {
        return ;
    }
    //TreeRecordDO d = new TreeRecordDO(UUIDUtil.getId(),parentId,root.getName());
    TreeRecordDO d = new TreeRecordDO(root.getId(),parentId,root.getName());
    list.add(d);
    if (root.getChildren()==null){
        return;
    }
    for (TreeBean sub:root.getChildren()){
        treeBean2list(sub, list, d.getId());
    }       
}
```
# 6 是否叶子节点判断
```sql
select
  t.org_id orgId,
  t.parent_org_id parentOrgId
  t.org_level orgLevel
  decode((select count(1)
    from mln_enterprise_org tt
	where tt.enterprise_id='123'
	and tt.parent_org_id=t.org_id
	and tt.is_valid=1
	and rownum=1),1,0,1) isLeaf
from mln_enterprise_org t1
where t.enterprise_id='123'
and t.parent_org_id='55'
and t.is_valid=1
and rownum<10000  //限制记录数
```
先筛选某集团下所有子公司再判断若该子公司的orgid是集团其他子公司的父id则为非叶子节点

# 7 level及fullpath字段维护 存储过程
插入时父节点level+1，父节点fullPath拼接本节点名得本节点全名；节点修改需更新子孙节点全路径：
- 思路一 按ID逐个递归向上找到根按rownum排序然后行转列wm_concat replace替换 不适合大数据量场景
- 思路二 新增fullPath  fullPathName字段 新增节点 修改节点均要刷新全路径 通过存储过程 游标 插入耗时但查询快
```sql
逐个递归查询计算节点level
select c.classify_id classifyId,
  (select count(y.classify_id) from mln_live_classify y where y.is_del=0 start wtih y.classify_id=c.classify_id connect by y.classify_id=prior y.parent_classify_id) as nodeLevel
from mln_live_classify y
where c.is_del=0
```

```sql
procedure update_fullpath(
      p_org_id in varchar2,
      p_org_fullpath in varchar2,
	  p_org_fullpath_cn in varchar2,
	  p_enterprise_id in varchar2) as
	v_fullpath varchar2(900);
	v_fullpath_cn varchar2(4000);
	cursor cur_children_nodes is select t.org_id,t.org_name from mln_enterprise_org t
	                           where t.parent_org_id=p_org_id
							   and t.is_valid=1
							   and t.enterprise_id=p_enterprise_id;
    begin
       for cur_node in cur_children_nodes loop
	    v_fullpath:=p_org_fullpath||cur_node.org_id||'/';
		v_fullpath_cn:=p_org_fullpath_cn||'/'||cur_node.org_name;
		update mln_enterprise_org t
		  set t.full_path=v_fullpath,
		      t.full_path_cn=v_fullpath_cn,
			  t.org_level=lengthb(translate(v_fullpath,'/'||v_fullpath,'/')),
			  t.update_date=sysdate
		where t.org_id=cur_node.org_id;
		commit;
		mlearndata.pkg_enterprise_org.update_fullpath(cur_node.org_id,v_fullpath,v_fullpath_cn,p_enterprise_id);
	end loop;
end update_fullpath;
end pkg_enterprise_org;
/
```

# 8 树结构横向展开
```sql
select 
  t1.classify_id classify_id1,t1.classify_name classify_name1,
  t2.classify_id classify_id2,t2.classify_name classify_name2
from (select * from mln_live_classify where parent_classify_id is null and category=1and is_del=0) t1
left join mln_live_classify t2 on t2.parent_classify_id=t1.classify_id
left join mln_live_classify t3 on t3.parent_classify_id=t2.classify_id
left join mln_live_classify t4 on t4.parent_classify_id=t3.classify_id
```
# 常用函数
+ sysdate：oracle当前时间
+ nvl ：判断是否为null
+ nvl2 
+ decode 
+ sys_guid ：oracle获取全局唯一ID
+ sys_connect_by_path ：获取树节点全路径（注意要自顶而下递归查询）
+ with as ：类似子查询，可当做临时表
+ wm_concat ：行转列，多行拼成一起
+ listagg：一对多 按分组拼接
+ regexp_substr
```sql
select t.type_id typeId, sys_connect_by_path(t.type_name, '|') fullPath
  from lyn_type_info t
 start with t.parent_type_id = '0'
connect by prior t.type_id = t.parent_type_id
```
```
with temp as(  
  select 'China' nation ,'Guangzhou' city from dual union all  
  select 'China' nation ,'Shanghai' city from dual union all  
  select 'China' nation ,'Beijing' city from dual union all  
  select 'USA' nation ,'New York' city from dual union all  
  select 'USA' nation ,'Bostom' city from dual union all  
  select 'Japan' nation ,'Tokyo' city from dual 
)  
select nation,listagg(city,',') within GROUP (order by city)  
from temp  
group by nation  
```
经验：
- 思路一先用with as和sys_connect_by_path获得所有classifyId及其全路径临时表，然后将该临时表与主表关联查询；
- 思路二查询主表利用子查询对每个classifyId查询其全路径；

# 其他操作

## sql两个条件或
select 1 from dual where
   exists(select 1 from mln_live_score s where s.title in...)
or
   exists(select 1 from mln_emp p where ...)

## 批量插入 
Insert into mln_live_gift_new(id,app_id,gift_name)select sys_guid(),'com.mlearning.sxt',g.gift_name from mln_live_gift_new g where g.app_id='com.mlearning.zhiniao'

create table as select
select * from mln_live_room_exam e
left join(select * from mln_live_room_exam_redpacket er where er.room_id=#roomId#) rp on rp.exam_id=e.exam_id

## 存在更新不存在插入
merge into mln_live_rating_summary y
using dual d
on (y.room_id=#roomId#)
when matched then
 update set y.total_rating = y.total_rating+#rating#,
             y.total_count = y.total_count+1
when not matched then
 insert (room_id,total_rating,total_count)values(#roomId#,0,0);
commit;

## 行列转换

listagg wm_concat

## 分组排序

举例：一个考试每个用户考多次查询最高成绩对应的记录

```sql
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

## 使用序列作为主键
<insert id="add" parameterClass="map">
   <selectKey resultClass="java.lang.String" keyProperty="testId">
       select trim(to_char(MLN_EXAM_S.Nextval,'999999999')) as ID from dual
	</selectKey\>
	insert into mln_exam(ID_MLN_TEST)values(#testId#)
</insert>

## 查询7天前置顶的直播间
select * from mln_live_room r where r.is_del=0 and r.is_top>0
and trunc(nvl(r.top_date,r.updated_date))<=trunc(sysdate)-7
oracle sql按天周月季年统计数据

## elasticJob分页分片查询 
where r.is_del=0 and MOD(to_number(r.room_id),#shardTotal#)=#shardItem#
MOD(ora_hash(r.room_id),#shardTotal#)=#shardItem#
select t2.id
from
  (select t.*,rownum rn
    from (select r.id_live_room as id
	from mln_live_room r 
	where r.is_del=0 and r.is_top>0
	and MOD(to_number(r.room_id),#shardTotal#)=#shardItem#) t
	where rownum<=#p.end#)t2
	where t2.rn>=#p.start#

## SQL去重
除id字段外其他几个字段重复，保留一条，获取其他几条记录
select * from mln_live_classify a where rowid != (select max(roomId) from mln_live_classify b where a.parent_classify_id
=b.parent_classify_id and a.is_del=b.is_del and b.classify_id='123')
去重复查询只取一条
select * from mln_live_classify a where rowid = (select max(roomId) from mln_live_classify b where a.parent_classify_id
=b.parent_classify_id and a.is_del=b.is_del )
删除其他冗余记录
row_number over partition实现方式

## clob字段更新
declare
  clobValue mln_live_room.edited_playb_data%TYPE;
begin
  clobValue := '超长字符串';
  update mln_live_room r set r.edited_playb_data = clobValue where r...;
  commit;
end;
/

update mln_live_room r set r.playb_data=replace(r.playb_data,'https:','http')

## 递归查询

start with connect by level

## 执行计划

explain

查看执行计划的方法：select /*+ gather_plan_statistics*/ from xxxx
select sql_id from v$sql t where t.sql_text like 'select /*+ gather_plan_statistics*/'||'%'
select * from table(dbms_xplan.display_cursor(sql_id,0,'ALLSTATS LAST'));
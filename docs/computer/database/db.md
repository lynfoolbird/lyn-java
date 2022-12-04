数据库规范

sql脚本

文件命名: 01_create_table_tpl_user_t_lyn.sql

文件内容：

表、字段约束(非空、默认值；类型、长度)、主键、索引、注释等；

执行前评审、数据备份、回退

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
# dbvc
database version controller(数据库版本控制)
# 简介
dbvc(数据库版本控制),见名思意,用于项目升级，只需编写.sql文件后，自动进行sql的更替，省去重复步骤
# 如何使用
## dbvc 
 dbvc项目时当前project core核心，其主要时通过有参构建`DbvcProperties`与`Connection`实现脚本的运行，借助`ibatis#ScriptRunner`
## dbvc-spring-boot-* 
  dbvc-spring-boot-* 则是实现spring-boot方式，依赖于`spring-boot-starter`与`mybatis-spring-boot-starter`
  自动获取`Connection`,将其注入`RunSqlFile`
## Properties
 `DbvcProperties`如果不了解`ibatis#ScriptRunner`请不要轻易更改,`DbvcProperties`主要提供了获取脚本的`url`配置与生产记录的`tableName`和`ScriptRunner#delimiter`分隔符
 `DbvcProperties`还提供了是否启动`enabled`字段,默认状态时`true`
## samples
<https://github.com/hb0730/dbvc/tree/master/dbvc-spring-boot-samples>


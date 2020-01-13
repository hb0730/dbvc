# dbvc
dbvc模块是这个dbvc-parent核心,用于自动执行脚本
# Maven coordinates
```
<dependency>
    <groupId>com.hb0730</groupId>
    <artifactId>dbvc</artifactId>
    <version>${dbvc-version}</version>
</dependency>
```
# usage
dbvc中`RunSqlFile`提供了有参构造`DbvcProperties`和`connection`只需实例化并且只提供了公开的`RunSqlFile#star`

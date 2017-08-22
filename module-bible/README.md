BIBLE 圣经 权威指南 实为 数据字典


权限点
bible.create
bible.update
bible.delete
bible.read
bible.read.byId
bible.grant

bible.import
bible.export


添加字典

添加字典项
修改字典项
删除一个字典项

获取子项集
创建子项
根据当前实体CODE修改
删除子项



依赖

```
git clone https://git.coding.net/minlia-team/minlia-boot-starter.git
cd minlia-boot-starter
mi
mvn clean install -DskipITs=true -DskipTests=true -Dmaven.test.skip=true -DdownloadSources=false -DdownloadJavadocs=false
```


先本地安装

```
git clone https://git.coding.net/minlia-team/minlia-modules.git
cd module-language
mi
mvn clean install -DskipITs=true -DskipTests=true -Dmaven.test.skip=true -DdownloadSources=false -DdownloadJavadocs=false
```
使用方式

```
<dependency>
    <groupId>com.minlia.modules</groupId>
    <artifactId>module-bible</artifactId>
    <version>1.0.0.RELEASE</version>
</dependency>
```
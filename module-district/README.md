交易网关

与上游的交易参数需要配置到Bible里面

签发给下游的交易参数使用实体保存



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
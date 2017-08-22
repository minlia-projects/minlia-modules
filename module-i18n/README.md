国际化模块



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
    <artifactId>module-language</artifactId>
    <version>1.0.0.RELEASE</version>
</dependency>
```
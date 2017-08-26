
# 如何愉快的进行数据访问

SPRING DATA, MYBATIS, Hibernate, JdbcTemplate

在众多的数据访问层中如何抉择, 这曾经让我们在一些阶段迷茫.

经过多年实战, 最终练就成了可以使用DATA JPA进行数据层访问与持久化, 查询时使用Mybatis进行的方案

为什么使用DATA JPA: 最初由[Oliver Gierke](https://github.com/synyx/hades)在08年发起的开源项目, 经过短短几年时间发展, 由于思想的前瞻性, 很受大家爱戴, 最后捐赠给了SPRING组织.

为什么结合MYBATIS: MYBATIS手写SQL时代做出了杰出的贡献, 封装日常的SQL, 带来更多灵活与便利性. 赞誉满满.

结合DATA JPA与MYBATIS过程中的问题
可能会出现transactionManager无法复用的问题, 需要指定2个使用自已的transactionManager.

## 现在我们如何进行数据访问
建表, 使用JPA进行模型创建, 根据模型自动化生成表结构, 使用原先的JPA注解即可


```
@javax.persistence.Entity;
public class Language{

private Long id;

private String name;

}
```

## Dao层的接口: 只需要定义接口, 即可拥有通过继承父接口的所有通用操作. 通过SPRING提供的动态PROXY进行实现自动化生成.

结合SPRING DATA JPA式方式命名查询动态生成SQL.

LanguageDao

```
public interface LanguageDao extends Dao<Language, Long> {

  Language findOneByBasenameAndLanguageAndCountryAndCode(String basename, String language,String country, String code);
}

```


## 通用查询: 由[Spring data mybatis](https://github.com/hatunet/spring-data-mybatis)提供的启动时生成Mapper机制进行各种基础SQL Mapper生成来完成.

前端与后端都可以进行查询条件的封装

```
@ApiOperation(value = "查找所有", notes = "查找所有", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
public StatefulBody findAll(@PageableDefault Pageable pageable) {
    Page<Language> found = languageReadOnlyService.findAll(pageable);
    return SuccessResponseBody.builder().payload(found).build();
}
```

单表, 或不想写SQL的业务使用JPA进行自动化操作, 查询类的使用BATIS进行自动化封装或手写SQL来解决业务需求.

## 具体与项目中的使用,请参见如下文件:

[MinliaCloudAutoConfiguration.java](https://github.com/minlia-projects/minlia-cloud/blob/dev/will/minlia-cloud-starter/src/main/java/com/minlia/cloud/autoconfiguration/MinliaCloudAutoConfiguration.java)

[DatabaseAutoConfiguration.java](https://github.com/minlia-projects/minlia-thirdparty/blob/dev/will/spring-data-batis-starter/src/main/java/com/minlia/cloud/data/batis/autoconfiguration/DatabaseAutoConfiguration.java)

[Language.java](https://github.com/minlia-projects/minlia-modules/blob/dev/will/module-i18n/src/main/java/com/minlia/module/language/v1/domain/Language.java)

[LanguageDao.java](https://github.com/minlia-projects/minlia-modules/blob/dev/will/module-i18n/src/main/java/com/minlia/module/language/v1/dao/LanguageDao.java)

[LanguageEndpoint.java](https://github.com/minlia-projects/minlia-modules/blob/dev/will/module-i18n/src/main/java/com/minlia/module/language/v1/LanguageEndpoint.java)



## 特别鸣谢: Spring Data JPA, Mybatis, albedo
[Spring data JPA](https://github.com/spring-projects/spring-data-jpa)

[Mybatis](https://github.com/mybatis/mybatis-3)

[Spring data mybatis](https://github.com/hatunet/spring-data-mybatis)

[albedo](https://github.com/somewhereMrli/albedo-boot)









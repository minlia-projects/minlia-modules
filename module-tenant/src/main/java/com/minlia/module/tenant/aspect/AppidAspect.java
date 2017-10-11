package com.minlia.module.tenant.aspect;

import com.minlia.module.tenant.annotation.Tenant;
import com.minlia.module.tenant.resolver.ThreadLocalBatisTenantIdentifierResolver;
import java.sql.SQLException;
import java.util.Arrays;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mybatis.repository.util.StringUtils;

/**
 * Aspect for logging execution of service and repository Spring components.
 */
@Aspect
@Slf4j
public class AppidAspect {

  @Autowired
  DataSource dataSource;

  @Pointcut("execution(public !void org.springframework.data.repository.Repository+.*(..))")
  public void pointcut() {
  }

  private void loggingDataSource() {
    try {
      log.debug("{}", dataSource.getConnection().getCatalog());
    } catch (SQLException e) {
//      e.printStackTrace();
    }
  }

//  @After("pointcut()")
//  public void logServiceAccess2(JoinPoint joinPoint) {
//    log.debug("@After Completed: " + joinPoint);
//    loggingDataSource();
//  }

//  @AfterReturning("pointcut()")
//  public void afterReturning(JoinPoint joinPoint) {
//    log.debug("AfterReturning : 집을 나간다.");
//    loggingDataSource();
//  }

  @Before("pointcut()")
  public void before(JoinPoint joinPoint) {
    log.debug("Before : {}", joinPoint.getArgs());
    Object[] objects = joinPoint.getArgs();
    for (Object obj : objects) {
      if (obj instanceof Persistable) {

//只对需要控制的部分进行不控制处理
//进入系统时已经经过webappidinterceptor过滤, 所以来的时候都会有appid


//在进入dao层时对关联实体进行判断是否需要tenant控制，若需要不改写idholder，若不需要，idholder内bypass置true
//connectionprovider内要判断idholder的bypass属性，若为true，不需要改写库名，否则按照appid改写库名
        Tenant tenant = obj.getClass().getAnnotation(Tenant.class);
        if (null != tenant) {
//          log.debug("需要进行tenant控制 {}", obj.getClass().getSimpleName());
          Table table = obj.getClass().getAnnotation(Table.class);
          String tableName = "";
          if (null != table && !StringUtils.isEmpty(table.name())) {
            tableName = table.name();
          } else {
            //没找到表名注解时找entity的注解
            Entity entity = obj.getClass().getAnnotation(Entity.class);
            tableName = entity.name();
            if (StringUtils.isEmpty(tableName)) {
              tableName = obj.getClass().getSimpleName();
            }
          }
          tableName = ImprovedNamingStrategy.INSTANCE.classToTableName(tableName);
//          log.debug("找到本实体的表名为 {}", tableName);
          ThreadLocalBatisTenantIdentifierResolver.setBypass(false);

        } else {
          ThreadLocalBatisTenantIdentifierResolver.setBypass(true);
          log.debug("需要切回主库1 {}", obj.getClass().getSimpleName());
        }

//        在离开dao层时无条件将idholder内的bypass置false

      } else {
        ThreadLocalBatisTenantIdentifierResolver.setBypass(true);
        log.debug("需要切回主库2 ", obj.getClass().getSimpleName());
      }
    }
//    loggingDataSource();
  }

  @Around("pointcut()")
  public Object around(ProceedingJoinPoint pjp) throws Throwable {
    Object retVal = pjp.proceed();
    log.debug("Enter: {}.{}() with argument[s] = {}", pjp.getSignature().getDeclaringTypeName(),
        pjp.getSignature().getName(), Arrays.toString(pjp.getArgs()));
//    loggingDataSource();

//    boolean hasClassAnnotation = false;
//    for(Class<?> i: pjp.getTarget().getClass().getInterfaces()) {
//      if(i.getAnnotation(ThrowResourceNotFound.class) != null) {
//        hasClassAnnotation = true;
//        break;
//      }
//    }
//
//    if(hasClassAnnotation && isObjectEmpty(retVal))
//      throw new RuntimeException( );

    return retVal;
  }

//
//  @Inject
//  private Environment env;
//
//  @Pointcut("within(*..service.*)")
//  public void loggingPointcut() {
//  }
//
//  @AfterThrowing(pointcut = "pointcut()", throwing = "e")
//  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
////    if (env.acceptsProfiles(Constants.SPRING_PROFILE_DEVELOPMENT)) {
//      log.error("Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'", joinPoint.getSignature().getDeclaringTypeName(),
//          joinPoint.getSignature().getName(), e.getCause() != null? e.getCause() : "NULL", e.getMessage(), e);
//
////    } else {
////      log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
////          joinPoint.getSignature().getName(), e.getCause() != null? e.getCause() : "NULL");
////    }
//  }
//
//  @Before("pointcut()")
//  public Object before(JoinPoint joinPoint) throws Throwable {
//    if (log.isDebugEnabled()) {
//      log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
//          joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
//    }
//    try {
//      Object result = joinPoint.getTarget()
//      if (log.isDebugEnabled()) {
//        log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
//            joinPoint.getSignature().getName(), result);
//      }
//      return result;
//    } catch (IllegalArgumentException e) {
//      log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
//          joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
//
//      throw e;
//    }
//  }
}

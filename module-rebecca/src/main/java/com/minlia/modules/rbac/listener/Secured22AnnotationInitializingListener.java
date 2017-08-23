//package com.minlia.apps.saas.listener;
//
//import com.google.common.collect.Lists;
//import com.minlia.cloud.utils.EnvironmentUtils;
//import java.lang.reflect.Method;
//import java.util.List;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
//import org.springframework.core.type.filter.AnnotationTypeFilter;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 仅扫描Endpoint层权限注解
// * 使用系统级别的 Language进行国际化配置
// */
//@Slf4j
//@Component
//public class Secured22AnnotationInitializingListener implements
//    ApplicationListener<ApplicationReadyEvent> {
//
////  @Autowired
////  PermissionWriteOnlyService permissionWriteOnlyService;
//
//
//
//  private String grabPermission(String source, String... types) {
//    if (StringUtils.isEmpty(source)) {
//      return null;
//    }
//
//    for (String type : types) {
//      if (source.startsWith(type)) {
//        String ret = source.replaceAll(type + "\\('(.*)'\\)", "$1").trim();
//        if (!StringUtils.isEmpty(ret)) {
//          return ret;
//        }
//      }
//    }
//    return null;
//  }
//
//  //
//  public void scanClass() {
//    ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
//        false);
//    scanner.addIncludeFilter(new AnnotationTypeFilter(Controller.class));
//    scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
//    List<Class<?>> clzs = Lists.newArrayList();
//    for (BeanDefinition beanDefinition : scanner.findCandidateComponents("")) {
////      Object source = beanDefinition.getSource();
//      String beanName = beanDefinition.getBeanClassName();
//      if (!StringUtils.isEmpty(beanName)) {
//        try {
//          Class<?> clz = Class.forName(beanName);
//          clzs.add(clz);
//        } catch (ClassNotFoundException e) {
//          e.printStackTrace();
//        }
//      }
//    }
//    scanMethod(clzs);
//
////    String[] beanNames=ContextHolder.getContext().getBeanDefinitionNames();
////    for(String beanName:beanNames){
////      Object obj=ContextHolder.getContext().getBean(beanName);
////      if(AssignableTypeFilter)
////    }
//  }
//
//  private void scanMethod(List<Class<?>> clzs) {
//    if (clzs.size() > 0) {
//      for (Class<?> clz : clzs) {
//        Method[] methods = clz.getDeclaredMethods();
//        for (Method method : methods) {
//          if (method.isAnnotationPresent(PreAuthorize.class)) {
////              log.debug("field {}", field);
//            PreAuthorize annotated = method.getDeclaredAnnotation(PreAuthorize.class);
//            if (annotated != null) {
//              String annotatedValue = annotated.value();
//              //"hasAnyAuthority('account.create')"
//              //将这串替换为 account.create
//              String value = grabPermission(annotatedValue, "hasAnyAuthority", "hasAuthority");
//              if (!StringUtils.isEmpty(value)) {
//                log.debug("Value {}", value);
////                  resolve(value);
//              }
//
////                for (Localize localize : ) {
//////                  TODO 添加业务
////
//////                  resolve(className, method, localize, messages);
////                }
//            }
//          }
//
//        }
//
//      }
//    }
//  }
//
//  /**
//   * 获取到所有注解的类,初始化到数据库
//   */
//  @Override
//  public void onApplicationEvent(ApplicationReadyEvent event) {
//    log.debug("SecuredAnnotationInitializingListener 获取到所有注解的类,初始化到数据库");
//
//    if (EnvironmentUtils.isProduction()) {
//      return;
//    }
//
//    scanClass();
////    final TypeReporter reporter = new TypeReporter() {
////      @SuppressWarnings("unchecked")
////      public Class<? extends Annotation>[] annotations() {
////        return new Class[]{Controller.class, PreAuthorize.class, PostAuthorize.class,
////            org.springframework.security.access.annotation.Secured.class};
////      }
////
////      private String grabPermission(String source,String ...types) {
////        if (StringUtils.isEmpty(source)) {
////          return null;
////        }
////
////        for(String type:types) {
////          if (source.startsWith(type)) {
////            String ret = source.replaceAll(type + "\\('(.*)'\\)", "$1").trim();
////            if (!StringUtils.isEmpty(ret)) {
////              return ret;
////            }
////          }
////        }
////        return null;
////      }
////
////
////
////      public void reportTypeAnnotation(Class<? extends Annotation> annotation, String className) {
////        try {
////          Class clz = Class.forName(className);
////          Method[] methods = clz.getDeclaredMethods();
////          for (Method method : methods) {
////            if (method.isAnnotationPresent(PreAuthorize.class)) {
//////              log.debug("field {}", field);
////              PreAuthorize annotated = method.getDeclaredAnnotation(PreAuthorize.class);
////              if (annotated != null) {
////                String annotatedValue = annotated.value();
////                //"hasAnyAuthority('account.create')"
////                //将这串替换为 account.create
////               String value= grabPermission(annotatedValue,"hasAnyAuthority","hasAuthority");
////                if (!StringUtils.isEmpty(value)) {
////                  log.debug("Value {}",value);
//////                  resolve(value);
////                }
////
//////                for (Localize localize : ) {
////////                  TODO 添加业务
//////
////////                  resolve(className, method, localize, messages);
//////                }
////              }
////            }
////
////            if (method.isAnnotationPresent(Localize.class)) {
////              Localize localize = method.getDeclaredAnnotation(Localize.class);
////              if (null != localize) {
////                //                  TODO 添加业务
//////                resolve(className, method, localize, messages);
////              }
////            }
////          }
////
////        } catch (Exception e) {
////          e.printStackTrace();
////        }
////      }
////    };
////
////    //发起扫找, 进行业务处理
////    final AnnotationDetector cf = new AnnotationDetector(reporter);
////    try {
////      cf.detect();
////    } catch (IOException e) {
////      e.printStackTrace();
////    }
////
////    //添加到数据库
////    //ExcpetionApiCode类型只能通过在代码中注解
////    //其它类型可以手动添加
////    if (!EnvironmentUtils.isProduction()) {
//////      messageAcceptor.setMessages(Constants.EXCEPTIONS_APICODE_PREFIX,messages);
////    }
//
////    log.debug("Messages {}",messages);
//  }
//}
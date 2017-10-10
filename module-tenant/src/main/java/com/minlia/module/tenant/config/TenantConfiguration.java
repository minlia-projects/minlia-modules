package com.minlia.module.tenant.config;


import com.minlia.module.tenant.batis.interceptor.MultiTenantInterceptor;
import com.minlia.module.tenant.batis.web.AppidWebInterceptor;
import com.minlia.module.tenant.hibernate.provider.HibernateTenantConnectionProvider;
import com.minlia.module.tenant.resolver.ThreadLocalBatisTenantIdentifierResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class TenantConfiguration extends WebMvcConfigurerAdapter {


//  /**
//   * for hibernate only
//   * @return
//   */
//  @Bean
//  public ThreadLocalBatisTenantIdentifierResolver threadLocalBatisTenantIdentifierResolver(){
//    return new ThreadLocalBatisTenantIdentifierResolver();
//  }
//
//  /**
//   * for hibernate only
//   * @return
//   */
//  @Bean
//  public HibernateTenantConnectionProvider hibernateTenantConnectionProvider(){
//    return new HibernateTenantConnectionProvider();
//  }


  /**
   * 创建BATIS 所需的interceptor
   */
  @Bean
  public MultiTenantInterceptor multiTenantInterceptor() {
    MultiTenantInterceptor interceptor = new MultiTenantInterceptor();
    return interceptor;
  }

  /**
   * 创建WEB层拦截器
   */
  @Bean
  public AppidWebInterceptor tenantWebInterceptor() {
    AppidWebInterceptor interceptor = new AppidWebInterceptor();
    return interceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 注册拦截器
    InterceptorRegistration interceptorRegistration = registry
        .addInterceptor(tenantWebInterceptor());
    // 配置拦截的路径
    interceptorRegistration.addPathPatterns("/api/v1/tenant/**");
    // 配置不拦截的路径
//    interceptorRegistration.excludePathPatterns("/**.html");

    // 还可以在这里注册其它的拦截器
//    registry.addInterceptor(new MasterWebInterceptor()).addPathPatterns("/api/**");
  }


}

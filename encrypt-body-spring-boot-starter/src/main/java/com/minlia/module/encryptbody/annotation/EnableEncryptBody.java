package com.minlia.module.encryptbody.annotation;

import com.minlia.module.encryptbody.advice.CryptRequestBodyAdvice;
import com.minlia.module.encryptbody.advice.EncryptResponseBodyAdvice;
import com.minlia.module.encryptbody.config.EncryptBodyConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>启动类</p>
 * <p>使用方法：在SpringBoot的Application启动类上添加此注解即可</p>
 * <p>更多使用信息请参考：<a href='https://github.com/Licoy/encrypt-body-spring-boot-starter/blob/master/README.md'>README</a></p>
 *
 * @author licoy.cn
 * @version 2018/9/6
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({EncryptBodyConfig.class,
//        HttpConverterConfig.class,    //TODO
        EncryptResponseBodyAdvice.class,
        CryptRequestBodyAdvice.class})
public @interface EnableEncryptBody {
}

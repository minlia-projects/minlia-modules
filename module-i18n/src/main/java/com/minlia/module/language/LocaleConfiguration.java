/*
 * Copyright © 2016 Minlia (http://oss.minlia.com/license/framework/2016)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.minlia.module.language;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

public class LocaleConfiguration extends WebMvcConfigurerAdapter {

    /**
     * 只可以在HEADER里改变请求的语言, 不可以在参数上加lang参数
     * @return
     */
//    @Lazy
//    @Bean(name = "localeResolver")
//    public LocaleResolver localeResolver() {
//        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
//        localeResolver.setDefaultLocale(Locale.getDefault());
//        return localeResolver;
//    }

//    @Lazy
//    @Bean(name = "localeResolver")
//    public LocaleResolver localeResolver() {
//        AngularCookieLocaleResolver cookieLocaleResolver = new AngularCookieLocaleResolver();
//        cookieLocaleResolver.setCookieName("NG_TRANSLATE_LANG_KEY");
//        return cookieLocaleResolver;
//    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//        localeChangeInterceptor.setParamName("lang");
//        registry.addInterceptor(localeChangeInterceptor);
//    }
}

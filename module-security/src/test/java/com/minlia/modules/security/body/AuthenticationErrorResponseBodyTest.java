package com.minlia.modules.security.body;

import org.apache.commons.lang3.LocaleUtils;
import org.junit.Test;

import java.util.Locale;


public class AuthenticationErrorResponseBodyTest {

    @Test
    public void testLocale(){
       Locale locale= LocaleUtils.toLocale("zh-CN");
        System.out.println(locale);
    }

}
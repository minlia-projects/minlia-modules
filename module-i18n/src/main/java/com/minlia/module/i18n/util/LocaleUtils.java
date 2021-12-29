package com.minlia.module.i18n.util;

import com.minlia.module.i18n.enums.LocaleEnum;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/8/8 6:00 PM
 */
public class LocaleUtils {

    public static LocaleEnum locale() {
        return LocaleEnum.valueOf(LocaleContextHolder.getLocale().toString());
    }

    public static String localeToString() {
        return LocaleContextHolder.getLocale().toString();
    }

}

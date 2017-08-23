//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.minlia.module.language.v1;

import static com.minlia.cloud.constant.Constants.X_LANG;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

public class AcceptHeaderLocaleResolver implements LocaleResolver {

  private final List<Locale> supportedLocales = new ArrayList(4);
  private Locale defaultLocale;

  public AcceptHeaderLocaleResolver() {
  }

  public void setSupportedLocales(List<Locale> locales) {
    this.supportedLocales.clear();
    if (locales != null) {
      this.supportedLocales.addAll(locales);
    }

  }

  public List<Locale> getSupportedLocales() {
    return this.supportedLocales;
  }

  public void setDefaultLocale(Locale defaultLocale) {
    this.defaultLocale = defaultLocale;
  }

  public Locale getDefaultLocale() {
    return this.defaultLocale;
  }

  public Locale resolveLocale(HttpServletRequest request) {
//    Locale defaultLocale = this.getDefaultLocale();
    String localeString = request.getHeader(X_LANG);
    if (!StringUtils.isEmpty(localeString)) {
      Locale locale = LocaleUtils.toLocale(localeString);
      if (this.isSupportedLocale(locale)) {
        return locale;
      }
    } else {
      Locale requestLocale = request.getLocale();
      if (this.isSupportedLocale(requestLocale)) {
        return requestLocale;
      } else {
        Locale supportedLocale = this.findSupportedLocale(request);
        return supportedLocale != null ? supportedLocale
            : (defaultLocale != null ? defaultLocale : requestLocale);
      }
    }
    return null;
  }

  private boolean isSupportedLocale(Locale locale) {
    List<Locale> supportedLocales = this.getSupportedLocales();
    return supportedLocales.isEmpty() || supportedLocales.contains(locale);
  }

  private Locale findSupportedLocale(HttpServletRequest request) {
    Enumeration requestLocales = request.getLocales();

    Locale locale;
    do {
      if (!requestLocales.hasMoreElements()) {
        return null;
      }

      locale = (Locale) requestLocales.nextElement();
    } while (!this.getSupportedLocales().contains(locale));

    return locale;
  }

  public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    throw new UnsupportedOperationException(
        "Cannot change HTTP accept header - use a different locale resolution strategy");
  }
}

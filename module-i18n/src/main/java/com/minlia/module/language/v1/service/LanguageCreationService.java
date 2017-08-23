package com.minlia.module.language.v1.service;


import com.minlia.module.language.v1.domain.Language;
import org.springframework.transaction.annotation.Transactional;

/**
 * 国际化初始化服务, 在系统启动时自动被调用, 如有需要初始化值可以直接使用
 */
@Transactional(readOnly = false)
public interface LanguageCreationService {

  /**
   * 当没有message时默认为 %%+code+%%
   * @param code
   * @return
   */
  public Language initialLanguage(String code);

  public Language initialLanguage(String code,String message);

  public Language initialLanguage(String language,String country,String code,String message);

  public Language initialLanguage(String basename,String language,String country,String code,String message);

}

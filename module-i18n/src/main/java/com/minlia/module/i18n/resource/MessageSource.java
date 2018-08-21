package com.minlia.module.i18n.resource;

import com.google.common.collect.Maps;
import com.minlia.module.i18n.bean.I18nDO;
import com.minlia.module.i18n.bean.I18nQO;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import com.minlia.module.i18n.service.I18nService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class MessageSource extends AbstractMessageSource implements ResourceLoaderAware, InitializingBean {
 
	private ResourceLoader resourceLoader;
	
	@Autowired
	private I18nService i18nService;

	//cache resource
	private static final Map<String, Map<String, String>> LOCAL_CACHE = new ConcurrentHashMap<>(256);

	public MessageSource() {}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.reload();
	}

//	@PostConstruct
//	public void init() {
//		this.reload();
//	}

	private void reload() {
		LOCAL_CACHE.clear();	//clear cache while reload data.
		LOCAL_CACHE.putAll(getAllMessageResource());
	}

	public Map<String, Map<String, String>> getAllMessageResource() {
//		if (CollectionUtils.isEmpty(i18ns)) {
//			return MapUtils.EMPTY_MAP;
//		}

		final Map<String, Map<String, String>> messageResources = Maps.newHashMap();
		for (int i = 0; i < LocaleEnum.values().length; i++) {
//			Map<String, String> i18nMap = i18nService.queryMap(I18nQO.builder().locale(LocaleEnum.values()[i].name()).build());
//			messageResources.put(LocaleEnum.values()[i].name(), i18nMap);

			Map<String, String> localeMessageResources = new HashMap<>();
			List<I18nDO> i18ns = i18nService.queryList(I18nQO.builder().locale(LocaleEnum.values()[i].name()).build());
			for (I18nDO i18n : i18ns) {
				localeMessageResources.put(i18n.getCode(),i18n.getValue());
			}
			messageResources.put(LocaleEnum.values()[i].name(), localeMessageResources);
		}
		return messageResources;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader != null?resourceLoader : new DefaultResourceLoader();
	}

	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		String msg = getText(code, locale);
		MessageFormat result = createMessageFormat(msg, locale);
		return result;
	}
 
	@Override
	protected String resolveCodeWithoutArguments(String code, Locale locale) {
		String result = getText(code, locale);
		return result;
	}

	private String getText(String code, Locale locale){
		String localeText = LOCAL_CACHE.get(locale.toString()).get(code);
		String resourceText = code;
		if(localeText != null){
			resourceText = localeText;
		}else{
			localeText = LOCAL_CACHE.get(Locale.SIMPLIFIED_CHINESE.toString()).get(code);;
			if(localeText != null){
				resourceText = localeText;
			}else{
				try {
					if(getParentMessageSource() != null){
						resourceText = getParentMessageSource().getMessage(code, null, locale);
					}
				} catch (Exception e) {
					log.error("Con not find message with {}:", code, e.getMessage());
				}
			}
		}
		return resourceText;
	}

//	private String getText(String code, Locale locale){
//		//本地语言编码
//		String key = code + MAP_SPLIT_CODE + locale.toString();
//		String localeText = LOCAL_CACHE.get(locale.toString()).get(key);
//		String resourceText = code;
//		if(localeText != null){
//			resourceText = localeText;
//		}else{
//			key = code + MAP_SPLIT_CODE + Locale.SIMPLIFIED_CHINESE;
//			localeText = LOCAL_CACHE.get(locale.toString()).get(key);
//			if(localeText != null){
//				resourceText = localeText;
//			}else{
//				try {
//					if(getParentMessageSource() != null){
//						resourceText = getParentMessageSource().getMessage(code, null, locale);
//					}
//				} catch (Exception e) {
//					logger.error("Con not find message with code:", code, e.getMessage());
//				}
//			}
//		}
//		return resourceText;
//	}
	
}

package com.minlia.module.i18n.resource;

import com.google.common.collect.Maps;
import com.minlia.module.i18n.attribution.bean.I18nAttributionQRO;
import com.minlia.module.i18n.attribution.entity.I18nAttribution;
import com.minlia.module.i18n.attribution.service.I18nAttributionService;
import com.minlia.module.i18n.entity.I18n;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import com.minlia.module.i18n.bean.I18nQRO;
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

	@Autowired
	private I18nAttributionService i18nAttributionService;

	//cache resource
	private static final Map<String, Map<String, String>> LOCAL_CACHE = new ConcurrentHashMap<>(256);

	public MessageSource() {}
	
	@Override
	public void afterPropertiesSet() {
		this.reload();
	}

//	@PostConstruct
//	public void init() {
//		this.reload();
//	}

	public void reload() {
		LOCAL_CACHE.clear();	//clear cache while reload data.
		LOCAL_CACHE.putAll(getAllMessageResource());
	}

	private Map<String, Map<String, String>> getAllMessageResource() {
		final Map<String, Map<String, String>> messageResources = Maps.newHashMap();
		for (int i = 0; i < LocaleEnum.values().length; i++) {
			Map<String, String> localeMessageResources = new HashMap<>();
			List<I18n> i18ns = i18nService.queryList(I18nQRO.builder().locale(LocaleEnum.values()[i].name()).build());
			for (I18n i18n : i18ns) {
				localeMessageResources.put(i18n.getCode(),i18n.getValue());
			}
			messageResources.put(LocaleEnum.values()[i].name(), localeMessageResources);
		}
		return messageResources;
	}

//	public Map<String, String> getLocalCache(Locale locale) {
//		return LOCAL_CACHE.get(locale.toString());
//	}
//
//	public Map<String, String> getLocalCache(String locale) {
//		return LOCAL_CACHE.get(locale);
//	}
	public Map<String, String> getLocalCache(Locale locale,String attributionCode) {
		return getLocalCache(locale.toString(),attributionCode);
	}

	public Map<String, String> getLocalCache(String locale,String attributionCode) {
		Map<String, String> map = Maps.newHashMap();
		List<I18nAttribution> list = i18nAttributionService.selectByAll(I18nAttributionQRO.builder().attributionCode(attributionCode).build());
		list.stream().forEach(i18nAttribution -> {
			map.put(i18nAttribution.getI18nCode(),LOCAL_CACHE.get(locale).get(i18nAttribution.getI18nCode()));
		});
		return map;
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
		String resourceText = code;
		String localeText = null;

		Map<String, String> localeSourceMap = LOCAL_CACHE.get(locale.toString());
		if (null != localeSourceMap) {
			localeText = localeSourceMap.get(code);
		}

		if(localeText != null){
			resourceText = localeText;
		}else{
			localeText = LOCAL_CACHE.get(Locale.SIMPLIFIED_CHINESE.toString()).get(code);

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

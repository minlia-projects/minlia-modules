package com.minlia.module.language.v2;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

/**
 * Reading of messages using data in databases. It must be provided with a
 * datasource and sql code which must be passed 2 jdbc parameters which will be
 * respectively filled with the code and the string representation of the
 * locale. <BR>
 */
@Slf4j
public class JdbcMessageSource extends AbstractMessageSource implements
    InitializingBean {

	@SuppressWarnings("unused")
	@Data
	private static class CacheKey {
		public String code;

		public Locale locale;

		public CacheKey(String code, Locale locale) {
			this.code = code;
			this.locale = locale;
		}
	}

	private NamedParameterJdbcOperations namedParameterJdbcOperations;

	private String sqlStatement;

	private Map<CacheKey, MessageFormat> cachedMessages = new HashMap<CacheKey, MessageFormat>();

	private long cachedMilliSecond = 0;

	private long lastQuery = 0;

	private Locale defaultLocale;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.support.AbstractMessageSource#resolveCode
	 * (java.lang.String, java.util.Locale)
	 */
	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		MessageFormat result = null;

		if (cachedMilliSecond == 0
				|| ((result = cachedMessages.get(new CacheKey(code, locale))) == null)
				|| lastQuery + cachedMilliSecond < System.currentTimeMillis()) {
			result = resolveCodeInternal(code, locale);
			if(null!=result) {
				cachedMessages.put(new CacheKey(code, locale), result);
			}
		}

    if (result != null) {
      return result;
    }

//		return resolveCodeInternal(code, locale);
		return null;
	}

	/**
	 * Check in base the message associated with the given code and locale
	 * 
	 * @param code
	 *            the code of the message to solve
	 * @param locale
	 *            the locale to check against
	 * @return a MessageFormat if one were found, either for the given locale or
	 *         for the default on, or null if nothing could be found
	 */
	protected MessageFormat resolveCodeInternal(String code, Locale locale) {
		String result;

		//当从数据库里获取时发出警告，可以被优化的
		log.warn("[OPTMIZATION REQUIRED] Fetching from datasource for code [{}] with locale [{}]",code,locale);

		lastQuery = System.currentTimeMillis();

		try {
			result = namedParameterJdbcOperations.getJdbcOperations()
					.queryForObject(sqlStatement,
							new Object[] { code, locale.getLanguage(),locale.getCountry() },
							String.class);
		} catch (IncorrectResultSizeDataAccessException e) {
			if (locale != null) {
				// Retry with english
				try {
					result = namedParameterJdbcOperations.getJdbcOperations()
							.queryForObject(
									sqlStatement,
									new Object[] { code,
											defaultLocale.getLanguage(),defaultLocale.getCountry() },
									String.class);
				} catch (IncorrectResultSizeDataAccessException ex) {
					return null;
				}
			} else {
				return null;
			}
		}

		return new MessageFormat(result, locale);
	}

	/**
	 * @param sqlStatement
	 *            The sqlStatement to set.
	 */
	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (sqlStatement == null) {
			throw new BeanInitializationException(
					"Sql statement should be filled");
		}

		if (namedParameterJdbcOperations == null) {
			throw new BeanInitializationException(
					"Jdbc template should be filled");
		}

	}

	/**
	 * Empty the cache so that all future message resolving request ends in
	 * database queries
	 */
	public void clearCache() {
		cachedMessages.clear();
	}

	public long getCachedMilliSecond() {
		return cachedMilliSecond;
	}

	public void setCachedMilliSecond(long cachedMilliSecond) {
		this.cachedMilliSecond = cachedMilliSecond;
	}

	public void setNamedParameterJdbcOperations(
			NamedParameterJdbcOperations namedParameterJdbcOperations) {
		this.namedParameterJdbcOperations = namedParameterJdbcOperations;
	}

	public Locale getDefaultLocale() {
		return defaultLocale;
	}

	public void setDefaultLocale(Locale defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

}
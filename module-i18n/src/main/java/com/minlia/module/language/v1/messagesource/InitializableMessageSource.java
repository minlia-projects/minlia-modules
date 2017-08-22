package com.minlia.module.language.v1.messagesource;

import com.minlia.cloud.utils.EnvironmentUtils;
import com.minlia.module.language.v1.messagesource.util.LocaleUtils;
import com.minlia.module.language.v1.messagesource.util.MessageInitializationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.*;

@Slf4j
public class InitializableMessageSource extends AbstractMessageSource implements InitializingBean {

    protected Map<Locale, List<String>> resolvingPath = new HashMap<Locale, List<String>>();
    protected Map<String, Map<String, MessageFormat>> messages;
    protected Locale defaultLocale;
    protected MessageProvider messageProvider;
    protected Boolean returnUnresolvedCode = false;
    protected List<String> basenames = new ArrayList<String>();

    /**
     * If this property is set to true this initializes post-construction (spring lifecycle interface).
     */
    protected boolean autoInitialize = true;

    /**
     * Property that indicates if all basenames returned from the {@link MessageProvider} should be used (=false) or the
     * ones explicitely set using {@link #setBasename(String)} or {@link #setBasenames(List)}.
     */
    protected boolean basenameRestriction = false;

    /**
     * Initializes messages by retrieving them from the set {@link MessageProvider}. This also leads to a reset of the
     * resolving-paths used to cache lookup-paths for messages
     */
    public void initialize() {

        log.debug("Initializing messageSource");
        // reset the path-cache (default-locale could have been changed)
        resolvingPath = new HashMap<Locale, List<String>>();

        if (!basenameRestriction) {
            basenames = new ArrayList<String>();
            basenames.addAll(messageProvider.getAvailableBaseNames());
        }

        messages = new HashMap<String, Map<String, MessageFormat>>();

        for (String basename : basenames) {
            initialize(basename);
        }
    }


    /**
     * Reads all messages from the {@link MessageProvider} for the given Basename.
     *
     * @param  basename  the basename to initialize messages for
     */
    protected void initialize(String basename) {

        initializeMessages(basename);
    }

    protected void initializeMessages(String basename) throws RuntimeException {

        Messages messagesForBasename = messageProvider.getMessages(basename);

        for (Locale locale : messagesForBasename.getLocales()) {
            Map<String, String> codeToMessage = messagesForBasename.getMessages(locale);

            for (String code : codeToMessage.keySet()) {
                try {
                    addMessage(basename, locale, code, createMessageFormat(codeToMessage.get(code), locale));
                } catch (RuntimeException e) {
                    throw new MessageInitializationException(String.format(
                            "Error processing Message code=%s locale=%s basename=%s, %s", code, locale, basename,
                            e.getMessage()), e);
                }
            }
        }
    }


    private void addMessage(String basename, Locale locale, String code, MessageFormat messageFormat) {

        String localeString = basename + "_" + (locale != null ? locale.toString() : "");
        Map<String, MessageFormat> codeMap = messages.get(localeString);

        if (codeMap == null) {
            codeMap = new HashMap<String, MessageFormat>();
            messages.put(localeString, codeMap);
        }

        codeMap.put(code, messageFormat);
    }


    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {

        Boolean isProduction= EnvironmentUtils.isProduction();
        //如果非生产环境,则每次请求都重新初始化, 达到修改即生效的效果
        if(!isProduction){
            initialize();
        }
        for (String basename : basenames) {
            List<String> paths = getPath(locale);
            for (String loc : paths) {
                if(null!=messages) {
                    Map<String, MessageFormat> formatMap = messages.get(basename + loc);
                    if (formatMap != null) {
                        MessageFormat format = formatMap.get(code);
                        if (format != null) {
                            if (format.getLocale() == null) {
                                format.setLocale(defaultLocale);
                            }
                            return format;
                        }
                    }
                }else{
                    try {
                        this.afterPropertiesSet();
                    } catch (Exception e) {
                        log.debug("afterPropertiesSet With exception {}",e.getMessage());
                    }
                }
            }
        }

        if (getReturnUnresolvedCode()) {
            return createMessageFormat(code, locale);
        } else {
            return null;
        }
    }


    private List<String> getPath(Locale locale) {

        List<String> path = resolvingPath.get(locale);
        if (path == null) {
            path = new ArrayList<String>();

            List<Locale> localePath = LocaleUtils.getPath(locale, getDefaultLocale());
            for (Locale loc : localePath) {
                if (loc == null) {
                    path.add("_");
                } else {

                    String language = LocaleUtils.getLanguage(loc);
                    String country = LocaleUtils.getCountry(loc);
                    String variant = LocaleUtils.getVariant(loc);
                    if (!variant.isEmpty()) {
                        path.add(String.format("_%s_%s_%s", language, country, variant));
                    } else if (!country.isEmpty()) {
                        path.add(String.format("_%s_%s", language, country));
                    } else if (!language.isEmpty()) {
                        path.add(String.format("_%s", language));
                    }
                }

            }

            resolvingPath.put(locale, path);
        }
        return path;
    }


    public Locale getDefaultLocale() {

        return defaultLocale;
    }


    /**
     * Sets the default {@link Locale} used during message-resolving. If for a given Locale the message is not found the
     * message gets looked up for the default-locale. If the message is not found then the "base-message" is used. This
     * is allowed to be null which then means "no default locale"
     *
     * @param  defaultLocale  the Locale to use as default or null if no default-locale should be used
     */
    public void setDefaultLocale(Locale defaultLocale) {

        this.defaultLocale = defaultLocale;
    }


    /**
     * Sets the {@link MessageProvider} for this which is asked for all its Messages during initialisation.
     *
     * @param  messageProvider  the {@link MessageProvider} to use
     */
    public void setMessageProvider(MessageProvider messageProvider) {

        Assert.notNull(messageProvider);

        this.messageProvider = messageProvider;
    }


    /**
     * Sets a single basename for this. This cannot be used in combination with {@link #setBasenames(List)}. If neither
     * {@link #setBasename(String)} nor {@link #setBasenames(List)} is called the basenames are looked up from the
     * {@link MessageProvider}
     *
     * @param  basename  the single basename to use for this instance
     */
    public void setBasename(String basename) {

        basenameRestriction = true;
        this.basenames = new ArrayList<String>();
        basenames.add(basename);
    }


    /**
     * Sets a {@link List} of basenames to use for this instance. This cannot be used in combination with
     * {@link #setBasename(String)}. If neither {@link #setBasename(String)} nor {@link #setBasenames(List)} is called
     * the basenames are looked up from the {@link MessageProvider}
     *
     * @param  basenames  the {@link List} of basenames
     */
    public void setBasenames(List<String> basenames) {

        Assert.notNull(basenames);

        if (!basenames.isEmpty()) {
            basenameRestriction = true;
            this.basenames = basenames;
        }
    }


    /**
     * Callback to call {@link #initialize()} after construction of this using a Spring-Callback.
     */
    public void afterPropertiesSet() throws Exception {

        if (autoInitialize) {
            initialize();
        }

    }


    /**
     * Sets the.
     *
     * @param  autoInitialize
     */
    public void setAutoInitialize(boolean autoInitialize) {

        this.autoInitialize = autoInitialize;
    }


    /**
     * @return  <br>
     *          Default value is false.If message could not be resolved returns null<br>
     *          if set to true- will return message code if the message could not be resolved
     */
    public Boolean getReturnUnresolvedCode() {

        return this.returnUnresolvedCode;
    }


    /**
     * @param  returnUnresolvedCode  -<br>
     *                               Default value is false.If message could not be resolved returns null<br>
     *                               if set to true- will return message code if the message could not be resolved
     */
    public void setReturnUnresolvedCode(Boolean returnUnresolvedCode) {

        this.returnUnresolvedCode = returnUnresolvedCode;
    }
}

package com.minlia.module.language.messagesource;

import java.util.Collection;

public interface MessageProvider {

    /**
     * Returns a {@link Messages} instance containing all the messages for a given basename.
     * 
     * @param basename the basename to receive {@link Messages} for
     * @return the {@link Messages} of that basename
     */
    Messages getMessages(String basename);


    /**
     * Returns a {@link Collection} of basenames that can be used for {@link #getMessages(String)}.
     * 
     * @return all available basenames
     */
    Collection<String> getAvailableBaseNames();
}

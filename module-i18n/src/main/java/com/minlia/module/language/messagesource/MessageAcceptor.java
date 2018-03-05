package com.minlia.module.language.messagesource;


public interface MessageAcceptor {

    /**
     * Set the {@link Messages} for the given basename.
     * 
     * @param basename the basename
     * @param messages the messages
     */
    void setMessages(String basename, Messages messages);
//    void addMessageIfNotExists(String basename,Messages messages);

}

package com.minlia.module.language.messagesource.util;

public class MessageInitializationException extends RuntimeException {

    public MessageInitializationException(String message, RuntimeException e) {
        super(message, e);
    }
}

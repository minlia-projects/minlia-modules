package com.minlia.module.language.v1.messagesource.importer;

import com.minlia.module.language.v1.messagesource.Messages;
import com.minlia.module.language.v1.messagesource.MessageAcceptor;
import com.minlia.module.language.v1.messagesource.MessageProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleMessageAcceptor implements MessageAcceptor, MessageProvider {

    Map<String, Messages> messageMap = new HashMap<String, Messages>();

    public void setMessages(String basename, Messages messages) {
        messageMap.put(basename, messages);
    }


    public Messages getMessages(String basename) {

        return messageMap.get(basename);
    }


    public List<String> getAvailableBaseNames() {

        return new ArrayList<String>(messageMap.keySet());
    }

}

/**
 * 
 */
package com.minlia.module.language.v1.messagesource.importer;

import com.minlia.module.language.v1.messagesource.MessageAcceptor;
import com.minlia.module.language.v1.messagesource.MessageProvider;
import com.minlia.module.language.v1.messagesource.filesystem.FileSystemMessageProvider;

import java.io.File;

public class Importer {

    private MessageProvider source;

    private MessageAcceptor target;


    public Importer(File basePath, MessageAcceptor target) {

        super();
        this.source = new FileSystemMessageProvider(basePath);
        this.target = target;
    }


    public Importer(MessageProvider source, MessageAcceptor target) {

        super();
        this.source = source;
        this.target = target;
    }


    public void importMessages(String basename) {

        target.setMessages(basename, source.getMessages(basename));
    }


    public void importMessages() {

        for (String basename : source.getAvailableBaseNames()) {
            importMessages(basename);
        }

        // TODO think about basenames that exist in target but not in source

    }
}

package com.minlia.modules.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 使用Mapper进行写入
 */
@Slf4j
public class ObjectResponseHandler<CLASS extends Class<?>, RESPONSE extends HttpResponse> implements ResponseHandler<CLASS> {

    protected final CLASS clazz;
    protected Object clazzObject;

    public ObjectResponseHandler(final CLASS clazz) {
        this.clazz = clazz;
    }

    public CLASS handleResponse(final HttpResponse response) throws IOException {
        final int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode >= 300) {
            throw new FileNotFoundException("Source not found, response code " + statusCode);
        }

        final HttpEntity entity = response.getEntity();
        return writeStreamToObject(entity.getContent());
    }


    protected CLASS writeStreamToObject(final InputStream source) throws IOException {
        String text = IOUtils.toString(source, StandardCharsets.UTF_8.name());
        log.debug("TARGET CLASS {}", clazz);
        ObjectMapper mapper = new ObjectMapper();
        Object x = mapper.readValue(text, clazz.getClass());
        //TODO convert Responsed String to Special Object
        //JSON, XML, WSDL etc.
        return (CLASS) x;
    }

}

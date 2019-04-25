package com.minlia.module.wechat.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.InputStream;

/**
 * 封装了一些采用HttpClient发送HTTP请求的方法
 */
public class HttpClientUtil {

    /**
     * 返回图像数据，但是要确定post方法返回的是一个image文件；
     *
     * @param url
     * @param body
     * @return
     * @throws Exception
     */
    public static File sendPostByJsonToFile(String url, String body) throws Exception {
        CloseableHttpClient httpclient = HttpClients.custom().build();
        HttpPost post = null;
        CloseableHttpResponse result = null;
        try {
            post = new HttpPost(url);
            HttpEntity entity2 = new StringEntity(body, Consts.UTF_8);
            post.setConfig(RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build());
            post.setHeader("Content-Type", "application/json");
            post.setEntity(entity2);
            result = httpclient.execute(post);
            InputStream inputStream = result.getEntity().getContent();
            File dest=File.createTempFile("qr_",".png");
            FileUtils.copyInputStreamToFile(inputStream, dest);
            return dest;
        } finally {
            if (result != null) {
                result.close();
            }
            if (post != null) {
                post.releaseConnection();
            }
            httpclient.close();
        }
    }

}
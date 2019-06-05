package com.minlia.modules.otp.sms;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author chen junhan 569551869@qq.com
 */
@Slf4j
@Service
@EnableConfigurationProperties(OtpSmsProperties.class)
public class OtpSmsServiceImpl implements OtpSmsService {

    @Autowired
    private OtpSmsProperties otpSmsProperties;

    @Override
    public String send(String icc, String destAddr, String message){

        SMSRequestBody smsRequestBody = new SMSRequestBody();
        smsRequestBody.setIcc(StringUtils.isNotBlank(icc) ? icc : otpSmsProperties.getDefaultIcc());
        smsRequestBody.setDestAddr(destAddr);
        smsRequestBody.setMessage(message);

        String result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            int timeout = 10;
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(timeout * 1000)
                    .setConnectionRequestTimeout(timeout * 1000)
                    .setSocketTimeout(timeout * 1000)
                    .build();
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
            CloseableHttpClient httpclient =
                    HttpClientBuilder.create()
                            .setDefaultRequestConfig(config)
                            .setSSLContext(sslContext)
                            .build();

            HttpPost post = new HttpPost(otpSmsProperties.getUrl());
            HttpEntity entity = new StringEntity(
                    URLEncoder.encode(objectMapper.writeValueAsString(smsRequestBody), UTF_8.name()),
                    ContentType.APPLICATION_JSON
            );
            post.setEntity(entity);
            post.setHeader(new BasicHeader("X-JETCO-CLIENT-ID", otpSmsProperties.getXJetcoClientId()));
            post.setHeader(new BasicHeader("X-JETCO-CLIENT-SECRET", otpSmsProperties.getXJetcoClientSecret()));
            post.setHeader(new BasicHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType()));

            HttpResponse response;

            response = httpclient.execute(post);
            if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent(), UTF_8));
                String line = reader.readLine();
                EntityUtils.consume(responseEntity);
                try {
                    SMSResponseBody smsResponseBody = objectMapper.readValue(line, SMSResponseBody.class);
                    result = smsResponseBody.getId();
                } catch (Exception e) {
                    log.error("Nova response mapping exception", e);
                }
            } else {
                log.error(String.valueOf(response.getStatusLine().getStatusCode())
                        + ": "
                        + response.getStatusLine().getReasonPhrase());
            }
            httpclient.close();
        }catch (Exception e){
            log.error("send error", e);
            result = null;
        }
        return result;
    }

}

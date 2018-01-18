package com.minlia.module.tencent.miniapp.wechat.miniapp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.minlia.module.tencent.miniapp.domain.WechatUserDetail;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.IOException;


/**
 * https://github.com/249134995/ace/blob/4b50feefc9e8253028c2bf0a402ffd1e5b4239a1/ace/ace-portal/ace-portal-service/src/main/java/com/huacainfo/ace/portal/service/impl/AuthorityServiceImpl.java
 */
@Service
@Slf4j
@Deprecated
public class WechatSessionRemoteApi<T> {

//  @Autowired
//  private BibleItemService bibleItemService;

//  private static final String JSCODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

  private OkHttpClient httpClient;
  private ObjectMapper objectMapper;

//  @Deprecated
//  public WechatSession jscode2session(String code) throws ApiException {
//
//    String url = String.format(JSCODE2SESSION_URL,
//            bibleItemService.get(WechatMaBibleConstants.MINIAPP_CODE, WechatMaBibleConstants.MINIAPP_ITEM_CODE_APPID),
//            bibleItemService.get(WechatMaBibleConstants.MINIAPP_CODE, WechatMaBibleConstants.MINIAPP_ITEM_CODE_SECRET),
//            code);
//    log.debug("Request with url: {}", url);
//    Request request = new Request.Builder()
//        .url(url)
//        .get()
//        .build();
//    try {
//      Response response = getHttpClient().newCall(request).execute();
//      String resStr = response.body().string();
//      try {
//        WechatError error = getObjectMapper().readValue(resStr, WechatError.class);
//        if (null != error.getErrcode()) {
//          throw new ApiException(error.getErrcode(), error.getErrmsg());
//        }
//      } catch (IOException ignored) {
//      }
//      return getObjectMapper()
//          .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
//          .readValue(resStr, WechatSession.class);
//    } catch (IOException e) {
//      log.error(e.getMessage(), e);
//      throw new ApiException(-1, e.getMessage());
//    }
//  }

  public WechatUserDetail decryptUserInfo(String encryptedData, String iv, String sessionKey) {
    byte[] res = WechatEncryptUtils.decrypt(Base64.decodeBase64(encryptedData),
        Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
    if (null != res && res.length > 0) {
      try {
        String resStr = new String(res, "UTF-8");
        return getObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
            .readValue(resStr, WechatUserDetail.class);
      } catch (IOException e) {
        log.error(e.getMessage(), e);
        return null;
      }
    }
    return null;
  }

  public <T> T decryptData(String encryptedData, String iv, String sessionKey, Class<T> clz) {

    String data = "";
    Object x=null;
    try {
      data = Aes128Util.decrypt(encryptedData, sessionKey, Aes128Util.generateIV(iv));
//        byte[] res = WechatEncryptUtils.decrypt(Base64.decodeBase64(encryptedData),
//            Base64.decodeBase64(iv), Base64.decodeBase64(sessionKey));
//        if (null != res && res.length > 0) {
//            try {
//
//            } catch (IOException e) {
//                log.error(e.getMessage(), e);
//                return null;
//            }
//        }


      x= (getObjectMapper()
          .setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
          .readValue(data, clz));

    } catch (Exception e) {
      e.printStackTrace();
    }

    return (T) x;

  }

  private OkHttpClient getHttpClient() {
    if (null == httpClient) {
      httpClient = new OkHttpClient();
    }
    return httpClient;
  }

  private ObjectMapper getObjectMapper() {
    if (null == objectMapper) {
      objectMapper = new ObjectMapper();
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    return objectMapper;
  }

}

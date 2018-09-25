package com.minlia.module.pooul.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by garen on 2018/7/18.
 */
@Component
@ConfigurationProperties(prefix = "pooul.merchant",ignoreUnknownFields = true)
@Data
public class PooulMerchantProperties {

    /**
     * 平台商户编号，固定值为普尔为你开通的平台商户编号
     */
    private String platformMerchantId;

    /**
     * 父级商户编号，如为平台商户下属一级商户则为平台商户编号，如为一级一下商户则为该父级商户编号，如：7590462217569167
     */
    private String parentId;

//    private String createUrl;
//
//    private String updateUrl;
//
//    private String deleteUrl;
//
//    private String getUrl;
//
//    private String searchUrl;

}

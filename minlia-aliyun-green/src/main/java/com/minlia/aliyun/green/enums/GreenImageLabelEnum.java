package com.minlia.aliyun.green.enums;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author garen
 */
public enum GreenImageLabelEnum {
//    porn：图片智能鉴黄
//    terrorism：图片暴恐涉政
//    ad：图文违规
//    qrcode：图片二维码
//    live：图片不良场景
//    logo：图片logo

    porn,
    terrorism,
    ad,
    qrcode,
    live,
    logo;

    public static List<String> all() {
        List list = Lists.newArrayList();
        for (GreenImageLabelEnum value : GreenImageLabelEnum.values()) {
            list.add(value.name());
        }
        return list;
    }

}
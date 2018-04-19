package com.minlia.modules.tencent.cloud.auth.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by garen on 2018/4/19.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaceAuth {

    /**
     * 腾讯云线下对接分配的 App ID
     */
    private String webankAppId;

    /**
     * 订单号，由合作方上送，每次唯一，不能超过 32 位
     */
    private String orderNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * idNo
     */
    private String idNo;

    /**
     * 用户 ID ，用户的唯一标识（不能带有特殊字符）
     */
    private String userId;

    /**
     * 非必填 BASE64String
     * 比对源照片，注意：原始图片不能超过 2M，且必须为 JPG 或 PNG 格式。
     * 参数有值：使用合作伙伴提供的比对源照片进行比对，必须注照片是正脸可信照片，照片质量由合作方保证。
     * 参数为空 ：根据身份证号 + 姓名使用权威数据源比对
     */
    private String sourcePhotoStr;

    /**
     * 比对源照片类型，参数值为1 时是：水纹正脸照。参数值为 2 时是：高清正脸照
     */
    private String sourcePhotoType;

    /**
     * 默认参数值为：1.0.0
     */
    private String version;

    /**
     * 签名：使用上面生成的签名
     */
    private String sign;

}

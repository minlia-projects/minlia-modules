package com.minlia.modules.qcloud.faceid.body.response;

import lombok.Data;

/**
 * Created by garen on 2018/4/19.
 */
@Data
public class QcloudFaceIdResultResponseBody {

    /**
     * 0：表示身份验证成功
     */
    private String code;

    /**
     * 返回结果描述
     */
    private String msg;

    /**
     * 业务流水号
     */
    private String bizSeqNo;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 证件号码
     */
    private String idNo;

    /**
     * 证件类型
     */
    private String idType;

    /**
     * 姓名
     */
    private String name	;

    /**
     * 活体检测得分
     */
    private String liveRate;

    /**
     * 人脸比对得分
     */
    private String similarity;

    /**
     * 进行刷脸的时间
     */
    private String occurredTime;

    /**
     * 人脸验证时的照片，base64 位编码
     */
    private String photo;

    /**
     * 人脸验证时的照片，base64 位编码
     */
    private String video;

    public boolean isSuccess(){
        return code.equals("0");
    }

}
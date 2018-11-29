package com.minlia.modules.qcloud.faceid.service;

import com.minlia.cloud.body.Response;
import com.minlia.modules.qcloud.faceid.bean.to.QcloudFaceIdTO;
import com.minlia.modules.qcloud.faceid.bean.dto.QcloudFaceIdResultDTO;

/**
 * Created by garen on 2018/4/19.
 */
public interface QcloudFaceidService {

    /**
     * 腾讯云人脸识别结果查询地址
     */
    public final static String H5FACEID_RESULT_URL = "https://idasc.webank.com/api/server/sync?app_id={app_id}&nonce={nonce}&order_no={order_no}&version=1.0.0&sign={sign}";

    String sign(String orderNo,String name,String idNo,String userId);

    /**
     * 人脸识别请求
     * @param to
     * @return
     */
    Response geth5faceid(QcloudFaceIdTO to);

    /**
     * 获取人脸识别结果
     * @param orderNo
     * @return
     */
    QcloudFaceIdResultDTO getH5faceidResult(String orderNo);

}

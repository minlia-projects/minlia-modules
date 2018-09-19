package com.minlia.modules.qcloud.faceid.mapper;

import com.minlia.modules.qcloud.faceid.body.QcloudFaceIdRecordQueryRequestBody;
import com.minlia.modules.qcloud.faceid.entity.QcloudFaceIdRecord;

import java.util.List;

/**
 * Created by garen on 2018/4/19.
 */
public interface QcloudFaceIdRecordMapper {

    int create(QcloudFaceIdRecord faceIdRecord);

    int update(QcloudFaceIdRecord faceIdRecord);

    int delete(String orderNo);

    long count(QcloudFaceIdRecordQueryRequestBody requestBody);

    QcloudFaceIdRecord queryLastByUserId(String userId);

    QcloudFaceIdRecord queryOne(QcloudFaceIdRecordQueryRequestBody requestBody);

    List<QcloudFaceIdRecord> queryList(QcloudFaceIdRecordQueryRequestBody recordRequestBody);

}

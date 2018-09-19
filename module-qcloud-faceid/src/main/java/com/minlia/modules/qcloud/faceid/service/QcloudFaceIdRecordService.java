package com.minlia.modules.qcloud.faceid.service;

import com.github.pagehelper.PageInfo;
import com.minlia.modules.qcloud.faceid.entity.QcloudFaceIdRecord;
import com.minlia.modules.qcloud.faceid.body.QcloudFaceIdRecordQueryRequestBody;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by garen on 2018/4/19.
 */
public interface QcloudFaceIdRecordService {

    QcloudFaceIdRecord create(QcloudFaceIdRecord faceIdRecord);

    QcloudFaceIdRecord update(QcloudFaceIdRecord faceIdRecord);

    void delete(String orderNo);

    long count(QcloudFaceIdRecordQueryRequestBody requestBody);

    QcloudFaceIdRecord queryLastByUserId(String userId);

    QcloudFaceIdRecord queryOne(QcloudFaceIdRecordQueryRequestBody recordRequestBody);

    List<QcloudFaceIdRecord> queryList(QcloudFaceIdRecordQueryRequestBody recordRequestBody);

    PageInfo<QcloudFaceIdRecord> queryPage(QcloudFaceIdRecordQueryRequestBody recordRequestBody, Pageable pageable);

}

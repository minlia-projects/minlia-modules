package com.minlia.modules.qcloud.faceid.service;

import com.github.pagehelper.PageInfo;
import com.minlia.modules.qcloud.faceid.entity.QcloudFaceIdRecord;
import com.minlia.modules.qcloud.faceid.body.QcloudFaceIdRecordQueryRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by garen on 2018/4/19.
 */
public interface QcloudFaceIdRecordService {

    QcloudFaceIdRecord create(QcloudFaceIdRecord faceIdRecord);

    QcloudFaceIdRecord update(QcloudFaceIdRecord faceIdRecord);

    void delete(String orderNo);

    long count(QcloudFaceIdRecordQueryRequest requestBody);

    QcloudFaceIdRecord queryLastByUserId(String userId);

    QcloudFaceIdRecord queryOne(QcloudFaceIdRecordQueryRequest recordRequestBody);

    List<QcloudFaceIdRecord> queryList(QcloudFaceIdRecordQueryRequest recordRequestBody);

    PageInfo<QcloudFaceIdRecord> queryPage(QcloudFaceIdRecordQueryRequest recordRequestBody, Pageable pageable);

}

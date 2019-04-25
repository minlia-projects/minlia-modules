package com.minlia.modules.qcloud.faceid.mapper;

import com.minlia.modules.qcloud.faceid.bean.qo.QcloudFaceIdRecordQQ;
import com.minlia.modules.qcloud.faceid.bean.domain.QcloudFaceIdRecord;

import java.util.List;

/**
 * Created by garen on 2018/4/19.
 */
public interface QcloudFaceIdRecordMapper {

    int create(QcloudFaceIdRecord faceIdRecord);

    int update(QcloudFaceIdRecord faceIdRecord);

    int delete(String orderNo);

    QcloudFaceIdRecord queryLastByUserId(String userId);

    long count(QcloudFaceIdRecordQQ qro);

    QcloudFaceIdRecord queryOne(QcloudFaceIdRecordQQ qro);

    List<QcloudFaceIdRecord> queryList(QcloudFaceIdRecordQQ qro);

}

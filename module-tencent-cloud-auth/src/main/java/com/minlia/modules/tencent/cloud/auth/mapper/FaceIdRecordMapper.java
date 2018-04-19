package com.minlia.modules.tencent.cloud.auth.mapper;

import com.minlia.modules.tencent.cloud.auth.body.TcFaceIdRecordQueryRequestBody;
import com.minlia.modules.tencent.cloud.auth.entity.FaceIdRecord;

import java.util.List;

/**
 * Created by garen on 2018/4/19.
 */
public interface FaceIdRecordMapper {

    int create(FaceIdRecord faceIdRecord);

    int update(FaceIdRecord faceIdRecord);

    int delete(String orderNo);

    long count(TcFaceIdRecordQueryRequestBody requestBody);

    FaceIdRecord queryOne(TcFaceIdRecordQueryRequestBody requestBody);

    List<FaceIdRecord> queryList(TcFaceIdRecordQueryRequestBody recordRequestBody);

}

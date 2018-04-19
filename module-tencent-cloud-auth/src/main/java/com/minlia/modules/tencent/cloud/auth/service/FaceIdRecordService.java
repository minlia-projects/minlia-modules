package com.minlia.modules.tencent.cloud.auth.service;

import com.github.pagehelper.PageInfo;
import com.minlia.modules.tencent.cloud.auth.body.TcFaceIdRecordQueryRequestBody;
import com.minlia.modules.tencent.cloud.auth.entity.FaceIdRecord;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by garen on 2018/4/19.
 */
public interface FaceIdRecordService {

    FaceIdRecord create(FaceIdRecord faceIdRecord);

    FaceIdRecord update(FaceIdRecord faceIdRecord);

    void delete(String orderNo);

    long count(TcFaceIdRecordQueryRequestBody requestBody);

    FaceIdRecord queryOne(TcFaceIdRecordQueryRequestBody recordRequestBody);

    List<FaceIdRecord> queryList(TcFaceIdRecordQueryRequestBody recordRequestBody);

    PageInfo<FaceIdRecord> queryPage(TcFaceIdRecordQueryRequestBody recordRequestBody, Pageable pageable);

}

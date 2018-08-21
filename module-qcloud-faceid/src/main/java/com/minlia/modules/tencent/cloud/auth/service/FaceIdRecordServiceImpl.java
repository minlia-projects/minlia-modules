package com.minlia.modules.tencent.cloud.auth.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.modules.tencent.cloud.auth.body.TcFaceIdRecordQueryRequestBody;
import com.minlia.modules.tencent.cloud.auth.entity.FaceIdRecord;
import com.minlia.modules.tencent.cloud.auth.mapper.FaceIdRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by garen on 2018/4/19.
 */
@Service
public class FaceIdRecordServiceImpl implements FaceIdRecordService {

    @Autowired
    private FaceIdRecordMapper faceIdRecordMapper;

    @Override
    public FaceIdRecord create(FaceIdRecord faceIdRecord) {
        faceIdRecordMapper.create(faceIdRecord);
        return faceIdRecord;
    }

    @Override
    public FaceIdRecord update(FaceIdRecord faceIdRecord) {
        faceIdRecordMapper.update(faceIdRecord);
        return faceIdRecord;
    }

    @Override
    public void delete(String orderNo) {
        faceIdRecordMapper.delete(orderNo);
    }

    @Override
    public long count(TcFaceIdRecordQueryRequestBody requestBody) {
        return faceIdRecordMapper.count(requestBody);
    }

    @Override
    public FaceIdRecord queryLastByUserId(String userId) {
        return faceIdRecordMapper.queryLastByUserId(userId);
    }

    @Override
    public FaceIdRecord queryOne(TcFaceIdRecordQueryRequestBody requestBody) {
        return faceIdRecordMapper.queryOne(requestBody);
    }

    @Override
    public List<FaceIdRecord> queryList(TcFaceIdRecordQueryRequestBody requestBody) {
        return faceIdRecordMapper.queryList(requestBody);
    }

    @Override
    public PageInfo<FaceIdRecord> queryPage(TcFaceIdRecordQueryRequestBody requestBody, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(),pageable.getPageSize()).doSelectPageInfo(()->faceIdRecordMapper.queryList(requestBody));
    }

}

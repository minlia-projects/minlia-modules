package com.minlia.modules.qcloud.faceid.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.modules.qcloud.faceid.bean.qo.QcloudFaceIdRecordQQ;
import com.minlia.modules.qcloud.faceid.bean.domain.QcloudFaceIdRecord;
import com.minlia.modules.qcloud.faceid.mapper.QcloudFaceIdRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by garen on 2018/4/19.
 */
@Service
public class QcloudFaceIdRecordServiceImpl implements QcloudFaceIdRecordService {

    @Autowired
    private QcloudFaceIdRecordMapper faceIdRecordMapper;

    @Override
    public QcloudFaceIdRecord create(QcloudFaceIdRecord faceIdRecord) {
        faceIdRecordMapper.create(faceIdRecord);
        return faceIdRecord;
    }

    @Override
    public QcloudFaceIdRecord update(QcloudFaceIdRecord faceIdRecord) {
        faceIdRecordMapper.update(faceIdRecord);
        return faceIdRecord;
    }

    @Override
    public void delete(String orderNo) {
        faceIdRecordMapper.delete(orderNo);
    }

    @Override
    public long count(QcloudFaceIdRecordQQ qo) {
        return faceIdRecordMapper.count(qo);
    }

    @Override
    public QcloudFaceIdRecord queryLastByUserId(String userId) {
        return faceIdRecordMapper.queryLastByUserId(userId);
    }

    @Override
    public QcloudFaceIdRecord queryOne(QcloudFaceIdRecordQQ qo) {
        return faceIdRecordMapper.queryOne(qo);
    }

    @Override
    public List<QcloudFaceIdRecord> queryList(QcloudFaceIdRecordQQ qo) {
        return faceIdRecordMapper.queryList(qo);
    }

    @Override
    public PageInfo<QcloudFaceIdRecord> queryPage(QcloudFaceIdRecordQQ qo, Pageable pageable) {
        return PageHelper.startPage(qo.getPageNumber(),qo.getPageSize()).doSelectPageInfo(()->faceIdRecordMapper.queryList(qo));
    }

}

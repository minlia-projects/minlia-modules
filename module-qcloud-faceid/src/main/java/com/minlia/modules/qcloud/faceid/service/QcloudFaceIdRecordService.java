package com.minlia.modules.qcloud.faceid.service;

import com.github.pagehelper.PageInfo;
import com.minlia.modules.qcloud.faceid.bean.domain.QcloudFaceIdRecord;
import com.minlia.modules.qcloud.faceid.bean.qo.QcloudFaceIdRecordQQ;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by garen on 2018/4/19.
 */
public interface QcloudFaceIdRecordService {

    QcloudFaceIdRecord create(QcloudFaceIdRecord faceIdRecord);

    QcloudFaceIdRecord update(QcloudFaceIdRecord faceIdRecord);

    void delete(String orderNo);

    QcloudFaceIdRecord queryLastByUserId(String userId);

    long count(QcloudFaceIdRecordQQ qro);

    QcloudFaceIdRecord queryOne(QcloudFaceIdRecordQQ qro);

    List<QcloudFaceIdRecord> queryList(QcloudFaceIdRecordQQ qro);

    PageInfo<QcloudFaceIdRecord> queryPage(QcloudFaceIdRecordQQ qro, Pageable pageable);

}

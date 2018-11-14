package com.minlia.modules.attachment.service;


import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.modules.attachment.bean.AttachmentCTO;
import com.minlia.modules.attachment.bean.AttachmentQO;
import com.minlia.modules.attachment.entity.Attachment;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by will on 6/17/17.
 */
public interface AttachmentService {

    /**
     * 创建
     * @param attachment
     * @return
     */
    Attachment create(Attachment attachment);

    /**
     * 创建
     * @param attachments
     * @return
     */
    List<Attachment> create(List<Attachment> attachments);

    /**
     * 创建
     * @param requestBody
     * @return
     */
    List<Attachment> create(AttachmentCTO requestBody);

    /**
     * 修改
     * @param attachment
     * @return
     */
    Attachment update(Attachment attachment);

    /**
     * 删除
     * @param id
     */
    Response delete(Long id);

    /**
     * 删除
     * @param relationId
     * @param belongsTo
     */
    Response delete(String relationId, String belongsTo);

    /**
     * 通过令牌绑定业务ID
     * @param accessKey
     * @param relationId
     */
    void bindByAccessKey(String accessKey, String relationId, String belongsTo);

    /**
     * 读取
     * @param id
     * @return
     */
    Attachment queryById(Long id);

    Attachment queryByKey(String key);

    String queryUrls(String relationId, String belongsTo);

    String queryFirstUrl(String relationId, String belongsTo);

    List<Attachment> queryAllByRelationIdAndBelongsTo(String relationId, String belongsTo);

    /**
     * 返回所有
     * @return
     */
    List<Attachment> queryList(AttachmentQO requestBody);

    /**
     * 返回所有
     * @return
     */
    PageInfo<Attachment> queryPage(AttachmentQO requestBody, Pageable pageable);

}

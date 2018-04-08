package com.minlia.modules.attachment.service;


import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.modules.attachment.body.AttachmentCreateRequestBody;
import com.minlia.modules.attachment.body.AttachmentQueryRequestBody;
import com.minlia.modules.attachment.entity.Attachment;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by will on 6/17/17.
 */
public interface AttachmentService {

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
    List<Attachment> create(AttachmentCreateRequestBody requestBody);

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
    StatefulBody delete(Long id);

    /**
     * 删除
     * @param relationId
     * @param belongsTo
     */
    StatefulBody delete(String relationId, String belongsTo);

    /**
     * 通过令牌绑定业务ID
     * @param accessToken
     * @param relationId
     */
    void bindByAccessKey(String accessToken, String relationId, String belongsTo);

    /**
     * 读取
     * @param id
     * @return
     */
    Attachment queryById(Long id);

    Attachment queryByAccessKey(String accessKey);

    List<Attachment> queryAllByRelationIdAndBelongsTo(String relationId, String belongsTo);

    /**
     * 返回所有
     * @return
     */
    List<Attachment> queryList(AttachmentQueryRequestBody requestBody);

    /**
     * 返回所有
     * @return
     */
    PageInfo<Attachment> queryPage(AttachmentQueryRequestBody requestBody, Pageable pageable);

}

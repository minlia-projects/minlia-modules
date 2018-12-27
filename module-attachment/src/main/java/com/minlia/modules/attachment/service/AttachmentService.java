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
     * @param cto
     * @return
     */
    List<Attachment> create(AttachmentCTO cto);

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
    String bindByAccessKey(String accessKey, String relationId, String belongsTo);
    void bindByAccessKey(List<String> accessKey, String relationId, String belongsTo);

    Attachment queryById(Long id);

    Attachment queryOne(AttachmentQO qo);

    List<Attachment> queryList(AttachmentQO qo);

    PageInfo<Attachment> queryPage(AttachmentQO qo, Pageable pageable);

    List<String>  queryUrls(AttachmentQO qo);

    /**
     * 改为集合 TODO
     * @param relationId
     * @param belongsTo
     * @return
     */
    @Deprecated
    String queryUrls(String relationId, String belongsTo);

    String queryFirstUrl(String relationId, String belongsTo);

}

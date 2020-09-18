package com.minlia.module.attachment.service;

import com.minlia.module.attachment.entity.SysAttachmentEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.attachment.bean.AttachmentCro;

import java.util.List;

/**
 * <p>
 * 附件 服务类
 * </p>
 *
 * @author garen
 * @since 2020-08-26
 */
public interface AttachmentService extends IService<SysAttachmentEntity> {

    List<SysAttachmentEntity> create(AttachmentCro cro);

    SysAttachmentEntity update(SysAttachmentEntity entity);

    /**
     * 根据令牌绑定附件，并返回url
     *
     * @param accessKey
     * @param relationTo
     * @param relationId
     * @return
     */
    String bindByAccessKey(String accessKey, String relationTo, String relationId);

    /**
     * 根据令牌集绑定附件
     *
     * @param accessKeys
     * @param relationTo
     * @param relationId
     * @param allowNull
     */
    void bindByAccessKey(List<String> accessKeys, String relationTo, String relationId, boolean allowNull);

}

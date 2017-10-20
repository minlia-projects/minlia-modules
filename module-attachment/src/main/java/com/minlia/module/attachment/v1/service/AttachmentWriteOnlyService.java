package com.minlia.module.attachment.v1.service;

import com.minlia.cloud.service.ReadOnlyService;
import com.minlia.cloud.service.WriteOnlyService;
import com.minlia.module.attachment.v1.body.AttachmentQueryRequestBody;
import com.minlia.module.attachment.v1.dao.AttachmentDao;
import com.minlia.module.attachment.v1.domain.Attachment;
import jxl.demo.Write;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by henry on 20/10/2017.
 * badcop@163.com
 */
@Transactional(readOnly = false)
public interface AttachmentWriteOnlyService extends WriteOnlyService<AttachmentDao, Attachment, Long> {

    /**
     * 通过令牌绑定业务ID
     * @param accessToken
     * @param businessId
     */
    public void bindByAccessToken(String accessToken,String businessId);

}
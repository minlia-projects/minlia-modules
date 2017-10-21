package com.minlia.module.attachment.v1.dao;

import com.minlia.cloud.dao.Dao;
import com.minlia.module.attachment.v1.domain.Attachment;

/**
 * Created by henry on 20/10/2017.
 * badcop@163.com
 */
public interface AttachmentDao extends Dao<Attachment, Long> {

    Attachment findByAccessToken(String accessToken);

}

package com.minlia.modules.attachment.service;


import com.minlia.cloud.body.StatefulBody;
import com.minlia.modules.attachment.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by will on 6/17/17.
 */
public interface AttachmentUploadService {

    StatefulBody upload(MultipartFile file, String relationId, String belongsTo) throws Exception;

    StatefulBody upload(File file, String relationId, String belongsTo) throws Exception;

}

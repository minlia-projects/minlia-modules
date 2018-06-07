package com.minlia.modules.attachment.service;


import com.minlia.cloud.body.StatefulBody;
import com.minlia.modules.attachment.body.AttachmentUploadRequestBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by will on 6/17/17.
 */
public interface AttachmentUploadService {

    StatefulBody upload(MultipartFile file) throws Exception;

    StatefulBody upload(MultipartFile file, String relationId, String belongsTo) throws Exception;

    StatefulBody upload(AttachmentUploadRequestBody requestBody);

}

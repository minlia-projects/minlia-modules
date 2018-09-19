package com.minlia.modules.attachment.service;


import com.minlia.cloud.body.Response;
import com.minlia.modules.attachment.body.AttachmentUploadRequestBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by will on 6/17/17.
 */
public interface AttachmentUploadService {

    Response upload(MultipartFile file) throws Exception;

    Response upload(MultipartFile file, String relationId, String belongsTo) throws Exception;

    Response upload(AttachmentUploadRequestBody requestBody);

}

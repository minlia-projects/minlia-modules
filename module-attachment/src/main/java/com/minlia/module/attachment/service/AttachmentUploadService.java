package com.minlia.module.attachment.service;


import com.minlia.cloud.body.Response;
import com.minlia.module.attachment.bean.AttachmentUploadBody;
import com.minlia.module.attachment.bean.AttachmentUploadRo;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author garen
 * @date 2018/6/6
 */
public interface AttachmentUploadService {

    Response upload(MultipartFile file) throws Exception;

    Response upload(MultipartFile file, String relationId, String belongsTo) throws Exception;

    Response uploadByBase64ToLocal(AttachmentUploadBody uploadBody, String relationId, String belongsTo);

    Response upload(AttachmentUploadRo to);

}
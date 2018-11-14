package com.minlia.modules.attachment.service;


import com.minlia.cloud.body.Response;
import com.minlia.modules.attachment.bean.AttachmentUploadTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by garen on 2018/6/6.
 */
public interface AttachmentUploadService {

    Response upload(MultipartFile file) throws Exception;

    Response upload(MultipartFile file, String relationId, String belongsTo) throws Exception;

//    Response upload(AttachmentUploadTO to);

}

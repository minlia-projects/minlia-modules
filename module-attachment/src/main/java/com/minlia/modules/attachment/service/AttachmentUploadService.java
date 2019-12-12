package com.minlia.modules.attachment.service;


import com.minlia.cloud.body.Response;
import com.minlia.modules.aliyun.oss.bean.OssFile;
import com.minlia.modules.attachment.ro.AttachmentBase64UploadRO;
import com.minlia.modules.attachment.ro.AttachmentUploadRO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by garen on 2018/6/6.
 */
public interface AttachmentUploadService {

    Response upload(MultipartFile file) throws Exception;

    Response upload(MultipartFile file, String relationId, String belongsTo) throws Exception;

    Response uploadByBase64(AttachmentBase64UploadRO uploadRO, String relationId, String belongsTo) throws Exception;

//    OssFile uploadByBase64ToLocal(AttachmentBase64UploadRO uploadBody, String relationId, String belongsTo);

    Response upload(AttachmentUploadRO to);

}

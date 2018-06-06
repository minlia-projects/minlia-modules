package com.minlia.modules.qcloud.oss.util;

import com.qcloud.cos.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by garen on 2018/6/6.
 */
public class QcloudCosUtils {

    private static boolean detectContentType = false;

    public static ObjectMetadata createDefaultObjectMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        if (detectContentType) {
            // TODO
        } else {
            metadata.setContentType(file.getContentType());
        }
        return metadata;
    }

}

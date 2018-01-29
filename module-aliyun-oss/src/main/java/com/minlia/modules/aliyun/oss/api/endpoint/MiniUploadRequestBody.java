package com.minlia.modules.aliyun.oss.api.endpoint;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Created by Calvin on 2017/8/3.
 */
@Data
public class MiniUploadRequestBody implements Serializable{

    private MultipartFile file;

}

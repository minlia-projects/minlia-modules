package com.minlia.modules.aliyun.oss.api.endpoint;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.aliyun.oss.api.body.MtsRequestBody;
import com.minlia.modules.aliyun.oss.api.body.MtsResponseBody;
import com.minlia.modules.aliyun.oss.api.body.UploadResponseBody;
import com.minlia.modules.aliyun.oss.api.config.AliyunOssProperties;
import com.minlia.modules.aliyun.oss.api.constant.AliyunOssCode;
import com.minlia.modules.aliyun.oss.api.enumeration.MtsTemplateType;
import com.minlia.modules.aliyun.oss.api.service.MtsService;
import com.minlia.modules.aliyun.oss.api.service.OssService;
import com.minlia.modules.aliyun.oss.bean.OssFile;
import com.minlia.modules.aliyun.oss.builder.PathBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
@Api(tags = "upload", description = "文件上传服务")
public class UploadEndpoint {

    @Autowired
    private AliyunOssProperties properties;
    
    @Resource(type = OssService.class)
    private OssService ossService;
    
    @Resource(type = MtsService.class)
    private MtsService mtsService;

    public static final String RETURN_URL_PATTERN="//%s/%s";

    @PostMapping(value = "file/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "文件上传服务", notes = "文件上传服务", httpMethod = "POST")
    public Response upload(MultipartFile file) throws Exception {
        OssFile ossFile = null;
        try {
            ossFile = ossService.upload(file);
        } catch (Exception e) {
            ApiAssert.state(false, AliyunOssCode.Exception.UPLOAD_FAILURE, e.getMessage());
        }

        UploadResponseBody ret = new UploadResponseBody();
        ret.setCode(1);
        ret.setStatus(1);
        ret.setMessage(ossFile.getUrl());
        ret.setLink(ossFile.getUrl());
        return ret;
    }

    @ApiOperation(value = "视屏上传", notes = "视屏上传", httpMethod = "POST")
    @PostMapping(value = "video/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response video(MultipartFile file) throws Exception {
        //上传文件
        String inputObject=keyGenerateByContentType();

        OssFile ossFile = null;
        try {
            ossFile = ossService.upload(file,inputObject);
        } catch (Exception e) {
            ApiAssert.state(false, AliyunOssCode.Exception.UPLOAD_FAILURE, e.getMessage());
        }
        String inputObjectUrl = ossFile.getUrl();

        //转码
        String outputObject = outputObjectGenerate(MtsTemplateType.MP4_HD);
        MtsResponseBody responseBody = mtsService.transcoding(MtsRequestBody.builder().inputObject(inputObject).outputObject(outputObject).mtsTemplateType(MtsTemplateType.MP4_HD).build());
        responseBody.setInputObjectUrl(inputObjectUrl);
        if(responseBody.getSuccess()){
            String outputObjectUrl=String.format(RETURN_URL_PATTERN,properties.getBucket(),properties.getEndpoint(),outputObject);
            responseBody.setOutputObjectUrl(outputObjectUrl);
            return Response.success(responseBody);
        }else {
            return Response.success(responseBody);
        }
    }

    @ApiOperation(value = "视屏转码", notes = "视屏转码", httpMethod = "POST")
    @PostMapping(value = "transcoding", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response transcoding(@Valid @RequestBody MtsRequestBody requestBody) throws Exception {
        mtsService.transcoding(requestBody);
        return Response.success();
    }

    private String outputObjectGenerate(MtsTemplateType mtsTemplateType){
        return PathBuilder.dateBuild() + UUID.randomUUID().toString() + "." + mtsTemplateType.getFormat();
    }

    private String keyGenerateByContentType(){
        return PathBuilder.dateBuild() + UUID.randomUUID().toString();
    }

}

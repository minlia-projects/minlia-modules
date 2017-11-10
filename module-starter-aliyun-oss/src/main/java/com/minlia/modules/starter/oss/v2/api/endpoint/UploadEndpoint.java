package com.minlia.modules.starter.oss.v2.api.endpoint;


import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.modules.starter.oss.v2.api.body.MtsRequestBody;
import com.minlia.modules.starter.oss.v2.api.body.MtsResponseBody;
import com.minlia.modules.starter.oss.v2.api.body.UploadResponseBody;
import com.minlia.modules.starter.oss.v2.api.code.UploadCode;
import com.minlia.modules.starter.oss.v2.api.config.AliyunOssProperties;
import com.minlia.modules.starter.oss.v2.api.enumeration.MtsTemplateTypeEnum;
import com.minlia.modules.starter.oss.v2.api.service.MtsService;
import com.minlia.modules.starter.oss.v2.api.service.OssService;
import com.minlia.modules.starter.oss.v2.builder.DatePathBuilder;
import com.minlia.modules.starter.oss.v2.builder.PathBuilder;
import com.minlia.modules.starter.oss.v2.builder.UuidFileNameBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
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

    @Autowired
    ResourceLoader resourceloader;

    @Resource(type = OssService.class)
    private OssService ossService;

    @Resource(
            type = MtsService.class
    )
    private MtsService mtsService;

    public static final String OLD_RETURN_URL_PATTERN="//%s.%s/%s";
    public static final String RETURN_URL_PATTERN="//%s/%s";

    public static final String ORIGIN_ENDPOINT = "oss-cn-shenzhen.aliyuncs.com";

    @PostMapping(value = "file/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "文件上传服务", notes = "文件上传服务", httpMethod = "POST")
    public StatefulBody upload(MultipartFile file) throws Exception {
        String key=keyGenerate(file);
        try {
           ossService.upload(file,key);
        } catch (Exception e) {
            ApiPreconditions.checkNotNull(e, UploadCode.E22, e.getMessage());
        }

        String uri = this.builderUri(key);

        UploadResponseBody ret = new UploadResponseBody();
        ret.setCode(1);
        ret.setStatus(1);
        ret.setMessage(uri);
        ret.setLink(uri);
        return ret;
    }

    @ApiOperation(value = "视屏上传", notes = "视屏上传", httpMethod = "POST")
    @PostMapping(value = "video/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody video(MultipartFile file) throws Exception {

        System.out.println(file.getOriginalFilename());
        System.out.println(file.getContentType());
        System.out.println(file.getName());
        //上传文件
        String inputObject=keyGenerateByContentType();
        try {
            ossService.upload(file,inputObject);
        } catch (Exception e) {
            ApiPreconditions.checkNotNull(e, UploadCode.E22, e.getMessage());
        }
        String inputObjectUrl = this.builderUri(inputObject);

        //转码
        String outputObject = outputObjectGenerate(MtsTemplateTypeEnum.MP4_HD);
        MtsResponseBody responseBody = mtsService.transcoding(MtsRequestBody.builder().inputObject(inputObject).outputObject(outputObject).mtsTemplateType(MtsTemplateTypeEnum.MP4_HD).build());
        responseBody.setInputObjectUrl(inputObjectUrl);
        if(responseBody.getSuccess()){
            String outputObjectUrl=String.format(RETURN_URL_PATTERN,properties.getBucket(),properties.getEndpoint(),outputObject);
            responseBody.setOutputObjectUrl(outputObjectUrl);
            return SuccessResponseBody.builder().payload(responseBody).build();
        }else {
            return SuccessResponseBody.builder().payload(responseBody).code(2).build();
        }
    }

    @ApiOperation(value = "视屏转码", notes = "视屏转码", httpMethod = "POST")
    @PostMapping(value = "transcoding", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody transcoding(@Valid @RequestBody MtsRequestBody requestBody) throws Exception {
        mtsService.transcoding(requestBody);
        return SuccessResponseBody.builder().build();
    }

    private String outputObjectGenerate(MtsTemplateTypeEnum mtsTemplateType){
        PathBuilder pathBuilder=new DatePathBuilder();
        return pathBuilder.build() + UUID.randomUUID().toString() + "." + mtsTemplateType.getFormat();
    }

    private String keyGenerate(MultipartFile file){
        UuidFileNameBuilder builder = new UuidFileNameBuilder();
        PathBuilder pathBuilder=new DatePathBuilder();
        String key = pathBuilder.build() +builder.build(file.getOriginalFilename());
        return key;
    }

    private String keyGenerateByContentType(){
        PathBuilder pathBuilder=new DatePathBuilder();
        String key = pathBuilder.build() + UUID.randomUUID().toString();
        return key;
    }

    private String builderUri(String key){
        String uri;
        if (ORIGIN_ENDPOINT.equals(properties.getEndpoint())) {
            uri=String.format(OLD_RETURN_URL_PATTERN,properties.getBucket(),properties.getEndpoint(),key);
        } else {
            uri=String.format(RETURN_URL_PATTERN,properties.getEndpoint(),key);
        }
        return uri;
    }
}

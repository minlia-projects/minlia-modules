//package com.minlia.modules.qcloud.oss.endpoint;
//
//
//import com.minlia.modules.qcloud.oss.service.QcloudCosService;
//import com.minlia.modules.qcloud.oss.util.QcloudCosUtils;
//import com.qcloud.cos.model.PutObjectRequest;
//import com.qcloud.cos.model.PutObjectResult;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//@Api(tags = "Qcloud Cos", description = "腾讯云对象存储")
//@RestController
//@RequestMapping(value = "/api")
//public class QcloudCosEndpoint {
//
//    @Autowired
//    private QcloudCosService qcloudCosService;
//
//    public static final String RETURN_URL_PATTERN="//%s/%s";
//
//    @PostMapping(value = "file/upload", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(value = "文件上传服务", notes = "文件上传服务", httpMethod = "POST")
//    public PutObjectResult upload(MultipartFile file) throws Exception {
//        PutObjectRequest request = new PutObjectRequest("mdl-1255765487",file.getName(),file.getInputStream(), QcloudCosUtils.createDefaultObjectMetadata(file));
//        PutObjectResult result = qcloudCosService.putObject(request);
//        return result;
//    }
//
////    @ApiOperation(value = "视屏上传", notes = "视屏上传", httpMethod = "POST")
////    @PostMapping(value = "video/upload", produces = MediaType.APPLICATION_JSON_VALUE)
////    public StatefulBody video(MultipartFile file) throws Exception {
////        //上传文件
////        String inputObject=keyGenerateByContentType();
////
////        OssFile ossFile = null;
////        try {
////            ossFile = ossService.upload(file,inputObject);
////        } catch (Exception e) {
////            ApiPreconditions.checkNotNull(e, UploadCode.E22, e.getMessage());
////        }
////        String inputObjectUrl = ossFile.getUrl();
////
////        //转码
////        String outputObject = outputObjectGenerate(MtsTemplateType.MP4_HD);
////        MtsResponseBody responseBody = mtsService.transcoding(MtsRequestBody.builder().inputObject(inputObject).outputObject(outputObject).mtsTemplateType(MtsTemplateType.MP4_HD).build());
////        responseBody.setInputObjectUrl(inputObjectUrl);
////        if(responseBody.getSuccess()){
////            String outputObjectUrl=String.format(RETURN_URL_PATTERN,properties.getBucket(),properties.getEndpoint(),outputObject);
////            responseBody.setOutputObjectUrl(outputObjectUrl);
////            return SuccessResponseBody.builder().payload(responseBody).build();
////        }else {
////            return SuccessResponseBody.builder().payload(responseBody).code(2).build();
////        }
////    }
//
////    @ApiOperation(value = "视屏转码", notes = "视屏转码", httpMethod = "POST")
////    @PostMapping(value = "transcoding", produces = MediaType.APPLICATION_JSON_VALUE)
////    public StatefulBody transcoding(@Valid @RequestBody MtsRequestBody requestBody) throws Exception {
////        mtsService.transcoding(requestBody);
////        return SuccessResponseBody.builder().build();
////    }
//
////    private String outputObjectGenerate(MtsTemplateType mtsTemplateType){
////        return PathBuilder.dateBuild() + UUID.randomUUID().toString() + "." + mtsTemplateType.getFormat();
////    }
////
////    private String keyGenerateByContentType(){
////        return PathBuilder.dateBuild() + UUID.randomUUID().toString();
////    }
//
//}

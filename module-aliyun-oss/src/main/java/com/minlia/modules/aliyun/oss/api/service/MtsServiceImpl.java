package com.minlia.modules.aliyun.oss.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.mts.model.v20140618.SubmitJobsRequest;
import com.aliyuncs.mts.model.v20140618.SubmitJobsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.minlia.modules.aliyun.oss.api.body.MtsRequestBody;
import com.minlia.modules.aliyun.oss.api.body.MtsResponseBody;
import com.minlia.modules.aliyun.oss.api.config.AliyunMtsProperties;
import com.minlia.modules.aliyun.oss.api.enumeration.MtsTemplateType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by garen on 2017/8/29.
 */
@Slf4j
@Service
public class MtsServiceImpl implements MtsService {

    @Autowired
    private AliyunMtsProperties aliyunMtsProperties;

    @Override
    public MtsResponseBody transcoding(MtsRequestBody requestbody) {
        MtsTemplateType mtsTemplateType = null == requestbody.getMtsTemplateType() ? MtsTemplateType.MP4_HD : requestbody.getMtsTemplateType();
        String transcodeTemplateId = mtsTemplateType.getValue();

        DefaultProfile profile = DefaultProfile.getProfile(aliyunMtsProperties.getRegion(), aliyunMtsProperties.getAccessKeyId(), aliyunMtsProperties.getAccessKeySecret());
        DefaultAcsClient client = new DefaultAcsClient(profile);

        SubmitJobsRequest request = new SubmitJobsRequest();
        request.setPipelineId(aliyunMtsProperties.getPipelineId());

        JSONObject input = new JSONObject();
        input.put("Location", aliyunMtsProperties.getOssLocation());
        input.put("Bucket", aliyunMtsProperties.getInputBucket());
        input.put("Object", requestbody.getInputObject());
        request.setInput(input.toJSONString());

        JSONArray outputs = new JSONArray();
        JSONObject outputConfigJson = new JSONObject();
        outputConfigJson.put("TemplateId", transcodeTemplateId);
        outputConfigJson.put("OutputObject", requestbody.getOutputObject());
        outputs.add(outputConfigJson);
        request.setOutputs(outputs.toJSONString());

        request.setOutputBucket(aliyunMtsProperties.getOutputBucket());
        request.setOutputLocation(aliyunMtsProperties.getOssLocation());

        SubmitJobsResponse response= null;
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            log.error("MTS error:",e);
            return MtsResponseBody.builder().success(false).inputObject(requestbody.getInputObject()).mtsTemplateType(mtsTemplateType).build();
        }

        SubmitJobsResponse.JobResult jobResult = response.getJobResultList().get(0);
        if(jobResult.getSuccess()){
            System.out.println(String.format("SubmitJob Success, JobId=%s", jobResult.getJob().getJobId()));
        }else {
            System.out.println(String.format("SubmitJob Failed, RequestId=%s;Code=%s;Message=%s", response.getRequestId(), jobResult.getCode(), jobResult.getMessage()));
        }
        MtsResponseBody responseBody = MtsResponseBody.builder().requestId(response.getRequestId()).success(jobResult.getSuccess()).inputObject(requestbody.getInputObject()).outputObject(requestbody.getOutputObject()).mtsTemplateType(mtsTemplateType).build();
        return responseBody;
    }

}

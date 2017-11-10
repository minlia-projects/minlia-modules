package com.minlia.modules.starter.oss;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.mts.model.v20140618.SubmitJobsRequest;
import com.aliyuncs.mts.model.v20140618.SubmitJobsResponse;
import com.aliyuncs.profile.DefaultProfile;
import org.junit.Test;


public class OssTest {

   @Test
    public void transcoding(){
        String region = "cn-shenzhen";
        String accessKeyId = "LTAIr2ZHCWVZEc1K";
        String accessKeySecret = "zC1HPTWMmD4aEBWdECSGcVAWhAoUcA";

        String pipelineId = "746d9ccee8c54829887cf66e85a09805";
        //oss-cn-hangzhou、oss-cn-shanghai、oss-us-west-1等;与region对应
        String ossLocation = "oss-cn-shenzhen";
        String inputObject = "video.mp4";
        String inputBucket = "wanquancaifu";
        String outputObject = "video22.mp4";
        String outputBucket = "wanquancaifu";
        String transcodeTemplateId = "S00000001-200020";

        DefaultProfile profile = DefaultProfile.getProfile(region, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        SubmitJobsRequest request = new SubmitJobsRequest();
        request.setPipelineId(pipelineId);

        JSONObject input = new JSONObject();
        input.put("Location", ossLocation);
        input.put("Bucket", inputBucket);
        input.put("Object", inputObject);
        request.setInput(input.toJSONString());

        JSONArray outputs = new JSONArray();
        JSONObject outputConfigJson = new JSONObject();
        outputConfigJson.put("TemplateId", transcodeTemplateId);
        outputConfigJson.put("OutputObject", outputObject);
        outputs.add(outputConfigJson);
        request.setOutputs(outputs.toJSONString());

        request.setOutputBucket(outputBucket);
        request.setOutputLocation(ossLocation);
        request.setOutputBucket(outputBucket);

        SubmitJobsResponse response= null;
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        SubmitJobsResponse.JobResult jobResult = response.getJobResultList().get(0);
        if(jobResult.getSuccess()){
            System.out.println(String.format("SubmitJob Success, JobId=%s", jobResult.getJob().getJobId()));
        }else {
            System.out.println(String.format("SubmitJob Failed, RequestId=%s;Code=%s;Message=%s",
                    response.getRequestId(), jobResult.getCode(), jobResult.getMessage()));
        }
    }

}

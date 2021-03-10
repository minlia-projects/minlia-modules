//package com.minlia.aliyun.green.util;
//
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.exceptions.ServerException;
//import com.aliyuncs.profile.DefaultProfile;
//import com.google.gson.Gson;
//import java.util.*;
//import com.aliyuncs.imageaudit.model.v20191230.*;
//
//public class RecognizeSpamText {
//
//    public static void main(String[] args) {
//        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", "<accessKeyId>", "<accessSecret>");
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        RecognizeSpamTextRequest request = new RecognizeSpamTextRequest();
//
//        List<RecognizeSpamTextRequest.Tasks> tasksList = new ArrayList<RecognizeSpamTextRequest.Tasks>();
//
//        RecognizeSpamTextRequest.Tasks tasks1 = new RecognizeSpamTextRequest.Tasks();
//        tasks1.setContent("本校小额贷款，安全、快捷、方便、无抵押，随机随贷，当天放款，上门服务。联系weixin 946932");
//        tasksList.add(tasks1);
//        request.setTaskss(tasksList);
//
//        List<RecognizeSpamTextRequest.Labels> labelsList = new ArrayList<RecognizeSpamTextRequest.Labels>();
//
//        RecognizeSpamTextRequest.Labels labels1 = new RecognizeSpamTextRequest.Labels();
//        labels1.setLabel("spam");
//        labelsList.add(labels1);
//
//        RecognizeSpamTextRequest.Labels labels2 = new RecognizeSpamTextRequest.Labels();
//        labels2.setLabel("politics");
//        labelsList.add(labels2);
//
//        RecognizeSpamTextRequest.Labels labels3 = new RecognizeSpamTextRequest.Labels();
//        labels3.setLabel("abuse");
//        labelsList.add(labels3);
//
//        RecognizeSpamTextRequest.Labels labels4 = new RecognizeSpamTextRequest.Labels();
//        labels4.setLabel("terrorism");
//        labelsList.add(labels4);
//
//        RecognizeSpamTextRequest.Labels labels5 = new RecognizeSpamTextRequest.Labels();
//        labels5.setLabel("porn");
//        labelsList.add(labels5);
//
//        RecognizeSpamTextRequest.Labels labels6 = new RecognizeSpamTextRequest.Labels();
//        labels6.setLabel("flood");
//        labelsList.add(labels6);
//
//        RecognizeSpamTextRequest.Labels labels7 = new RecognizeSpamTextRequest.Labels();
//        labels7.setLabel("contraband");
//        labelsList.add(labels7);
//
//        RecognizeSpamTextRequest.Labels labels8 = new RecognizeSpamTextRequest.Labels();
//        labels8.setLabel("ad");
//        labelsList.add(labels8);
//        request.setLabelss(labelsList);
//
//        try {
//            RecognizeSpamTextResponse response = client.getAcsResponse(request);
//            System.out.println(new Gson().toJson(response));
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            System.out.println("ErrCode:" + e.getErrCode());
//            System.out.println("ErrMsg:" + e.getErrMsg());
//            System.out.println("RequestId:" + e.getRequestId());
//        }
//
//    }
//}
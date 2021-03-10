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
//public class RecognizeTerrorismText {
//
//    public static void main(String[] args) {
//        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", "<accessKeyId>", "<accessSecret>");
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        RecognizeTerrorismTextRequest request = new RecognizeTerrorismTextRequest();
//
//        List<RecognizeTerrorismTextRequest.Tasks> tasksList = new ArrayList<RecognizeTerrorismTextRequest.Tasks>();
//
//        RecognizeTerrorismTextRequest.Tasks tasks1 = new RecognizeTerrorismTextRequest.Tasks();
//        tasks1.setContent("王天刚去饭店吃饭后发现自己的车子被刮了，破口大骂是哪个傻逼干的？");
//        tasksList.add(tasks1);
//        request.setTaskss(tasksList);
//
//        List<RecognizeTerrorismTextRequest.Labels> labelsList = new ArrayList<RecognizeTerrorismTextRequest.Labels>();
//
//        RecognizeTerrorismTextRequest.Labels labels1 = new RecognizeTerrorismTextRequest.Labels();
//        labels1.setLabel("spam");
//        labelsList.add(labels1);
//
//        RecognizeTerrorismTextRequest.Labels labels2 = new RecognizeTerrorismTextRequest.Labels();
//        labels2.setLabel("politics");
//        labelsList.add(labels2);
//
//        RecognizeTerrorismTextRequest.Labels labels3 = new RecognizeTerrorismTextRequest.Labels();
//        labels3.setLabel("abuse");
//        labelsList.add(labels3);
//
//        RecognizeTerrorismTextRequest.Labels labels4 = new RecognizeTerrorismTextRequest.Labels();
//        labels4.setLabel("terrorism");
//        labelsList.add(labels4);
//
//        RecognizeTerrorismTextRequest.Labels labels5 = new RecognizeTerrorismTextRequest.Labels();
//        labels5.setLabel("porn");
//        labelsList.add(labels5);
//
//        RecognizeTerrorismTextRequest.Labels labels6 = new RecognizeTerrorismTextRequest.Labels();
//        labels6.setLabel("flood");
//        labelsList.add(labels6);
//
//        RecognizeTerrorismTextRequest.Labels labels7 = new RecognizeTerrorismTextRequest.Labels();
//        labels7.setLabel("contraband");
//        labelsList.add(labels7);
//
//        RecognizeTerrorismTextRequest.Labels labels8 = new RecognizeTerrorismTextRequest.Labels();
//        labels8.setLabel("ad");
//        labelsList.add(labels8);
//        request.setLabelss(labelsList);
//
//        try {
//            RecognizeTerrorismTextResponse response = client.getAcsResponse(request);
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
//package com.minlia.aliyun.green;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.aliyun.oss.ClientException;
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.exceptions.ServerException;
//import com.aliyuncs.green.model.v20180509.TextScanRequest;
//import com.aliyuncs.http.FormatType;
//import com.aliyuncs.http.HttpResponse;
//import com.aliyuncs.profile.DefaultProfile;
//import com.aliyuncs.profile.IClientProfile;
//import com.minlia.aliyun.green.bean.AliyunGreenResult;
//
//import java.util.*;
//
//public class Main {
//
////    public static void main(String[] args) throws Exception {
////        IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", "LTAI4GL2xYzQAJiKU3W7DWJ3", "W65DgDth45J4CT8aJmidjSUgsbHHI1");
////        DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", "Green", "green.cn-shanghai.aliyuncs.com");
//////        DefaultProfile.addEndpoint("cn-shanghai", "Green", "green.cn-shanghai.aliyuncs.com");
////
////        IAcsClient client = new DefaultAcsClient(profile);
////        TextFeedbackRequest textFeedbackRequest = new TextFeedbackRequest();
////        textFeedbackRequest.setAcceptFormat(FormatType.JSON);
////        textFeedbackRequest.setHttpContentType(FormatType.JSON);
////        textFeedbackRequest.setMethod(com.aliyuncs.http.MethodType.POST);
////        textFeedbackRequest.setEncoding("UTF-8");
////        textFeedbackRequest.setRegionId("cn-shanghai");
////
////        List<Map<String, Object>> tasks = new ArrayList();
////        Map<String, Object> task1 = new LinkedHashMap();
////        task1.put("dataId", UUID.randomUUID().toString());
////        task1.put("content", "本校小额贷款，安全、快捷、方便、无抵押，随机随贷，当天放款，上门服务。联系weixin 946932");
////        tasks.add(task1);
////
////        JSONObject data = new JSONObject();
////        //内容检测
////        data.put("scenes", Arrays.asList("antispam"));
////        data.put("taskId", "txt6z3Na17XbrD4P7QdJzYbk1-1q4seJ");
////        data.put("tasks", tasks);
////
////
////        System.out.println(JSON.toJSONString(data, true));
////        textFeedbackRequest.setHttpContent(data.toJSONString().getBytes("UTF-8"), "UTF-8", FormatType.JSON);
////
////        // 请务必设置超时时间。
////        textFeedbackRequest.setConnectTimeout(3000);
////        textFeedbackRequest.setReadTimeout(6000);
////        try {
////            HttpResponse httpResponse = client.doAction(textFeedbackRequest);
////            if(httpResponse.isSuccess()){
////                JSONObject scrResponse = JSON.parseObject(new String(httpResponse.getHttpContent(), "UTF-8"));
////                System.out.println(JSON.toJSONString(scrResponse, true));
////            }else{
////                System.out.println("response not success. status:" + httpResponse.getStatus());
////            }
////        } catch (ServerException e) {
////            e.printStackTrace();
////        } catch (ClientException e) {
////            e.printStackTrace();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//    public static void main(String[] args) throws Exception {
//        IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", "LTAI4GL2xYzQAJiKU3W7DWJ3", "W65DgDth45J4CT8aJmidjSUgsbHHI1");
//        DefaultProfile.addEndpoint("cn-shanghai", "Green", "green.cn-shanghai.aliyuncs.com");
//        IAcsClient client = new DefaultAcsClient(profile);
//        TextScanRequest textScanRequest = new TextScanRequest();
//        textScanRequest.setAcceptFormat(FormatType.JSON);
//        textScanRequest.setHttpContentType(FormatType.JSON);
//        textScanRequest.setMethod(com.aliyuncs.http.MethodType.POST);
//        textScanRequest.setEncoding("UTF-8");
//        textScanRequest.setRegionId("cn-shanghai");
//        List<Map<String, Object>> tasks = new ArrayList();
//        Map<String, Object> task1 = new LinkedHashMap();
//        task1.put("dataId", UUID.randomUUID().toString());
//        /**
//         * 待检测的文本，长度不超过10000个字符。
//         */
////        task1.put("content", "test");
//        task1.put("content", "本校小额贷款，安全、快捷、方便、无抵押，随机随贷，当天放款，上门服务。联系weixin 946932");
//        tasks.add(task1);
//        JSONObject data = new JSONObject();
//
//        /**
//         * 检测场景。文本垃圾检测请传递antispam。
//         **/
//        data.put("scenes", Arrays.asList("antispam"));
//        data.put("tasks", tasks);
//        textScanRequest.setHttpContent(data.toJSONString().getBytes("UTF-8"), "UTF-8", FormatType.JSON);
//        // 请务必设置超时时间。
//        textScanRequest.setConnectTimeout(3000);
//        textScanRequest.setReadTimeout(6000);
//        try {
//            HttpResponse httpResponse = client.doAction(textScanRequest);
//            if (httpResponse.isSuccess()) {
//                AliyunGreenResult result = AliyunGreenResult.format(new String(httpResponse.getHttpContent(), "UTF-8"));
//            } else {
//                System.out.println("response not success. status:" + httpResponse.getStatus());
//            }
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}
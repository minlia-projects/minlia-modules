//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.green.model.v20180509.ImageSyncScanRequest;
//import com.aliyuncs.http.FormatType;
//import com.aliyuncs.http.HttpResponse;
//import com.aliyuncs.http.MethodType;
//import com.aliyuncs.http.ProtocolType;
//import com.aliyuncs.profile.DefaultProfile;
//import com.aliyuncs.profile.IClientProfile;
//import com.minlia.aliyun.green.bean.AliyunGreenImageResult;
//import com.minlia.aliyun.green.config.AliyunGreenConfig;
//import com.minlia.aliyun.green.uploader.ClientUploader;
//import lombok.RequiredArgsConstructor;
//import org.apache.commons.io.FileUtils;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.io.File;
//import java.util.*;
//
//@Component
//@RequiredArgsConstructor
//public class Main {
//
//    private final AliyunGreenConfig aliyunGreenConfig;
//    private static IAcsClient client = null;
//
//    public Main handle(String url) {
//        return handle(url, false);
//    }
//
//    public static void main(String[] args) throws Exception {
//        IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", "请填写您的accessKeyId", "请填写您的accessKeySecret");
//        DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", "Green", "green.cn-shanghai.aliyuncs.com");
//        IAcsClient client = new DefaultAcsClient(profile);
//
//
//
//        /**
//         * 如果您要检测的文件存于本地服务器上，可以通过下述代码片生成URL。
//         * 再将返回的URL作为图片地址传递到服务端进行检测。
//         */
//        ClientUploader clientUploader = ClientUploader.getImageClientUploader(profile, false);
//        byte[] imageBytes = null;
//        String url = null;
//        try {
//            // 这里读取本地文件作为二进制数据，当做输入做为示例。实际使用中请直接替换成您的图片二进制数据。
//            imageBytes = FileUtils.readFileToByteArray(new File("/Users/01fb4ab6420b5f34623e13b82b51ef87.jpg"));
//            // 上传到服务端。
//            url = clientUploader.uploadBytes(imageBytes);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//
//        imageSyncScanRequest.setHttpContent(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(httpBody.toJSONString()),
//                "UTF-8", FormatType.JSON);
//
//
//        HttpResponse httpResponse = null;
//        try {
//            httpResponse = client.doAction(imageSyncScanRequest);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // 服务端接收到请求，完成处理后返回的结果。
//        if (httpResponse != null && httpResponse.isSuccess()) {
//            JSONObject scrResponse = JSON.parseObject(org.apache.commons.codec.binary.StringUtils.newStringUtf8(httpResponse.getHttpContent()));
//            System.out.println(JSON.toJSONString(scrResponse, true));
//            int requestCode = scrResponse.getIntValue("code");
//            // 每一张图片的检测结果。
//            JSONArray taskResults = scrResponse.getJSONArray("data");
//            if (200 == requestCode) {
//                for (Object taskResult : taskResults) {
//                    // 单张图片的处理结果。
//                    int taskCode = ((JSONObject) taskResult).getIntValue("code");
//                    // 图片对应检测场景的处理结果。如果是多个场景，则会有每个场景的结果。
//                    JSONArray sceneResults = ((JSONObject) taskResult).getJSONArray("results");
//                    if (200 == taskCode) {
//                        for (Object sceneResult : sceneResults) {
//                            String scene = ((JSONObject) sceneResult).getString("scene");
//                            String suggestion = ((JSONObject) sceneResult).getString("suggestion");
//                            // 根据scene和suggestion做相关处理。
//                            // 根据不同的suggestion结果做业务上的不同处理。例如，将违规数据删除等。
//                            System.out.println("scene = [" + scene + "]");
//                            System.out.println("suggestion = [" + suggestion + "]");
//                        }
//                    } else {
//                        // 单张图片处理失败，原因视具体的情况详细分析。
//                        System.out.println("task process fail. task response:" + JSON.toJSONString(taskResult));
//                    }
//                }
//            } else {
//                /**
//                 * 表明请求整体处理失败，原因视具体的情况详细分析。
//                 */
//                System.out.println("the whole image scan request failed. response:" + JSON.toJSONString(scrResponse));
//            }
//        }
//    }
//
//    @PostConstruct
//    public void init() {
//        IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", aliyunGreenConfig.getAccessKeyId(), aliyunGreenConfig.getAccessKeySecret());
//        DefaultProfile.addEndpoint("cn-shanghai", "Green", "green.cn-shanghai.aliyuncs.com");
//        client = new DefaultAcsClient(profile);
//    }
//
//    public static ImageSyncScanRequest getRequest() {
//        ImageSyncScanRequest imageSyncScanRequest = new ImageSyncScanRequest();
//        // 指定API返回格式。
//        imageSyncScanRequest.setAcceptFormat(FormatType.JSON);
//        // 指定请求方法。
//        imageSyncScanRequest.setMethod(MethodType.POST);
//        imageSyncScanRequest.setEncoding("utf-8");
//        // 支持HTTP和HTTPS。
//        imageSyncScanRequest.setProtocol(ProtocolType.HTTP);
//        /**
//         * 请设置超时时间。服务端全链路处理超时时间为10秒，请做相应设置。
//         * 如果您设置的ReadTimeout小于服务端处理的时间，程序中会获得一个read timeout异常。
//         */
//        imageSyncScanRequest.setConnectTimeout(3000);
//        imageSyncScanRequest.setReadTimeout(10000);
//        return imageSyncScanRequest;
//    }
//
//    private static JSONObject getData(List scenes, String url) {
//        JSONObject httpBody = new JSONObject();
//        /**
//         * 设置要检测的场景。计费依据此处传递的场景计算。
//         * 一次请求中可以同时检测多张图片，每张图片可以同时检测多个风险场景，计费按照场景计算。
//         * 例如，检测2张图片，场景传递porn和terrorism，计费会按照2张图片鉴黄，2张图片暴恐检测计算。
//         * porn：表示色情场景检测。
//         */
//        httpBody.put("scenes", scenes);
//
//        /**
//         * 设置待检测图片。一张图片对应一个task。
//         * 多张图片同时检测时，处理的时间由最后一个处理完的图片决定。
//         * 通常情况下批量检测的平均响应时间比单张检测的要长，一次批量提交的图片数越多，响应时间被拉长的概率越高。
//         * 这里以单张图片检测作为示例。如果是批量图片检测，请自行构建多个task。
//         */
//        JSONObject task = new JSONObject();
//        task.put("dataId", UUID.randomUUID().toString());
//
//        // 设置图片链接为上传后的URL。
//        task.put("url", url);
//        task.put("time", new Date());
//        httpBody.put("tasks", Arrays.asList(task));
//
//        return httpBody;
//    }
//
//}
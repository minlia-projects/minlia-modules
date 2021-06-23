//package com.minlia.aliyun.sts.service;
//
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.http.MethodType;
//import com.aliyuncs.profile.DefaultProfile;
//import com.aliyuncs.profile.IClientProfile;
//import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
//import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
//
//public class StsServiceSample {
//
//    public static void main(String[] args) {
//        // STS接入地址，例如sts.cn-hangzhou.aliyuncs.com。
//        String endpoint = "sts.cn-hangzhou.aliyuncs.com";
//        // 填写步骤1生成的访问密钥AccessKey ID和AccessKey Secret。
//        String AccessKeyId = "";
//        String accessKeySecret = "";
//        // 填写步骤3获取的角色ARN。
//        String roleArn = "acs:ram:::role/ramoss";
//        // 自定义角色会话名称，用来区分不同的令牌，例如可填写为SessionTest。
//        String roleSessionName = "SessionRamoss";
//        // 以下Policy用于限制仅允许使用临时访问凭证向目标存储空间examplebucket上传文件。
//        // 临时访问凭证最后获得的权限是步骤4设置的角色权限和该Policy设置权限的交集，即仅允许将文件上传至目标存储空间examplebucket下的exampledir目录。
////        String policy = "{\n" +
////                "    \"Version\": \"1\", \n" +
////                "    \"Statement\": [\n" +
////                "        {\n" +
////                "            \"Action\": [\n" +
////                "                \"oss:PutObject\"\n" +
////                "            ], \n" +
////                "            \"Resource\": [\n" +
////                "                \"acs:oss:*:*:examplebucket/*\" \n" +
////                "            ], \n" +
////                "            \"Effect\": \"Allow\"\n" +
////                "        }\n" +
////                "    ]\n" +
////                "}";
//        try {
//            // 添加endpoint。
////            DefaultProfile.addEndpoint("", "", "Sts", endpoint);
//            DefaultProfile.addEndpoint("", "Sts", endpoint);
//
//            // 构造default profile。
//            IClientProfile profile = DefaultProfile.getProfile("", AccessKeyId, accessKeySecret);
//            // 构造client。
//            DefaultAcsClient client = new DefaultAcsClient(profile);
//            final AssumeRoleRequest request = new AssumeRoleRequest();
//            request.setMethod(MethodType.POST);
//            request.setRoleArn(roleArn);
//            request.setRoleSessionName(roleSessionName);
////            request.setPolicy(policy); // 如果policy为空，则用户将获得该角色下所有权限。
//            request.setDurationSeconds(3600L); // 设置临时访问凭证的有效时间为3600秒。
//            final AssumeRoleResponse response = client.getAcsResponse(request);
//            System.out.println("Expiration: " + response.getCredentials().getExpiration());
//            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
//            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
//            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
//            System.out.println("RequestId: " + response.getRequestId());
//        } catch (ClientException e) {
//            System.out.println("Failed：");
//            System.out.println("Error code: " + e.getErrCode());
//            System.out.println("Error message: " + e.getErrMsg());
//            System.out.println("RequestId: " + e.getRequestId());
//        }
//    }
//}
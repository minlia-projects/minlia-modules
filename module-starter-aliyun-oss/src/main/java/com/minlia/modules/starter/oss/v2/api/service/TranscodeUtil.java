//package com.minlia.modules.starter.oss.v2.api.service;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URLEncoder;
//import java.util.UUID;
//
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Component;
//
////import com.alibaba.fastjson.JSONArray;
////import com.alibaba.fastjson.JSONObject;
////import com.aliyun.api.AliyunClient;
////import com.aliyun.api.DefaultAliyunClient;
////import com.aliyun.api.domain.Job;
////import com.aliyun.api.domain.OSSFile;
////import com.aliyun.api.mts.mts20140618.request.QueryJobListRequest;
////import com.aliyun.api.mts.mts20140618.request.SubmitAnalysisJobRequest;
////import com.aliyun.api.mts.mts20140618.request.SubmitJobsRequest;
////import com.aliyun.api.mts.mts20140618.response.QueryJobListResponse;
////import com.aliyun.api.mts.mts20140618.response.SubmitAnalysisJobResponse;
////import com.aliyun.api.mts.mts20140618.response.SubmitJobsResponse;
//////import com.aliyun.mts.domain.OSSFileDO;
//import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.model.OSSObject;
//import com.aliyun.oss.model.ObjectMetadata;
////import com.taobao.api.ApiException;
//
//@Component("transcodeUtil")
//public class TranscodeUtil {
//    private static Logger log = Logger.getLogger(TranscodeUtil.class);
//
//	private static String MTS_SERVER_URL = "http://mts.aliyuncs.com/";
//    private static final String ossEndPoint = "http://oss-cn-hangzhou.aliyuncs.com";
//    private static final String location = "oss-cn-hangzhou";
//    private static String accessKeyId = "A253byb4X2r3NB3m";
//    private static String accessKeySecret = "kiXcEqr2VianvEkC3Lv4rfpL1H0g34";
//    private static String pipelineId = "741d42eb32964ccfadea979f529a91eb";
//
//    //private static String testVideoFilePath = "videos/test.amr";
//    private static String templateId = "S00000001-200020";
//    private static String inputBucket ="xcjservice";
//    private static String outputBucket = "xcjservice"; //   mediatranslate/voice/transcode/
//    private static String downloadKey = "voice/transcode/";
//
//    private static AliyunClient aliyunClient;
//
//    //private static OSSClient ossClient;
//
//    //初始化OSS client
//    static{
//    	aliyunClient = new DefaultAliyunClient(MTS_SERVER_URL, accessKeyId, accessKeySecret);
//    	//ossClient = new OSSClient(ossEndPoint, accessKeyId, accessKeySecret);
//    }
//
//	public static OSSFileDO uploadFile(String localVideoFile,String mediaObj) {
//		//本地文件开始上传
//		log.info("------ 本地文件上传开始------");
//		OSSClient ossClient = new OSSClient(ossEndPoint, accessKeyId, accessKeySecret);
//        try {
//            File file = new File(localVideoFile);
//            InputStream content = new FileInputStream(file);
//
//            ObjectMetadata meta = new ObjectMetadata();
//            meta.setContentLength(file.length());
//            ossClient.putObject(inputBucket, mediaObj, content, meta);
//
//            String encodedObjectName = URLEncoder.encode(mediaObj, "utf-8");
//            return new OSSFileDO(location, inputBucket, encodedObjectName);
//        } catch (Exception e) {
//        	e.printStackTrace();
//        	log.info("----- 文件上传异常:: "+e.getMessage());
//            throw new RuntimeException("fail uploadLocalFile");
//        }
//	}
//
//	//inputFile 上传至oss存储的文件(待转码object地址)
//	public static String systemTemplateJobFlow(OSSFileDO inputFile,String prefix) {
//		log.info("------文件上传至OSS存储完成------");
//        String analysisJobId = submitAnalysisJob(inputFile, pipelineId);
//        System.out.println("analysisJobId: "+analysisJobId);
//        String transcodeJobId = submitTranscodeJob(inputFile, templateId,prefix);
//        System.out.println("submit end");
//        Job transcodeJob = waitTranscodeJobComplete(transcodeJobId);
//
//        OSSFile outputFile  = transcodeJob.getOutput().getOutputFile();
//        String outputFileOSSUrl = "http://" + outputFile.getBucket() + ".oss-cn-hangzhou.aliyuncs.com/" + outputFile.getObject();
//        System.out.println("Transcode success, the target file url is: " + outputFileOSSUrl);
//        log.info("--------文件转码完成, 存储位置："+outputFileOSSUrl);
//        return outputFileOSSUrl;
//	}
//
//	private static String submitAnalysisJob(OSSFileDO inputFile, String pipelineId) {
//		//AnalysisJob 开始
//        SubmitAnalysisJobRequest request = new SubmitAnalysisJobRequest();
//
//        request.setInput(inputFile.toJsonString());
//        JSONObject jsonObject = new JSONObject();
//        JSONObject qualityControlJson = new JSONObject();
//        qualityControlJson.put("RateQuality", "25");
//        qualityControlJson.put("MethodStreaming", "network");
//        jsonObject.put("QualityControl", qualityControlJson);
//        request.setAnalysisConfig(jsonObject.toJSONString());
//        request.setPipelineId(pipelineId);
//
//        try {
//            SubmitAnalysisJobResponse response = aliyunClient.execute(request);
//            if(!response.isSuccess()) {
//                throw new RuntimeException("SubmitAnalysisJobRequest failed");
//        	}
//            //AnalysisJob 成功
//            return response.getAnalysisJob().getId();
//        } catch (ApiException e) {
//        	e.printStackTrace();
//        	log.info("----- submitAnalysisJob 文件分析异常:: "+e.getMessage());
//            throw new RuntimeException(e);
//        }
//	}
//
//	private static String submitTranscodeJob(OSSFileDO inputFile, String templateId,String prefix) {
//		log.info("----- 提交媒体文件转码开始----");  //downloadKey
//	    //String outputOSSObjectKey = "voice/transcode/"+prefix+".mp4";
//		String outputOSSObjectKey = downloadKey+prefix+".mp4";
//        JSONObject jobConfig = new JSONObject();
//        jobConfig.put("OutputObject", outputOSSObjectKey);
//        jobConfig.put("TemplateId", templateId);
//        JSONArray jobConfigArray = new JSONArray();
//        jobConfigArray.add(jobConfig);
//
//        SubmitJobsRequest request = new SubmitJobsRequest();
//        request.setInput(inputFile.toJsonString());
//        request.setOutputBucket(outputBucket);
//        request.setOutputs(jobConfigArray.toJSONString());
//        request.setPipelineId(pipelineId);
//
//        Integer outputJobCount = 1;
//
//        try {
//            SubmitJobsResponse response = aliyunClient.execute(request);
//            if(!response.isSuccess()) {
//                log.info("----- 提交媒体文件转码错误，response failed----");
//                throw new RuntimeException("SubmitJobsRequest failed");
//        	}
//            if(response.getJobResultList().size() != outputJobCount) {
//                log.info("----- 提交媒体文件转码错误，response size failed----");
//                throw new RuntimeException("SubmitJobsRequest Size failed");
//        	}
//            //媒体文件转码job提交
//            return response.getJobResultList().get(0).getJob().getJobId();
//        } catch (Exception e) {
//        	e.printStackTrace();
//        	log.info("----- submitTranscodeJob 媒体文件转码异常:: "+e.getMessage());
//            throw new RuntimeException(e);
//        }
//	}
//
//	private static Job waitTranscodeJobComplete(String transcodeJobId) {
//        QueryJobListRequest request = new QueryJobListRequest();
//        request.setJobIds(transcodeJobId);
//        Integer count = 0;
//
//        try {
//          while (true) {
//                QueryJobListResponse response = aliyunClient.execute(request);
//                if(!response.isSuccess()) {
//                    throw new RuntimeException("QueryJobListRequest failed");
//            	}
//
//                Job transcodeJob = response.getJobList().get(0);
//                String status = transcodeJob.getState();
//
//                if ("TranscodeFail".equals(status)) {
//                    throw new RuntimeException("transcodeJob state Failed");
//                }
//
//                if ("TranscodeSuccess".equals(status)) {
//                	System.out.println("transcode count: "+count);
//                    return transcodeJob;
//                }
//                count++;
//
//                //return transcodeJob;
//                //Thread.sleep(100);
//            }
//        } catch (Exception e) {
//        	e.printStackTrace();
//        	log.info("----- waitTranscodeJob 异常:: "+e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//
//	public static void downloadMedia(String targetFile,String prefix){
//		log.info("----- 文件下载开始------");
//		ObjectMetadata om = null;
//		BufferedInputStream bis = null;
//		FileOutputStream fos = null;
//		BufferedOutputStream bos = null;
//		 try{
//			 String key = downloadKey+prefix+".mp4"; // voice/transcode/1462843196805.mp4
//			 log.info("------文件："+key+" 正在下载-----");
//			 System.out.println("mediaDownloadFile key : "+key);
//			 OSSClient ossClient = new OSSClient(ossEndPoint, accessKeyId, accessKeySecret);
//			 OSSObject ossObject = ossClient.getObject(inputBucket,key);
//
//			// 读Object内容
//			System.out.println("Object content:");
//			bis = new BufferedInputStream(ossObject.getObjectContent());
//			fos = new FileOutputStream(new File(targetFile));
//			bos = new BufferedOutputStream(fos);
//			int len = 0;
//			byte[] by = new byte[2048];
//			while((len=bis.read(by)) != -1){
//				bos.write(by, 0, len);
//			}
//			System.out.println("downloadMediaFile end");
//			log.info("-------下载完成，文件名称："+targetFile);
//			// 关闭client
//			//ossClient.shutdown();
//		 } catch (Exception e) {
//			 e.printStackTrace();
//			 log.info("-------下载完成到本地出现异常："+e.getMessage());
//            System.out.println("=======exception message: ======= "+e.getMessage());
//         }finally{
//        	 try {
//				bis.close();
//	        	fos.close();
//	        	bos.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//         }
//		// 关闭client
//		//ossClient.shutdown();
//	}
//
//	public static void mediaFileTranscode(String localVideoFile,String targetFile,String fileName){
//		String mediaObj = "voice/"+fileName;
//		System.out.println("====mediaFileTranscode  mediaObj: "+mediaObj);
//		String prefix = fileName.substring(0,fileName.indexOf("."));
//		OSSFileDO inputFile = uploadFile(localVideoFile,mediaObj);
//    	//媒体转码
//    	String outputFile = systemTemplateJobFlow(inputFile,prefix);
//    	// MediaInfoJob 获取媒体信息作业。
////    	String timestamp = Long.toString(System.currentTimeMillis()/1000);
////    	System.out.println("timestamp: "+timestamp);
////
//    	downloadMedia(targetFile,prefix);
//	}
//
//	public void deleteSourceFile(String sourceFile) {
//		File file = new File(sourceFile);
//		if(file.exists() && file.isFile()){
//			file.delete();
//		}
//	}
//
//	public static void main(String[] args) {
//    	long startTime = System.currentTimeMillis();
//    	String sourceFile = "E:/wxservice/src/web/medias/16050610/testVoice.amr";
//    	String targetFile = "E:/wxservice/src/web/medias/16050610/testVoice.mp4";
//    	String fileName = "testVoice.amr";  // 1462843196805
//    	mediaFileTranscode(sourceFile,targetFile,fileName);
//    	long endTime = System.currentTimeMillis();
//    	System.out.println("take time in total: "+String.valueOf(endTime-startTime));
//	}
//
//}
package com.minlia.modules.sms.starter;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import org.junit.Test;

public class SMSAliyun   {

    private static final String aliyun_sms_region_id = "cn-hangzhou";
	private static final String aliyun_sms_access_key_id = "LTAIc3r9adaIXwpH";
	private static final String aliyun_sms_access_key_secret = "i86XebUFjiDY7cDQVCFFpPQBOUTcZt";
	private static final String aliyun_sms_end_point_name = "cn-hangzhou";
	private static final String aliyun_sms_product = "Sms";
	private static final String aliyun_sms_domain = "sms.aliyuncs.com";
	public final static String SIGN_NAME_PUXIN = "SIGN_NAME_PUXIN"; //集团短信签名
	public final static String TEMPLATE_CODE_OF_VALIDATE = "TEMPLATE_CODE_OF_VALIDATE"; //验证短信模板CODE
	public final static String TEMPLATE_CODE_OF_INFORM = "TEMPLATE_CODE_OF_INFORM"; //通知短信模板CODE
	public final static String TEMPLATE_CODE_OF_PROMOTION = "TEMPLATE_CODE_OF_PROMOTION";//推广短信模板CODE

	@Test
	public void send(){
		String phone="18566297716";
		String content="<天上有个林妹妹>";

		this.sendSMS(phone,content);
	}

	public boolean sendSMS(String phone, String content) {
		try {
		IClientProfile profile = DefaultProfile.getProfile(aliyun_sms_region_id, aliyun_sms_access_key_id, aliyun_sms_access_key_secret);
        DefaultProfile.addEndpoint(aliyun_sms_end_point_name, aliyun_sms_region_id, aliyun_sms_product,aliyun_sms_domain);
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendSmsRequest request = new SingleSendSmsRequest();
        request.setSignName("测试");
        request.setTemplateCode("SMS_56155265");
        request.setParamString("{\"name\":\""+content+"\"}");
        request.setRecNum(phone);
        SingleSendSmsResponse response = client.getAcsResponse(request);//短信发送
        return true;
		} catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	public boolean sendVerifyCode(String phone,String verifyCode){
		return this.sendSMS(phone, verifyCode);
	}
}
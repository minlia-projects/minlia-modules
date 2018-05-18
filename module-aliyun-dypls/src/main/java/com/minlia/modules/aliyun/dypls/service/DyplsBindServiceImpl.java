package com.minlia.modules.aliyun.dypls.service;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dyplsapi.model.v20170525.*;
import com.aliyuncs.exceptions.ClientException;
import com.minlia.modules.aliyun.dypls.body.BindAxnRequestBody;
import com.minlia.modules.aliyun.dypls.config.DyplsConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by garen on 2018/5/18.
 */
@Service
public class DyplsBindServiceImpl implements DyplsBindService {

    @Autowired
    private IAcsClient acsClient;

    @Autowired
    private DyplsConfig dyplsConfig;



    @Override
    public BindAxbResponse bindAxb(BindAxbRequest request) throws ClientException {
        //设置超时时间-可自行调整
//        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //必填:对应的号池Key
        request.setPoolKey("FC12345678");
        //必填:AXB关系中的A号码
        request.setPhoneNoA("15010101010");
        //必填:AXB关系中的B号码
        request.setPhoneNoB("15020202020");
        //必填:绑定关系对应的失效时间-不能早于当前系统时间
        request.setExpiration("2017-09-08 17:00:00");
        //可选:是否需要录制音频-默认是false
//        request.setIsRecordingEnabled(false);
        //外部业务自定义ID属性
        request.setOutId("yourOutId");



        //hint 此处可能会抛出异常，注意catch
        BindAxbResponse response = acsClient.getAcsResponse(request);
        return response;
    }

    @Override
    public BindAxnResponse bindAxn(BindAxnRequestBody requestBody) throws ClientException {
//        //设置超时时间-可自行调整
//        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//        //初始化ascClient需要的几个参数
//        final String product = "Dyplsapi";//隐私号码产品名称（产品名称固定，无需修改）
//        final String domain = "dyplsapi.aliyuncs.com";//隐私号码产品域名（产品域名固定，无需修改）
//        //替换成你的AK
//        final String accessKeyId = "LTAI7w8hY3vG8RNb";//你的accessKeyId,参考本文档步骤2
//        final String accessKeySecret = "4081VLQEFTS9Zo0RoTbJxMQypPuI87";//你的accessKeySecret，参考本文档步骤2
//        //初始化ascClient,暂时不支持多region
//        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
//                accessKeySecret);
//        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
//        IAcsClient acsClient = new DefaultAcsClient(profile);
//
//        //AXN绑定请求结构体-参数说明详见参数说明
//        BindAxnRequest request = new BindAxnRequest();
//        //必填:号池Key-详见概览页面的号池管
//        request.setPoolKey("FC100000032206036");
//        //必填:AXN关系中的A号码
////        request.setPhoneNoA("13025401006");
//        request.setPhoneNoA("18566297716");
//        //可选:AXN中A拨打X的时候转接到的默认的B号码,如果不需要则不设置
////        request.setPhoneNoB("15020202020");
//        //可选:指定X号码进行选号
//        //request.setPhoneNoX("1700000000");
//        //可选:期望分配X号码归属的地市(省去地市后缀后的城市名称)
//        //request.setExpectCity("北京");
//        //必填:绑定关系对应的失效时间-不能早于当前系统时间
//        request.setExpiration("2018-06-17 17:00:00");
//        //可选:是否需要录制音频-默认是false
//        request.setIsRecordingEnabled(false);
//        //外部业务自定义ID属性
//        request.setOutId("yourOutId");
//        //hint 此处可能会抛出异常，注意catch
//        BindAxnResponse response = acsClient.getAcsResponse(request);
//        if(response.getCode() != null && response.getCode().equals("OK")) {
//            //请求成功
//        }
//        return response;

        BindAxnRequest request = new BindAxnRequest();
        BeanUtils.copyProperties(requestBody,request);
        if (StringUtils.isEmpty(request.getPoolKey())) {
            request.setPoolKey(dyplsConfig.getPoolKey());
        }
        request.setExpiration(DateFormatUtils.format(requestBody.getExpireTime(),"yyyy-MM-dd HH:mm:ss"));
        BindAxnResponse response = acsClient.getAcsResponse(request);
        return response;
    }

    @Override
    public BindAxnExtensionResponse bindAxnExtension(BindAxnExtensionRequest request) throws ClientException {
        //必填:对应的号池Key
        request.setPoolKey("FC12345678");
        //必填:AXN关系中的A号码
        request.setPhoneNoA("15010101010");
        //可选:分机号，如果不填，自动分配1-3位
        request.setExtension("103");
        //可选:A拨打X时回拨到默认的B号码
        request.setPhoneNoB("13507950000");
        //必填:绑定关系对应的失效时间-不能早于当前系统时间
        request.setExpiration("2017-09-08 17:00:00");
        //可选:是否需要录制音频-默认是false
        request.setIsRecordingEnabled(false);
        //外部业务自定义ID属性
        request.setOutId("yourOutId");
        //hint 此处可能会抛出异常，注意catch
        BindAxnExtensionResponse response = acsClient.getAcsResponse(request);

        return response;
    }

    @Override
    public UpdateSubscriptionResponse updateSubscription(UpdateSubscriptionRequest request) throws ClientException {
        //绑定关系对应的号池Key
        request.setPoolKey("FC10003");
        //必填:绑定关系ID
        request.setSubsId("91996");
        //必填:绑定关系对应的X号码
        request.setPhoneNoX("17100000000");
        //必填:操作类型指令支持,支持的操作类型详见文档
        request.setOperateType("updateNoB");
        //可选:需要修改的B号码
        request.setPhoneNoB("17030000000");
        //hint 此处可能会抛出异常，注意catch
        UpdateSubscriptionResponse response = acsClient.getAcsResponse(request);
        return response;
    }

    /**
     * 订购关系解绑示例(解绑接口不区分AXB、AXN)
     * @return
     * @throws ClientException
     */
    @Override
    public UnbindSubscriptionResponse unbind(String subsId, String secretNo) throws ClientException {
        //组装请求对象
        UnbindSubscriptionRequest request = new UnbindSubscriptionRequest();
        //必填:对应的号池Key
        request.setPoolKey(dyplsConfig.getPoolKey());
        //必填-分配的X小号-对应到绑定接口中返回的secretNo;
        request.setSecretNo(secretNo);
        //可选-绑定关系对应的ID-对应到绑定接口中返回的subsId;
        request.setSubsId(subsId);

        UnbindSubscriptionResponse response = acsClient.getAcsResponse(request);

        return response;
    }

}

package com.minlia.module.aliyun.dypls.service;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dyplsapi.model.v20170525.*;
import com.aliyuncs.exceptions.ClientException;
import com.minlia.cloud.body.Response;
import com.minlia.module.aliyun.dypls.body.BindAxnRequestBody;
import com.minlia.module.aliyun.dypls.config.DyplsConfig;
import com.minlia.module.aliyun.dypls.entity.DyplsBind;
import com.minlia.module.aliyun.dypls.event.DyplsBindEvent;
import com.minlia.module.aliyun.dypls.event.DyplsUnbindEvent;
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
    public Response bindAxn(BindAxnRequestBody requestBody) {
        BindAxnRequest request = new BindAxnRequest();
        BeanUtils.copyProperties(requestBody,request);
        if (StringUtils.isEmpty(request.getPoolKey())) {
            request.setPoolKey(dyplsConfig.getPoolKey());
        }
        request.setExpiration(DateFormatUtils.format(requestBody.getExpireTime(),"yyyy-MM-dd HH:mm:ss"));
        BindAxnResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            return Response.failure("AXN绑定失败", e.getErrMsg());
        }

        if(response.getCode() != null && response.getCode().equals("OK")) {
            DyplsBind dyplsBind = new DyplsBind();
            BeanUtils.copyProperties(request,dyplsBind);
            dyplsBind.setSubsId(response.getSecretBindDTO().getSubsId());
            dyplsBind.setSecretNo(response.getSecretBindDTO().getSecretNo());
            dyplsBind.setExpireTime(requestBody.getExpireTime());
            DyplsBindEvent.onBind(dyplsBind);
            return Response.success(response.getMessage(), response.getSecretBindDTO());
        } else {
            return Response.failure(response.getMessage());
        }
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
    public Response unbind(String subsId, String secretNo) {
        //组装请求对象
        UnbindSubscriptionRequest request = new UnbindSubscriptionRequest();
        //必填:对应的号池Key
        request.setPoolKey(dyplsConfig.getPoolKey());
        //必填-分配的X小号-对应到绑定接口中返回的secretNo;
        request.setSecretNo(secretNo);
        //可选-绑定关系对应的ID-对应到绑定接口中返回的subsId;
        request.setSubsId(subsId);

        UnbindSubscriptionResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            return Response.failure("Unbind绑定失败", e.getErrMsg());
        }
        if(response.getCode() != null && response.getCode().equals("OK")) {
            DyplsUnbindEvent.unbind(secretNo);
            return Response.success(response.getMessage());
        } else {
            return Response.failure(response.getMessage());
        }
    }

}

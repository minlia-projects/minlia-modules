package com.minlia.module.aliyun.dypls.service;

import com.aliyuncs.dyplsapi.model.v20170525.*;
import com.aliyuncs.exceptions.ClientException;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.module.aliyun.dypls.body.BindAxnRequestBody;

/**
 * Created by garen on 2018/5/18.
 */
public interface DyplsBindService {

    BindAxbResponse bindAxb(BindAxbRequest request) throws ClientException;

    StatefulBody bindAxn(BindAxnRequestBody body);

    BindAxnExtensionResponse bindAxnExtension(BindAxnExtensionRequest request) throws ClientException;

    UpdateSubscriptionResponse updateSubscription(UpdateSubscriptionRequest request) throws ClientException;

    StatefulBody unbind(String subsId, String secretNo);

}

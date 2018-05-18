package com.minlia.modules.aliyun.dypls.service;

import com.aliyuncs.dyplsapi.model.v20170525.*;
import com.aliyuncs.exceptions.ClientException;
import com.minlia.modules.aliyun.dypls.body.BindAxnRequestBody;

/**
 * Created by garen on 2018/5/18.
 */
public interface DyplsBindService {

    BindAxbResponse bindAxb(BindAxbRequest request) throws ClientException;

    BindAxnResponse bindAxn(BindAxnRequestBody body) throws ClientException;

    BindAxnExtensionResponse bindAxnExtension(BindAxnExtensionRequest request) throws ClientException;

    UpdateSubscriptionResponse updateSubscription(UpdateSubscriptionRequest request) throws ClientException;

    UnbindSubscriptionResponse unbind(String subsId, String secretNo) throws ClientException;

}

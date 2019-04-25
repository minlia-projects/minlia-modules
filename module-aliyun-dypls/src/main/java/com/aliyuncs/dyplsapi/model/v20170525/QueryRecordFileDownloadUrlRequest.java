/*
 * Licensed ro the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * ro you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed ro in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.aliyuncs.dyplsapi.model.v20170525;

import com.aliyuncs.RpcAcsRequest;
import com.aliyuncs.dyplsapi.model.v20170525.QueryRecordFileDownloadUrlResponse;

/**
 * @author auto create
 * @version 
 */
public class QueryRecordFileDownloadUrlRequest extends RpcAcsRequest<com.aliyuncs.dyplsapi.model.v20170525.QueryRecordFileDownloadUrlResponse> {

	public QueryRecordFileDownloadUrlRequest() {
		super("Dyplsapi", "2017-05-25", "QueryRecordFileDownloadUrl");
	}

	private String resourceOwnerAccount;

	private String callId;

	private String callTime;

	private Long resourceOwnerId;

	private String poolKey;

	private Long ownerId;

	private String productType;

	public String getResourceOwnerAccount() {
		return this.resourceOwnerAccount;
	}

	public void setResourceOwnerAccount(String resourceOwnerAccount) {
		this.resourceOwnerAccount = resourceOwnerAccount;
		if(resourceOwnerAccount != null){
			putQueryParameter("ResourceOwnerAccount", resourceOwnerAccount);
		}
	}

	public String getCallId() {
		return this.callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
		if(callId != null){
			putQueryParameter("CallId", callId);
		}
	}

	public String getCallTime() {
		return this.callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
		if(callTime != null){
			putQueryParameter("CallTime", callTime);
		}
	}

	public Long getResourceOwnerId() {
		return this.resourceOwnerId;
	}

	public void setResourceOwnerId(Long resourceOwnerId) {
		this.resourceOwnerId = resourceOwnerId;
		if(resourceOwnerId != null){
			putQueryParameter("ResourceOwnerId", resourceOwnerId.toString());
		}
	}

	public String getPoolKey() {
		return this.poolKey;
	}

	public void setPoolKey(String poolKey) {
		this.poolKey = poolKey;
		if(poolKey != null){
			putQueryParameter("PoolKey", poolKey);
		}
	}

	public Long getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
		if(ownerId != null){
			putQueryParameter("OwnerId", ownerId.toString());
		}
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
		if(productType != null){
			putQueryParameter("ProductType", productType);
		}
	}

	@Override
	public Class<com.aliyuncs.dyplsapi.model.v20170525.QueryRecordFileDownloadUrlResponse> getResponseClass() {
		return QueryRecordFileDownloadUrlResponse.class;
	}

}

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
package com.aliyuncs.dyplsapi.transform.v20170525;

import com.aliyuncs.dyplsapi.model.v20170525.QueryRecordFileDownloadUrlResponse;
import com.aliyuncs.transform.UnmarshallerContext;


public class QueryRecordFileDownloadUrlResponseUnmarshaller {

	public static QueryRecordFileDownloadUrlResponse unmarshall(QueryRecordFileDownloadUrlResponse queryRecordFileDownloadUrlResponse, UnmarshallerContext context) {
		
		queryRecordFileDownloadUrlResponse.setRequestId(context.stringValue("QueryRecordFileDownloadUrlResponse.RequestId"));
		queryRecordFileDownloadUrlResponse.setCode(context.stringValue("QueryRecordFileDownloadUrlResponse.Code"));
		queryRecordFileDownloadUrlResponse.setMessage(context.stringValue("QueryRecordFileDownloadUrlResponse.Message"));
		queryRecordFileDownloadUrlResponse.setDownloadUrl(context.stringValue("QueryRecordFileDownloadUrlResponse.DownloadUrl"));
	 
	 	return queryRecordFileDownloadUrlResponse;
	}
}
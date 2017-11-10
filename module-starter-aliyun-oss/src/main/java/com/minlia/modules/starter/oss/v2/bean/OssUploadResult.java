package com.minlia.modules.starter.oss.v2.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jun
 *
 * OSS上传结果
 */
public class OssUploadResult {

	private boolean success;
	private List<OssFile> successFiles = new ArrayList<OssFile>();
	private List<OssFile> failureFiles = new ArrayList<OssFile>();

	/**
	 * OSS上传结果
	 * 
	 * @param success 是否成功
	 * @param ossFile OSS文件
	 */
	public OssUploadResult(boolean success, OssFile ossFile) {
		this.success = success;
		if (this.success) {
			this.successFiles.add(ossFile);
		} else {
			this.failureFiles.add(ossFile);
		}
	}

	/**
	 * OSS上传结果
	 * 
	 * @param success 是否成功
	 * @param ossFiles OSS文件
	 */
	public OssUploadResult(boolean success, List<OssFile> ossFiles) {
		this.success = success;
		if (this.success) {
			this.successFiles.addAll(ossFiles);
		} else {
			this.failureFiles.addAll(ossFiles);
		}
	}

	/**
	 * OSS上传结果
	 * 
	 * @param successFiles 成功的文件
	 * @param failureFiles 失败的文件
	 */
	public OssUploadResult(List<OssFile> successFiles, List<OssFile> failureFiles) {
		if (successFiles != null) {
			this.successFiles.addAll(successFiles);
		}
		if (failureFiles != null) {
			this.failureFiles.addAll(failureFiles);
		}

		this.success = !successFiles.isEmpty() && failureFiles.isEmpty();
	}

	public boolean isSuccess() {
		return success;
	}

	public List<OssFile> getSuccessFiles() {
		return successFiles;
	}

	public List<OssFile> getFailureFiles() {
		return failureFiles;
	}

}

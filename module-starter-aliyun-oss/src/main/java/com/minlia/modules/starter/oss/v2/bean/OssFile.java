package com.minlia.modules.starter.oss.v2.bean;

/**
 * @author Jun
 *
 * OSS 文件
 */
public class OssFile {

	private String url;

	private String eTag;

	private long size;

	private String name;

	private String originalName;

	private String contentType;

	private String errorCode;

	private String errorMessage;

	/**
	 * 上传成功的文件
	 * 
	 * @param eTag
	 */
	public OssFile(String eTag) {
		this.eTag = eTag;
	}

	/**
	 * 上传失败的文件
	 * 
	 * @param errorCode 错误代码
	 * @param errorMessage 错误消息
	 */
	public OssFile(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String geteTag() {
		return eTag;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}

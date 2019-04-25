package com.minlia.modules.aliyun.oss.bean;

import lombok.Data;

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

	public OssFile(String eTag) {
		this.eTag = eTag;
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

}

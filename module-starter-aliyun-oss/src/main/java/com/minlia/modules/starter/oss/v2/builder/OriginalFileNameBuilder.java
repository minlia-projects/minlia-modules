package com.minlia.modules.starter.oss.v2.builder;

/**
 * @author Jun
 *
 * 保持原文件名
 */
public class OriginalFileNameBuilder implements FileNameBuilder {

	/* (non-Javadoc)
	 * @see com.belerweb.oss4springmvc.builder.FileNameBuilder#build(java.lang.String)
	 */
	@Override
	public String build(String fileName) {
		return fileName;
	}

}

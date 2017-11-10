package com.minlia.modules.starter.oss.v2.builder;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jun
 *
 * 按上传日期(例如:2012-09-06)归档/储存文件
 */
public class DatePathBuilder implements PathBuilder {

	private static final String DEFAULT_PATTERN = "yyyy-MM-dd";

	private SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_PATTERN);

	/* (non-Javadoc)
	 * @see com.belerweb.oss4springmvc.PathBuilder#buildPath()
	 */
	@Override
	public String build() {
		return dateFormat.format(new Date()) + Constant.SLASH;
	}

	public void setPattern(String pattern) {
		if (pattern != null) {
			this.dateFormat = new SimpleDateFormat(pattern);
		}
	}

}

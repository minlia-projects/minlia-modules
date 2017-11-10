package com.minlia.modules.starter.oss.v2.builder;


import java.util.UUID;

/**
 * @author Jun
 *
 * 保持原文件名
 */
public class UuidFileNameBuilder implements FileNameBuilder {

	/* (non-Javadoc)
	 * @see com.belerweb.oss4springmvc.builder.FileNameBuilder#build(java.lang.String)
	 */
	@Override
	public String build(String fileName) {

		String suffix = Constant.EMPTY;  //设置一个常量值

		//Constant.DOT:"."     contains()方法返回true,当且仅当此字符串包含指定的char值序列

		//如果文件名有"."的话，进入 ，否则不进入
		if (fileName.contains(Constant.DOT)) {
			//lastIndexOf() 方法可返回一个指定的字符串值最后出现的位置，在一个字符串中的指定位置从后向前搜索。
			//剔除.jpg前的字符串
			suffix = fileName.substring(fileName.lastIndexOf(Constant.DOT));
		}
		return UUID.randomUUID().toString() + suffix;    //“wsedrftgyhnjmk.jpg”
	}

}

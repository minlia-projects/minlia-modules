package com.minlia.module.library.config;

import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigAutowired(type = "SYS_LIBRARY")
public class LibraryConfig {

    /**
     * 编号前缀
     */
    private String numberPrefix;

    /**
     * 编号填充位数
     */
    private Integer numberFullNum;

    /**
     * 编号序列
     */
    private String sequenceName;

}
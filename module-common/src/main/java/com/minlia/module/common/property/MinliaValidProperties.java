package com.minlia.module.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/5/21 3:19 PM
 */
@Data
@ConfigurationProperties(prefix = "system.valid.rule")
public class MinliaValidProperties {

    private String cellphone;

    private String email;

    private String username;

}

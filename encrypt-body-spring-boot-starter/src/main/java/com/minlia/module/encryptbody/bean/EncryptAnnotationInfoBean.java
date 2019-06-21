package com.minlia.module.encryptbody.bean;

import com.minlia.module.encryptbody.enums.EncryptBodyMethod;
import com.minlia.module.encryptbody.enums.SHAEncryptType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>加密注解信息</p>
 * @author licoy.cn
 * @version 2018/9/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncryptAnnotationInfoBean {

    private EncryptBodyMethod encryptBodyMethod;

    private String key;

    private SHAEncryptType shaEncryptType;

}

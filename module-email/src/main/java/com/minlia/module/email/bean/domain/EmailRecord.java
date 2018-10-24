package com.minlia.module.email.bean.domain;

import com.minlia.module.data.entity.WithIdEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 邮件
 * Created by garen on 2018/8/7.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRecord extends WithIdEntity {

    /**
     * 编号
     */
    private String number;

    /**
     * 主题
     */
    private String subject;

}

package com.minlia.module.todo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.todo.enumeration.TodoStatus;
import lombok.*;

import java.util.Date;

/**
 * Created by garen on 2018/4/27.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({})
@JsonIgnoreProperties(value = {})
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = true)
public class MyTodo extends AbstractEntity {

    /**
     * 编号
     */
    private String number;

    @JsonIgnore
    private String relationId;

    /**
     * 类型
     */
    private String type;

    /**
     * 大类
     */
    private String category;
//
//    /**
//     * 发起人
//     */
//    private String sponsor;

    /**
     * 处理人
     */
    private String handler;

    /**
     * 标题
     */
    private String title;

    /**
     * 状态
     */
    private TodoStatus status;

    /**
     * 内容
     */
    private String content;

    /**
     * 时间：待办时间、待处理时间
     */
    private Date time;

    /**
     * 过期时间
     */
    private Date expiryTime;

    /**
     * 备注
     */
    private String notes;

}

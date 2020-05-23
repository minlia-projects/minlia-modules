package com.minlia.module.todo.bean.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minlia.module.common.constant.LocalDateConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;


/**
 * Created by garen on 2018/4/27.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoCTO {

    @NotBlank
    private String relationId;

    @NotBlank
    private String type;

    @NotBlank
    private String handler;

    @NotBlank
    private String content;

    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime time;

    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime expiryTime;

}

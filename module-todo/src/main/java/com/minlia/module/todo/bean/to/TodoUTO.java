package com.minlia.module.todo.bean.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoUTO {

    private String number;

    @NotBlank
    private String content;

    private Date time;

    private Date expiryTime;


}

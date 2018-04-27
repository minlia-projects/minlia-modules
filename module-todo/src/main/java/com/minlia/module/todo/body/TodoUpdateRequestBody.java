package com.minlia.module.todo.body;

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
public class TodoUpdateRequestBody {

    private String number;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private Date time;

    private Date expiryTime;


}

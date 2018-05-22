package com.minlia.module.todo.body;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by garen on 2018/4/27.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoQueryRequestBody {

    private String relationId;

    private String number;

    private String type;

    private String status;

    private String handler;

}

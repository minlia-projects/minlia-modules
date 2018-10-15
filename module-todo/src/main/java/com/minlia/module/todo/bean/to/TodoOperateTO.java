package com.minlia.module.todo.bean.to;

import com.minlia.module.todo.enumeration.TodoOperate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoOperateTO {

    @NotBlank
    private String number;

    @NotNull
    private TodoOperate operate;

    @NotBlank
    @Size(max = 200)
    private String notes;

}

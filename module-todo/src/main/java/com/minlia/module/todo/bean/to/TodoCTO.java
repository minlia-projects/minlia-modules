package com.minlia.module.todo.bean.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

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

  private Date time;

  private Date expiryTime;

}

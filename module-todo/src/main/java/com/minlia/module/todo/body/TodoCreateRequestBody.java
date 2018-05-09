package com.minlia.module.todo.body;

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
public class TodoCreateRequestBody {

  @NotBlank
  private String relationId;

  @NotBlank
  private String type;

  @NotBlank
  private String handler;

  @NotBlank
  private String title;

  @NotBlank
  private String content;

  private Date time;

  private Date expiryTime;

}

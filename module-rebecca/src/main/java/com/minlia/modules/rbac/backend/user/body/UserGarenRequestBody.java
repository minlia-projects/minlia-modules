package com.minlia.modules.rbac.backend.user.body;

import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by garen on 2017/8/8.
 */
@ApiModel("用户-授权")
@Data
public class UserGarenRequestBody implements ApiRequestBody {

    @NotNull
    private String guid;

    @NotBlank
    private List<String> roles;

}
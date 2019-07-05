package com.minlia.modules.rebecca.bean.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RoleCTO {

    @NotBlank
    @Size(max = 30)
    private String code;

    @NotBlank
    @Size(max = 50)
    private String label;

    @Size(max = 255)
    private String notes;

    private List<Long> permissions;

}
package com.minlia.module.data.entity;

import com.minlia.module.data.bean.QueryRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * @author garen
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonAutoDetect(
//        fieldVisibility = JsonAutoDetect.Visibility.DEFAULT,
//        getterVisibility = JsonAutoDetect.Visibility.NONE,
//        setterVisibility = JsonAutoDetect.Visibility.NONE,
//        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
//        creatorVisibility = JsonAutoDetect.Visibility.NONE)
@Getter
@Setter
public abstract class BaseQueryEntity extends QueryRequest {

    private static final long serialVersionUID = 1L;

    @Min(1)
    private Long id;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 最后修改人
     */
    private Long lastModifiedBy;

    /**
     * 最后修改时间
     */
    private LocalDateTime lastModifiedDate;

}
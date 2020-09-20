package com.minlia.module.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * 审计字段不要手动赋值，与业务无关
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public abstract class WithDateEntity extends WithIdEntity {

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
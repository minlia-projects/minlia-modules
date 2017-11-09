package com.minlia.module.registry2.domain;

import com.minlia.cloud.entity.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.lang.reflect.Type;

/**
 * @author Henry Lin badcop@163.com
 */
@Data
@Entity
public class Registry2 extends AbstractEntity{

    private String packageName;
    private String className;
    private String propertyName;
    private Class type;
    private String stringValue;



}

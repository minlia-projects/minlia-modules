/*
 * Copyright © 2016 Minlia (http://oss.minlia.com/license/framework/2016)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.minlia.cloud.entity;

//import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.cloud.entity.listener.AbstractLocalizedEntityListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mybatis.annotations.DynamicSearch;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * AbstractEntity with audit
 * Created by William Raym on 06/21/17.
 * <p>
 * TODO 国际化
 * 以模型的属性名称为键值
 * <p>
 * entity.bible.name.id=xxxxx
 * <p>
 * 添加的时候已经取到当前系统的语言
 * <p>
 * 自动进行语言路由
 * <p>
 * 单条记录归属于当前语言
 * 由JPA提供查询append的功能, 动态插入当前locale到当前SQL中
 * <p>
 * 存入时由prePersist提供能力
 * 查询时自动封装 where locale ='en_US'的能力
 *
 * @since 1.0.0
 */
@JsonPropertyOrder({"locale"})
@MappedSuperclass
@Slf4j
@EntityListeners(AbstractLocalizedEntityListener.class)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.DEFAULT, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)@DynamicSearch

@org.springframework.data.mybatis.annotations.MappedSuperclass
public abstract class AbstractLocalizedEntity extends AbstractAuditingEntity {

//    @JsonIgnore
//    private String locale;
//
//    public String getLocale() {
//        return locale;
//    }
//
//    public void setLocale(String locale) {
//        this.locale = locale;
//    }
//
//    @Override
//    @Transient
//    @org.springframework.data.annotation.Transient
//    @JSONField(serialize = false)
//    public int hashCode() {
//        return 17 + (isEmpty() ? 0 : getId().hashCode() * 31);
//    }
//
//    /**
//     * 判断是否相等
//     *
//     * @param obj 对象
//     * @return 是否相等
//     */
//    @Override
//    @Transient
//    @org.springframework.data.annotation.Transient
//    @JSONField(serialize = false)
//    public boolean equals(Object obj) {
//        if (obj == this) {
//            return true;
//        }
//        if (isEmpty() || obj == null || !getClass().isAssignableFrom(obj.getClass())) {
//            return false;
//        }
//        AbstractLocalizedEntity entity = (AbstractLocalizedEntity) obj;
//        if (entity.isEmpty()) {
//            return false;
//        }
//        return getId().equals(entity.getId());
//    }
//
//
//    public String toString() {
//        return "AbstractLocalizedEntity (id=" + this.getId() + ")";
//    }

}

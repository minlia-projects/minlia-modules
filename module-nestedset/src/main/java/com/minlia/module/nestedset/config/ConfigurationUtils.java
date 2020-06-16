package com.minlia.module.nestedset.config;

/*-
 * #%L
 * minlia
 * %%
 * Copyright (C) 2005 - 2020 Minlia Team
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.google.common.collect.Maps;
import com.minlia.module.nestedset.annotation.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ResolvableType;

import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class ConfigurationUtils {

    public static Class<?> getType(Object type1, int index) {
//        Type type = type1.getClass().getGenericInterfaces()[0];
//        Type[] params = ((ParameterizedType) type).getActualTypeArguments();
//        Class<?> reponseClass = (Class) params[index];
//        return reponseClass;

        ResolvableType resolvableType = ResolvableType.forClass(type1.getClass());
        ResolvableType x = resolvableType.getGeneric(1);
        System.out.println(((ParameterizedType) x.getType()).getActualTypeArguments());
        Type type = type1.getClass().getGenericInterfaces()[1];
        Type[] params = ((ParameterizedType) type).getActualTypeArguments();
        Class<?> reponseClass = (Class) params[index];
        return (Class<?>) resolvableType.getGeneric().resolve();
    }

    private static Map<Class<?>, Configuration> configs = Maps.newHashMap();

    public static String getLeftFieldName(Class<?> nodeClass) {
        return getConfig(nodeClass).getLeftFieldName();
    }

    public static String getRightFieldName(Class<?> nodeClass) {
        return getConfig(nodeClass).getRightFieldName();
    }

    public static String getLevelFieldName(Class<?> nodeClass) {
        return getConfig(nodeClass).getLevelFieldName();
    }

    public static String getIdFieldName(Class<?> nodeClass) {
        return getConfig(nodeClass).getIdFieldName();
    }

    public static String getParentIdFieldName(Class<?> nodeClass) {
        return getConfig(nodeClass).getParentIdFieldName();
    }

    public static Configuration getConfig(Class<?> clazz) {
        if (!configs.containsKey(clazz)) {
            Configuration config = new Configuration();
            NestedSetModel entity = clazz.getAnnotation(NestedSetModel.class);
            config.setEntityName(clazz.getSimpleName());
            if (!StringUtils.isEmpty(entity.leftFieldName())) {
                config.setLeftFieldName(entity.leftFieldName());
            }
            if (!StringUtils.isEmpty(entity.rightFieldName())) {
                config.setRightFieldName(entity.rightFieldName());
            }
            if (!StringUtils.isEmpty(entity.levelFieldName())) {
                config.setLevelFieldName(entity.levelFieldName());
            }
            if (!StringUtils.isEmpty(entity.idFieldName())) {
                config.setIdFieldName(entity.idFieldName());
            }
            if (!StringUtils.isEmpty(entity.parentIdFieldName())) {
                config.setParentIdFieldName(entity.parentIdFieldName());
            }
            configs.put(clazz, config);
        }

        return configs.get(clazz);
    }


//    private static Configuration getConfig2(Class<?> clazz) {
//        if (!configs.containsKey(clazz)) {
//            Configuration config = new Configuration();
//
//            Entity entity = clazz.getAnnotation(Entity.class);
//            String name = entity.name();
//            config.setEntityName((name != null && name.length() > 0) ? name : clazz.getSimpleName());
//
//            for (Field field : clazz.getDeclaredFields()) {
//                if (field.getAnnotation(LeftColumn.class) != null) {
//                    config.setLeftFieldName(field.getName());
//                } else if (field.getAnnotation(RightColumn.class) != null) {
//                    config.setRightFieldName(field.getName());
//                } else if (field.getAnnotation(LevelColumn.class) != null) {
//                    config.setLevelFieldName(field.getName());
//                } else if (field.getAnnotation(IdColumn.class) != null) {
//                    config.setIdFieldName(field.getName());
//                } else if (field.getAnnotation(ParentIdColumn.class) != null) {
//                    config.setParentIdFieldName(field.getName());
//                }
//            }
//            configs.put(clazz, config);
//        }
//
//        return configs.get(clazz);
//    }
}

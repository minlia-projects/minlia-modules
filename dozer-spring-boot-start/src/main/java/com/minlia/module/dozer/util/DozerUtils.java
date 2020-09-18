package com.minlia.module.dozer.util;

import com.google.common.collect.Lists;
import com.minlia.cloud.holder.ContextHolder;
import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2020/8/21 15:05:27
 */
public class DozerUtils {

    public static <T> T map(Object source, Class<T> destinationClass) throws MappingException {
        return Mapper.INSTANCE.get().map(source, destinationClass);
    }

    public static void map(Object source, Object destination) throws MappingException {
        Mapper.INSTANCE.get().map(source, destination);
    }

    public static <T, S> List<T> map(List<S> sourceList, Class<T> destinationClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return null;
        }
        List<T> targetList = Lists.newArrayList();
        for (S s : sourceList) {
            targetList.add(Mapper.INSTANCE.get().map(s, destinationClass));
        }
        return targetList;
    }

    public enum Mapper {
        INSTANCE;

        public DozerBeanMapper get() {
            return ContextHolder.getContext().getBean(DozerBeanMapper.class);
        }
    }

}

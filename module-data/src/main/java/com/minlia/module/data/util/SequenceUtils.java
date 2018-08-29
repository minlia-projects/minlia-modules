package com.minlia.module.data.util;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.data.mapper.SequenceMapper;

/**
 * Created by garen on 2018/8/29.
 */
public class SequenceUtils {

    public static Long currval(String code) {
        return getMapper().currval(code);
    }

    public static Long nextval(String code) {
        return getMapper().nextval(code);
    }

    public static SequenceMapper getMapper() {
        return ContextHolder.getContext().getBean(SequenceMapper.class);
    }

}

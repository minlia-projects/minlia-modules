package com.minlia.module.data.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by garen on 2018/8/29.
 */
@Mapper
public interface SequenceMapper {

    @Select("select `currval`(#{code})")
    long currval(String code);

    @Select("select `nextval`(#{code})")
    long nextval(String code);

}

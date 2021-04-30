package com.minlia.module.data.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by garen on 2018/8/29.
 */
@Mapper
public interface SequenceMapper {

    @Select("select f_currval(#{code})")
    long currval(String code);

    @Select("select f_nextval(#{code})")
    long nextval(String code);

    @Select("SELECT fun_nextval_lpad(#{code}, #{length}, #{padStr}, #{prefix})")
    String nextvalWithLpad(String code, int length, String padStr, String prefix);

}
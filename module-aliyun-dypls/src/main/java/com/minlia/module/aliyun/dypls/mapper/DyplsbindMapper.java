package com.minlia.module.aliyun.dypls.mapper;

import com.minlia.module.aliyun.dypls.body.BindAxnQueryRequestBody;
import com.minlia.module.aliyun.dypls.entity.DyplsBind;

import java.util.List;

public interface DyplsbindMapper {

    int create(DyplsBind dyplsBind);

    int update(DyplsBind dyplsBind);

    int delete(Long id);

    int deleteBySecretNo(String subsId);

    DyplsBind queryById(Long id);

    List<DyplsBind> queryList(BindAxnQueryRequestBody body);

}
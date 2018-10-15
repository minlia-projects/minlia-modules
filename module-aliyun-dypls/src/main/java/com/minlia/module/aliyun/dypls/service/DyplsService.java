package com.minlia.module.aliyun.dypls.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.aliyun.dypls.bean.BindAxnQO;
import com.minlia.module.aliyun.dypls.entity.DyplsBind;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by garen on 2018/5/18.
 */
public interface DyplsService {

    DyplsBind create(DyplsBind dyplsBind);

    DyplsBind update(DyplsBind dyplsBind);

    int delete(Long id);

    void deleteBySecretNo(String secretNo);

    DyplsBind queryById(Long id);

    DyplsBind queryFirstByCellphone(String cellphone);

    List<DyplsBind> queryList(BindAxnQO qo);

    PageInfo<DyplsBind> queryPage(BindAxnQO qo, Pageable pageable);

}

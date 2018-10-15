package com.minlia.module.aliyun.dypls.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.module.aliyun.dypls.bean.BindAxnQO;
import com.minlia.module.aliyun.dypls.entity.DyplsBind;
import com.minlia.module.aliyun.dypls.mapper.DyplsbindMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by garen on 2018/5/18.
 */
@Service
public class DyplsServiceImpl implements DyplsService {

    @Autowired
    private DyplsbindMapper dyplsbindMapper;

    @Override
    public DyplsBind create(DyplsBind dyplsBind) {
        dyplsbindMapper.create(dyplsBind);
        return dyplsBind;
    }

    @Override
    public DyplsBind update(DyplsBind dyplsBind) {
        return null;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public void deleteBySecretNo(String secretNo) {
        dyplsbindMapper.deleteBySecretNo(secretNo);
    }

    @Override
    public DyplsBind queryById(Long id) {
        return dyplsbindMapper.queryById(id);
    }

    @Override
    public DyplsBind queryFirstByCellphone(String cellphone) {
        List<DyplsBind> dyplsBinds = this.queryList(BindAxnQO.builder().phoneNoA(cellphone).build());
        return dyplsBinds.get(0);
    }

    @Override
    public List<DyplsBind> queryList(BindAxnQO qo) {
        return dyplsbindMapper.queryList(qo);
    }

    @Override
    public PageInfo<DyplsBind> queryPage(BindAxnQO qo, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(),pageable.getPageSize()).doSelectPageInfo(()->dyplsbindMapper.queryList(qo));
    }

}

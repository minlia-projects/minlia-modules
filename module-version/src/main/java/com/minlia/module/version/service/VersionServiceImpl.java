package com.minlia.module.version.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.version.bean.entity.Version;
import com.minlia.module.version.bean.ro.VersionQRO;
import com.minlia.module.version.bean.ro.VersionCRO;
import com.minlia.module.version.bean.ro.VersionURO;
import com.minlia.module.version.mapper.VersionMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 2018/09/27.
 */
@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private VersionMapper versionMapper;

    @Override
    public Version create(VersionCRO cto) {
        Version version = mapper.map(cto, Version.class);
        if (null == cto.getTest()) {
            version.setTest(false);
        }
        versionMapper.create(version);
        return version;
    }

    @Override
    public Version update(VersionURO uto) {
        Version version = versionMapper.queryById(uto.getId());
        ApiAssert.notNull(version, SystemCode.Message.DATA_NOT_EXISTS);

        mapper.map(uto, version);
        versionMapper.update(version);
        return version;
    }

    @Override
    public void delete(Long id) {
        Version version = versionMapper.queryById(id);
        ApiAssert.notNull(version, SystemCode.Message.DATA_NOT_EXISTS);
        versionMapper.delete(id);
    }

    @Override
    public Version queryById(Long id) {
        return versionMapper.queryById(id);
    }

    @Override
    public long count(VersionQRO qo) {
        return versionMapper.count(qo);
    }

    @Override
    public Version one(VersionQRO qo) {
        return versionMapper.one(qo);
    }

    @Override
    public List<Version> list(VersionQRO qo) {
        return versionMapper.list(qo);
    }

    @Override
    public PageInfo<Version> page(VersionQRO qro, Pageable pageable) {
        return PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(()-> versionMapper.list(qro));
    }

}

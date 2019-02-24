package com.minlia.module.version.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.version.bean.domain.Version;
import com.minlia.module.version.bean.qo.VersionQO;
import com.minlia.module.version.bean.to.VersionCTO;
import com.minlia.module.version.bean.to.VersionUTO;
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
    public Version create(VersionCTO cto) {
        Version version = mapper.map(cto, Version.class);
        if (null == cto.getTest()) {
            version.setTest(false);
        }
        versionMapper.create(version);
        return version;
    }

    @Override
    public Version update(VersionUTO uto) {
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
    public long count(VersionQO qo) {
        return versionMapper.count(qo);
    }

    @Override
    public Version one(VersionQO qo) {
        return versionMapper.one(qo);
    }

    @Override
    public List<Version> list(VersionQO qo) {
        return versionMapper.list(qo);
    }

    @Override
    public PageInfo<Version> page(VersionQO qo, Pageable pageable) {
        return PageHelper.startPage(qo.getPageNumber(), qo.getPageSize()).doSelectPageInfo(()-> versionMapper.list(qo));
    }

}

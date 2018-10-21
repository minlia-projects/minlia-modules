package com.minlia.module.version.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.version.bean.domain.Version;
import com.minlia.module.version.bean.qo.VersionQO;
import com.minlia.module.version.bean.to.VersionCTO;
import com.minlia.module.version.bean.to.VersionUTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VersionService {

    Version create(VersionCTO cto);

    Version update(VersionUTO uto);

    void delete(Long id);

    Version queryById(Long id);

    long count(VersionQO qo);

    Version one(VersionQO qo);

    List<Version> list(VersionQO qo);

    PageInfo<Version> page(VersionQO qo, Pageable pageable);

}

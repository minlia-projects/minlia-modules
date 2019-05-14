package com.minlia.module.version.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.version.bean.entity.Version;
import com.minlia.module.version.bean.ro.VersionQRO;
import com.minlia.module.version.bean.ro.VersionCRO;
import com.minlia.module.version.bean.ro.VersionURO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VersionService {

    Version create(VersionCRO cto);

    Version update(VersionURO uto);

    void delete(Long id);

    Version queryById(Long id);

    long count(VersionQRO qo);

    Version one(VersionQRO qo);

    List<Version> list(VersionQRO qo);

    PageInfo<Version> page(VersionQRO qo, Pageable pageable);

}

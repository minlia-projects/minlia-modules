package com.minlia.module.version.mapper;

import com.minlia.module.version.bean.entity.Version;
import com.minlia.module.version.bean.ro.VersionQRO;

import java.util.List;

/**
 * Created by garen on 2018/10/16.
 */
public interface VersionMapper {

    long create(Version version);

    long update(Version version);

    long delete(Long id);

    long count(VersionQRO qo);

    Version queryById(Long id);

    Version one(VersionQRO qo);

    List<Version> list(VersionQRO qo);

}

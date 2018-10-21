package com.minlia.module.version.mapper;

import com.minlia.module.version.bean.domain.Version;
import com.minlia.module.version.bean.qo.VersionQO;

import java.util.List;

/**
 * Created by garen on 2018/10/16.
 */
public interface VersionMapper {

    long create(Version version);

    long update(Version version);

    long delete(Long id);

    Version queryById(Long id);

    long count(VersionQO qo);

    Version one(VersionQO qo);

    List<Version> list(VersionQO qo);

}

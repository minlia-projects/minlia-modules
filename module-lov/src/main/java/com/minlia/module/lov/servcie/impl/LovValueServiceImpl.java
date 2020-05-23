package com.minlia.module.lov.servcie.impl;

import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.lov.bean.LovValueQRO;
import com.minlia.module.lov.enntity.Lov;
import com.minlia.module.lov.enntity.LovValue;
import com.minlia.module.lov.mapper.LovValueMapper;
import com.minlia.module.lov.servcie.LovService;
import com.minlia.module.lov.servcie.LovValueService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LovValueServiceImpl implements LovValueService {

    @Autowired
    private LovService lovService;

    @Resource
    private LovValueMapper lovValueMapper;

    @Override
    public int insertSelective(LovValue record) {
        //判断lov_id是否存在
        Lov lov = lovService.selectByPrimaryKey(record.getLovId());
        ApiAssert.notNull(lov, SystemCode.Message.DATA_NOT_EXISTS);
        if (record.getParentId() != null) {
            ApiAssert.state(lovValueMapper.countByCode(record.getParentId()) == 1, SystemCode.Message.DATA_NOT_EXISTS);
        }
        record.setLovCode(lov.getCode());
        return lovValueMapper.insertSelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return lovValueMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Boolean disable(Long id) {
        LovValue lovValue = lovValueMapper.selectByPrimaryKey(id);
        lovValue.setDisFlag(!lovValue.getDelFlag());
        lovValueMapper.updateByPrimaryKeySelective(lovValue);
        return lovValue.getDisFlag();
    }

    @Override
    public Boolean delete(Long id) {
        LovValue lovValue = lovValueMapper.selectByPrimaryKey(id);
        lovValue.setDelFlag(true);
        lovValueMapper.updateByPrimaryKeySelective(lovValue);
        return lovValue.getDisFlag();
    }

    @Override
    public int updateByPrimaryKeySelective(LovValue record) {
        return lovValueMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public LovValue selectByPrimaryKey(Long id) {
        return lovValueMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<LovValue> selectByAll(LovValueQRO qro) {
//        if (StringUtils.isNotBlank(qro.getLovCode())) {
//            Lov lov = lovService.selectOneByCode(qro.getLovCode());
//            qro.setLovId(null != lov ? lov.getId() : -1);
//        }
        return lovValueMapper.selectByAll(qro);
    }

    @Override
    public LovValue selectOneByAll(LovValueQRO qro) {
//        if (StringUtils.isNotBlank(qro.getLovCode())) {
//            Lov lov = lovService.selectOneByCode(qro.getLovCode());
//            qro.setLovId(null != lov ? lov.getId() : -1);
//        }
        return lovValueMapper.selectOneByAll(qro);
    }

    @Override
    public LovValue selectOneByCodeAndLovCode(String lovCode, String code) {
        Lov lov = lovService.selectOneByCode(lovCode);
        return null != lov ? lovValueMapper.selectOneByCodeAndLovId(lov.getId(), code, LocaleContextHolder.getLocale().toString()) : null;
    }

    @Override
    public String selectNameByCodeAndLovCode(String lovCode, String code) {
        LovValue lovValue = this.selectOneByCodeAndLovCode(lovCode, code);
        return null != lovValue ? lovValue.getName() : null;
    }

    @Override
    public List<String> selectNameByAll(LovValueQRO qro) {
        return lovValueMapper.selectNameByAll(qro);
    }
}

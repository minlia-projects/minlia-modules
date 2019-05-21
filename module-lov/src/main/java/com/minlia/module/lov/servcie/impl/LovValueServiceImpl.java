package com.minlia.module.lov.servcie.impl;

import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.lov.bean.LovValueQRO;
import com.minlia.module.lov.enntity.Lov;
import com.minlia.module.lov.enntity.LovValue;
import com.minlia.module.lov.mapper.LovValueMapper;
import com.minlia.module.lov.servcie.LovService;
import com.minlia.module.lov.servcie.LovValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LovValueServiceImpl implements LovValueService{

    @Autowired
    private LovService lovService;

    @Resource
    private LovValueMapper lovValueMapper;

    @Override
    public int insertSelective(LovValue record) {
        //判断lov_id是否存在
        Lov lov = lovService.selectByPrimaryKey(record.getLovId());
        ApiAssert.notNull(lov, SystemCode.Message.DATA_NOT_EXISTS);
        ApiAssert.state(record.getParentId() != null && lovValueMapper.countById(record.getParentId()) == 1, SystemCode.Message.DATA_NOT_EXISTS);
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
    public int updateByPrimaryKeySelective(LovValue record) {
        return lovValueMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public LovValue selectByPrimaryKey(Long id) {
        return lovValueMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<LovValue> selectByAll(LovValueQRO qro) {
        return lovValueMapper.selectByAll(qro);
    }

}

package com.minlia.module.lov.servcie.impl;

import com.minlia.cloud.code.Code;
import com.minlia.cloud.code.SystemCode;
import com.minlia.module.lov.bean.LovQRO;
import com.minlia.module.lov.enntity.Lov;
import com.minlia.module.lov.mapper.LovMapper;
import com.minlia.module.lov.servcie.LovService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LovServiceImpl implements LovService {

    @Resource
    private LovMapper lovMapper;

    @Override
    public int insertSelective(Lov record) {
        return lovMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Lov record) {
        return lovMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return lovMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Boolean disable(Long id) {
        Lov lov = lovMapper.selectByPrimaryKey(id);
        lov.setDisFlag(!lov.getDelFlag());
        lovMapper.updateByPrimaryKeySelective(lov);
        return lov.getDisFlag();
    }

    @Override
    public Lov selectByPrimaryKey(Long id) {
        return lovMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Lov> selectByAll(LovQRO qro) {
        return lovMapper.selectByAll(qro);
    }

    @Override
    public Lov selectOneByCode(String code) {
        return lovMapper.selectOneByCode(code);
    }

    @Override
    public Code delete(Long id) {
        Lov lov = lovMapper.selectByPrimaryKey(id);
        lov.setDelFlag(true);
        lovMapper.updateByPrimaryKey(lov);
        return SystemCode.Message.DELETE_SUCCESS;
    }
}


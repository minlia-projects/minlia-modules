package com.minlia.module.bankcard.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.aliyun.market.config.AliyunMarketProperties;
import com.minlia.module.aliyun.market.dto.BankCardVerifyDTO;
import com.minlia.module.aliyun.market.ro.BankCardVerifyRO;
import com.minlia.module.aliyun.market.utils.AliyunMarketUtils;
import com.minlia.module.bankbranch.service.BankBranchService;
import com.minlia.module.bankcard.entity.BankCard;
import com.minlia.module.bankcard.mapper.BankCardMapper;
import com.minlia.module.bankcard.ro.BankCardCRO;
import com.minlia.module.bankcard.ro.BankCardQRO;
import com.minlia.module.bankcard.ro.BankCardURO;
import com.minlia.module.bankcard.service.BankCardService;
import com.minlia.module.bankcard.vo.BankCardVO;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import com.minlia.modules.security.code.SecurityCode;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 3/13/17.
 */
@Service
public class BankCardServiceImpl implements BankCardService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private BankCardMapper bankCardMapper;

    @Autowired
    private BankBranchService bankBranchService;

    @Autowired
    private AliyunMarketProperties aliyunMarketProperties;

    @Override
    public BankCard create(BankCardCRO cto) {
        long count = bankCardMapper.count(BankCardQRO.builder().guid(SecurityContextHolder.getCurrentGuid()).number(cto.getNumber()).build());
        ApiAssert.state(count == 0, SystemCode.Message.DATA_ALREADY_EXISTS);

        //判断联行号是否存在
        Boolean bankBranchExists = bankBranchService.exists(cto.getBankCode());
        ApiAssert.state(bankBranchExists, "联行号不存在");

        //银行二、三、四要素验证
        BankCardVerifyDTO verifyDTO = AliyunMarketUtils.verifyBankCard(aliyunMarketProperties.getBankcardVerifyLianzhuo(), mapper.map(cto, BankCardVerifyRO.class));
        ApiAssert.state(verifyDTO.isSuccess(), verifyDTO.getResp().getCode() + "", verifyDTO.getResp().getDesc());

        BankCard bankCard = mapper.map(cto, BankCard.class);
        long withdrawCount = bankCardMapper.count(BankCardQRO.builder().guid(SecurityContextHolder.getCurrentGuid()).isWithdraw(true).build());
        bankCard.setIsWithdraw(withdrawCount > 0 ? false : true);
        bankCardMapper.create(bankCard);
        return bankCard;
    }

    @Override
    public BankCard update(BankCardURO uto) {
        long count = bankCardMapper.count(BankCardQRO.builder().id(uto.getId()).guid(SecurityContextHolder.getCurrentGuid()).build());
        ApiAssert.state(count == 1, SystemCode.Message.DATA_NOT_EXISTS);

        //判断联行号是否存在 TODO
        BankCard bankCard = mapper.map(uto, BankCard.class);
        bankCardMapper.update(bankCard);
        return bankCard;
    }

    @Override
    public void delete(Long id) {
        BankCardVO bankCard = bankCardMapper.queryById(id);
        ApiAssert.notNull(bankCard, SystemCode.Message.DATA_NOT_EXISTS);
        ApiAssert.state(bankCard.getGuid().equals(SecurityContextHolder.getCurrentGuid()), SecurityCode.Message.NOT_DATA_AUTHORIZED);
        bankCardMapper.delete(id);
    }

    @Override
    public void setWithdrawCard(Long id) {
        String guid = SecurityContextHolder.getCurrentGuid();
        long count = bankCardMapper.count(BankCardQRO.builder().id(id).guid(guid).build());
        ApiAssert.state(count > 0, SystemCode.Message.DATA_NOT_EXISTS);
        bankCardMapper.setWithdraw(guid,id);
    }

    @Override
    public BankCardVO queryById(Long id) {
        return bankCardMapper.queryById(id);
    }

    @Override
    public long count(BankCardQRO qo) {
        return bankCardMapper.count(qo);
    }

    @Override
    public BankCardVO queryOne(BankCardQRO qo) {
        return bankCardMapper.queryOne(qo);
    }

    @Override
    public List<BankCardVO> queryList(BankCardQRO qo) {
        return bankCardMapper.queryList(qo);
    }

    @Override
    public PageInfo<BankCardVO> queryPage(BankCardQRO qo, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageSize(),pageable.getPageNumber()).doSelectPageInfo(()->bankCardMapper.queryList(qo));
    }

}

package com.minlia.module.wallet.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.aliyun.market.bean.dto.BankCardVerifyDTO;
import com.minlia.module.aliyun.market.bean.to.BankCardVerifyTO;
import com.minlia.module.aliyun.market.config.AliyunMarketProperties;
import com.minlia.module.aliyun.market.utils.AliyunMarketUtils;
import com.minlia.module.bank.service.BankBranchService;
import com.minlia.module.wallet.bean.domain.BankCardDo;
import com.minlia.module.wallet.bean.to.BankCardCTO;
import com.minlia.module.wallet.bean.to.BankCardQO;
import com.minlia.module.wallet.bean.to.BankCardUTO;
import com.minlia.module.wallet.bean.vo.BankCardVo;
import com.minlia.module.wallet.mapper.BankCardMapper;
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
    public BankCardDo create(BankCardCTO cto) {
        long count = bankCardMapper.count(BankCardQO.builder().guid(SecurityContextHolder.getCurrentGuid()).number(cto.getNumber()).build());
        ApiAssert.state(count == 0, SystemCode.Message.DATA_ALREADY_EXISTS);

        //判断联行号是否存在
        Boolean bankBranchExists = bankBranchService.exists(cto.getBankCode());
        ApiAssert.state(bankBranchExists, "联行号不存在");

        //银行二、三、四要素验证
        BankCardVerifyDTO verifyDTO = AliyunMarketUtils.verifyBankCard(aliyunMarketProperties.getBankcardVerifyLianzhuo(),mapper.map(cto,BankCardVerifyTO.class));
        ApiAssert.state(verifyDTO.isSuccess(), verifyDTO.getResp().getCode(), verifyDTO.getResp().getDesc());

        BankCardDo bankCard = mapper.map(cto,BankCardDo.class);
        bankCard.setGuid(SecurityContextHolder.getCurrentGuid());
        long withdrawCount = bankCardMapper.count(BankCardQO.builder().guid(SecurityContextHolder.getCurrentGuid()).isWithdraw(true).build());
        bankCard.setIsWithdraw(withdrawCount > 0 ? false : true);
        bankCardMapper.create(bankCard);
        return bankCard;
    }

    @Override
    public BankCardDo update(BankCardUTO uto) {
        long count = bankCardMapper.count(BankCardQO.builder().id(uto.getId()).guid(SecurityContextHolder.getCurrentGuid()).build());
        ApiAssert.state(count == 1, SystemCode.Message.DATA_NOT_EXISTS);

        //判断联行号是否存在 TODO
        BankCardDo bankCard = mapper.map(uto,BankCardDo.class);
        bankCardMapper.update(bankCard);
        return bankCard;
    }

    @Override
    public void delete(Long id) {
        BankCardVo bankCard = bankCardMapper.queryById(id);
        ApiAssert.notNull(bankCard, SystemCode.Message.DATA_NOT_EXISTS);
        ApiAssert.state(bankCard.getGuid().equals(SecurityContextHolder.getCurrentGuid()), SecurityCode.Message.NOT_DATA_AUTHORIZED);
        bankCardMapper.delete(id);
    }

    @Override
    public void setWithdrawCard(Long id) {
        String guid = SecurityContextHolder.getCurrentGuid();
        long count = bankCardMapper.count(BankCardQO.builder().id(id).guid(guid).build());
        ApiAssert.state(count > 0, SystemCode.Message.DATA_NOT_EXISTS);
        bankCardMapper.setWithdraw(guid,id);
    }

    @Override
    public BankCardVo queryById(Long id) {
        return bankCardMapper.queryById(id);
    }

    @Override
    public long count(BankCardQO qo) {
        return bankCardMapper.count(qo);
    }

    @Override
    public BankCardVo queryOne(BankCardQO qo) {
        return bankCardMapper.queryOne(qo);
    }

    @Override
    public List<BankCardVo> queryList(BankCardQO qo) {
        return bankCardMapper.queryList(qo);
    }

    @Override
    public PageInfo<BankCardVo> queryPage(BankCardQO qo, Pageable pageable) {
        return PageHelper.startPage(qo.getPageSize(),qo.getPageNumber()).doSelectPageInfo(()->bankCardMapper.queryList(qo));
    }

}

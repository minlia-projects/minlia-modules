package com.minlia.module.wallet.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.aliyun.market.bean.dto.BankCardVerifyDTO;
import com.minlia.module.aliyun.market.bean.to.BankCardVerifyTO;
import com.minlia.module.aliyun.market.config.AliyunMarketProperties;
import com.minlia.module.aliyun.market.utils.AliyunMarketUtils;
import com.minlia.module.bank.service.BankBranchService;
import com.minlia.module.wallet.bean.to.BankCardCTO;
import com.minlia.module.wallet.bean.to.BankCardQO;
import com.minlia.module.wallet.bean.to.BankCardUTO;
import com.minlia.module.wallet.bean.vo.BankCardVo;
import com.minlia.module.wallet.bean.domain.BankCardDo;
import com.minlia.module.wallet.mapper.BankCardMapper;
import com.minlia.modules.rbac.context.SecurityContextHolder;
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
        ApiPreconditions.is(count > 0, ApiCode.NOT_AUTHORIZED,"记录已存在");

        //判断联行号是否存在
        Boolean bankBranchExists = bankBranchService.exists(cto.getBankCode());
        ApiPreconditions.not(bankBranchExists,ApiCode.NOT_FOUND,"联行号不存在");

        //银行二、三、四要素验证
        BankCardVerifyDTO verifyDTO = AliyunMarketUtils.verifyBankCard(aliyunMarketProperties.getBankcardVerifyLianzhuo(),mapper.map(cto,BankCardVerifyTO.class));
        if (!verifyDTO.isSuccess()) {
            ApiPreconditions.is(true,ApiCode.BASED_ON,verifyDTO.getResp().getDesc());
        }

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
        ApiPreconditions.is(count == 0, ApiCode.NOT_FOUND,"记录不存在");

        //判断联行号是否存在 TODO

        BankCardDo bankCard = mapper.map(uto,BankCardDo.class);
        bankCardMapper.update(bankCard);
        return bankCard;
    }

    @Override
    public void delete(Long id) {
        BankCardVo bankCard = bankCardMapper.queryById(id);
        ApiPreconditions.is(null == bankCard,ApiCode.NOT_FOUND,"记录不存在");
        ApiPreconditions.not(bankCard.getGuid().equals(SecurityContextHolder.getCurrentGuid()),ApiCode.NOT_AUTHORIZED,"没有操作此记录的权限");
        bankCardMapper.delete(id);
    }

    @Override
    public void setWithdrawCard(Long id) {
        String guid = SecurityContextHolder.getCurrentGuid();
        long count = bankCardMapper.count(BankCardQO.builder().id(id).guid(guid).build());
        ApiPreconditions.is(count == 0, ApiCode.NOT_FOUND,"记录不存在");
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
        return PageHelper.startPage(pageable.getPageSize(),pageable.getPageNumber()).doSelectPageInfo(()->bankCardMapper.queryList(qo));
    }

}

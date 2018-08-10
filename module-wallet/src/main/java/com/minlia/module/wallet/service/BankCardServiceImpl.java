package com.minlia.module.wallet.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.wallet.domain.BankCardDo;
import com.minlia.module.wallet.dto.BankCardCreateDto;
import com.minlia.module.wallet.dto.BankCardQueryDto;
import com.minlia.module.wallet.dto.BankCardUpdateDto;
import com.minlia.module.wallet.mapper.BankCardMapper;
import com.minlia.module.wallet.vo.BankCardVo;
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

    @Override
    public BankCardDo create(BankCardCreateDto dto) {
        long count = bankCardMapper.count(BankCardQueryDto.builder().guid(SecurityContextHolder.getCurrentGuid()).number(dto.getNumber()).build());
        ApiPreconditions.is(count > 0, ApiCode.NOT_AUTHORIZED,"记录已存在");

        //判断联行号是否存在 TODO

        BankCardDo bankCard = mapper.map(dto,BankCardDo.class);
        bankCard.setGuid(SecurityContextHolder.getCurrentGuid());
        long withdrawCount = bankCardMapper.count(BankCardQueryDto.builder().guid(SecurityContextHolder.getCurrentGuid()).isWithdraw(true).build());
        bankCard.setIsWithdraw(withdrawCount > 0 ? false : true);
        bankCardMapper.create(bankCard);
        return bankCard;
    }

    @Override
    public BankCardDo update(BankCardUpdateDto dto) {
        long count = bankCardMapper.count(BankCardQueryDto.builder().id(dto.getId()).guid(SecurityContextHolder.getCurrentGuid()).build());
        ApiPreconditions.is(count == 0, ApiCode.NOT_FOUND,"记录不存在");

        //判断联行号是否存在 TODO

        BankCardDo bankCard = mapper.map(dto,BankCardDo.class);
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
        long count = bankCardMapper.count(BankCardQueryDto.builder().id(id).guid(guid).build());
        ApiPreconditions.is(count == 0, ApiCode.NOT_FOUND,"记录不存在");
        bankCardMapper.setWithdraw(guid,id);
    }

    @Override
    public BankCardVo queryById(Long id) {
        return bankCardMapper.queryById(id);
    }

    @Override
    public long count(BankCardQueryDto dto) {
        return bankCardMapper.count(dto);
    }

    @Override
    public BankCardVo queryOne(BankCardQueryDto dto) {
        return bankCardMapper.queryOne(dto);
    }

    @Override
    public List<BankCardVo> queryList(BankCardQueryDto dto) {
        return bankCardMapper.queryList(dto);
    }

    @Override
    public PageInfo<BankCardVo> queryPage(BankCardQueryDto dto, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageSize(),pageable.getPageNumber()).doSelectPageInfo(()->bankCardMapper.queryList(dto));
    }

}

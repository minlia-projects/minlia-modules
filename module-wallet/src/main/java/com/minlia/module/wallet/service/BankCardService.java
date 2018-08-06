package com.minlia.module.wallet.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.wallet.domain.BankCardDo;
import com.minlia.module.wallet.dto.BankCardCreateDto;
import com.minlia.module.wallet.dto.BankCardQueryDto;
import com.minlia.module.wallet.dto.BankCardUpdateDto;
import com.minlia.module.wallet.vo.BankCardVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankCardService {

    /**
     * 创建
     * @param createDto
     * @return
     */
    BankCardDo create(BankCardCreateDto createDto);

    /**
     * 修改
     * @param updateDto
     * @return
     */
    BankCardDo update(BankCardUpdateDto updateDto);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 设置提现卡号
     * @param id
     * @return
     */
    void setWithdrawCard(Long id);

    BankCardVo queryById(Long id);

    BankCardVo queryOne(BankCardQueryDto dto);

    List<BankCardVo> queryList(BankCardQueryDto dto);

    PageInfo<BankCardVo> queryPage(BankCardQueryDto dto, Pageable pageable);

}

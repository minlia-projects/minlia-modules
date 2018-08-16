package com.minlia.module.wallet.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.wallet.domain.BankCardDo;
import com.minlia.module.wallet.bean.to.BankCardCreateTo;
import com.minlia.module.wallet.bean.to.BankCardQueryTo;
import com.minlia.module.wallet.bean.to.BankCardUpdateTo;
import com.minlia.module.wallet.bean.vo.BankCardVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankCardService {

    /**
     * 创建
     * @param createDto
     * @return
     */
    BankCardDo create(BankCardCreateTo createDto);

    /**
     * 修改
     * @param updateDto
     * @return
     */
    BankCardDo update(BankCardUpdateTo updateDto);

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

    long count(BankCardQueryTo dto);

    BankCardVo queryOne(BankCardQueryTo dto);

    List<BankCardVo> queryList(BankCardQueryTo dto);

    PageInfo<BankCardVo> queryPage(BankCardQueryTo dto, Pageable pageable);

}

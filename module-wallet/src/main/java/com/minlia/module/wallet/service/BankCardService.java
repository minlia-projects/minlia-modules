package com.minlia.module.wallet.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.wallet.bean.domain.BankCardDo;
import com.minlia.module.wallet.bean.to.BankCardCTO;
import com.minlia.module.wallet.bean.to.BankCardQO;
import com.minlia.module.wallet.bean.to.BankCardUTO;
import com.minlia.module.wallet.bean.vo.BankCardVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankCardService {

    /**
     * 创建
     * @param cto
     * @return
     */
    BankCardDo create(BankCardCTO cto);

    /**
     * 修改
     * @param uto
     * @return
     */
    BankCardDo update(BankCardUTO uto);

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

    long count(BankCardQO dto);

    BankCardVo queryOne(BankCardQO qo);

    List<BankCardVo> queryList(BankCardQO qo);

    PageInfo<BankCardVo> queryPage(BankCardQO qo, Pageable pageable);

}

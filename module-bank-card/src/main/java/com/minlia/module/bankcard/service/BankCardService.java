package com.minlia.module.bankcard.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.bankcard.entity.BankCard;
import com.minlia.module.bankcard.ro.BankCardCRO;
import com.minlia.module.bankcard.ro.BankCardQRO;
import com.minlia.module.bankcard.ro.BankCardURO;
import com.minlia.module.bankcard.vo.BankCardVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankCardService {

    /**
     * 创建
     * @param cto
     * @return
     */
    BankCard create(BankCardCRO cto);

    /**
     * 修改
     * @param uto
     * @return
     */
    BankCard update(BankCardURO uto);

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

    BankCardVO queryById(Long id);

    long count(BankCardQRO dto);

    BankCardVO queryOne(BankCardQRO qo);

    List<BankCardVO> queryList(BankCardQRO qo);

    PageInfo<BankCardVO> queryPage(BankCardQRO qo, Pageable pageable);

}

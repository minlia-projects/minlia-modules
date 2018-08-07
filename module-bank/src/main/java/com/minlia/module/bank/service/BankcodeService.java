package com.minlia.module.bank.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.bank.domain.BankcodeDo;
import com.minlia.module.bank.dto.BankcodeQueryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankcodeService {

    void init(int page);

    /**
     * 创建
     * @param BankcodeDo
     * @return
     */
    BankcodeDo create(BankcodeDo BankcodeDo);

    /**
     * 修改
     * @param BankcodeDo
     * @return
     */
    BankcodeDo update(BankcodeDo BankcodeDo);

    /**
     * 删除
     * @param number
     */
    void delete(String number);

    BankcodeDo queryByNumber(String number);

    BankcodeDo queryOne(BankcodeQueryDto dto);

    List<BankcodeDo> queryList(BankcodeQueryDto dto);

    PageInfo<BankcodeDo> queryPage(BankcodeQueryDto dto, Pageable pageable);

}

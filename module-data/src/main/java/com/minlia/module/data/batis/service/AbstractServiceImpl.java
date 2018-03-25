package com.minlia.module.data.batis.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.minlia.module.data.batis.mapper.AbstractMapper;
import java.util.List;


/**
 * @author will
 */
public class AbstractServiceImpl<M extends AbstractMapper<T>, T> extends
    ServiceImpl<M, T> implements
    AbstractService<T> {

  @Override
  public Page<T> pageList(Page<T> page, Wrapper<T> wrapper) {
    List mapList = this.baseMapper.pageList(page, wrapper);
    page.setRecords(mapList);
    return page;
  }
}

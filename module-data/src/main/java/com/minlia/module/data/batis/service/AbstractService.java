package com.minlia.module.data.batis.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

public interface AbstractService<T> extends IService<T> {

  Page<T> pageList(Page<T> page, Wrapper<T> wrapper);

}

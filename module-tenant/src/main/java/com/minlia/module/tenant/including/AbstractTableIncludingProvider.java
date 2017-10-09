package com.minlia.module.tenant.including;

import com.google.common.collect.Lists;
import java.util.List;

/**
 * Created by will on 10/8/17.
 */
public class AbstractTableIncludingProvider implements TableIncludingProvider{

  protected List<String> including;

  //获取到允许隔离的表名集
  //在后面进行隔离的时候使用
  public List<String> getIncluding(){
    return Lists.newArrayList();
  }


}

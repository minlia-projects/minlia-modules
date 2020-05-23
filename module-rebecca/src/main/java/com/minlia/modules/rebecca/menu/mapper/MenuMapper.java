package com.minlia.modules.rebecca.menu.mapper;

import com.minlia.modules.rebecca.menu.bean.MenuQRO;
import com.minlia.modules.rebecca.menu.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    Long countById(@Param("id")Long id);

    Long countByParentId(@Param("parentId")Long parentId);

	int updateTypeById(@Param("updatedType")String updatedType,@Param("id")Long id);

    int clearByRoleId(Long roleId);

    int grant(Long roleId, List<Long> ids);

    List<Menu> selectByAll(MenuQRO qro);

    long countByAll(MenuQRO qro);

}
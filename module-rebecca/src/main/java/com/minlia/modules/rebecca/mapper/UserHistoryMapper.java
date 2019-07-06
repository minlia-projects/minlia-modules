package com.minlia.modules.rebecca.mapper;

import org.apache.ibatis.annotations.Param;
import com.minlia.modules.rebecca.enumeration.UserUpdateTypeEcnum;

import com.minlia.modules.rebecca.bean.domain.UserHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface UserHistoryMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(UserHistory record);

    UserHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserHistory record);

    List<UserHistory> selectByAll(UserHistory userHistory);

    long countByAll(UserHistory userHistory);

    Long countByUpdateTypeAndGuidAndLastModifiedDateAfter(@Param("updateType") UserUpdateTypeEcnum updateType, @Param("guid") String guid, @Param("minLastModifiedDate") LocalDateTime minLastModifiedDate);

}
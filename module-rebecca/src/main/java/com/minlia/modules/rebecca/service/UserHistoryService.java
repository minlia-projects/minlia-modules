package com.minlia.modules.rebecca.service;

import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.bean.domain.UserHistory;
import com.minlia.modules.rebecca.enumeration.UserUpdateTypeEcnum;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;

public interface UserHistoryService {

    void insertSelective(User user, UserUpdateTypeEcnum updateType);

    int updateByPrimaryKeySelective(UserHistory record);

    int deleteByPrimaryKey(Long id);

    UserHistory selectByPrimaryKey(Long id);

    long countByAll(UserHistory userHistory);

    long countByUpdateTypeAndGuidAndLastModifiedDateAfter(UserUpdateTypeEcnum updateType, String minGuid, LocalDateTime lastModifiedDate);

}

package com.minlia.modules.rebecca.service.impl;

import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.bean.domain.UserHistory;
import com.minlia.modules.rebecca.enumeration.UserUpdateTypeEcnum;
import com.minlia.modules.rebecca.mapper.UserHistoryMapper;
import com.minlia.modules.rebecca.service.UserHistoryService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class UserHistoryServiceImpl implements UserHistoryService {

    @Autowired
    private Mapper mapper;

    @Resource
    private UserHistoryMapper userHistoryMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return userHistoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(User user, UserUpdateTypeEcnum updateType) {
        UserHistory userHistory = mapper.map(user, UserHistory.class);
        userHistory.setUpdateType(updateType);
        return userHistoryMapper.insertSelective(userHistory);
    }

    @Override
    public UserHistory selectByPrimaryKey(Long id) {
        return userHistoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UserHistory record) {
        return userHistoryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public long countByAll(UserHistory userHistory) {
        return userHistoryMapper.countByAll(userHistory);
    }

    @Override
    public long countByUpdateTypeAndGuidAndLastModifiedDateAfter(UserUpdateTypeEcnum updateType, String minGuid, LocalDateTime lastModifiedDate) {
        return userHistoryMapper.countByUpdateTypeAndGuidAndLastModifiedDateAfter(updateType, minGuid, lastModifiedDate);
    }

}

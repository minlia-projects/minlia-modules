package com.minlia.module.wechat.login.mapper;
import org.apache.ibatis.annotations.Param;

import com.minlia.module.wechat.login.entity.WechatUser;
import com.minlia.module.wechat.login.ro.WechatUserQO;

import java.util.List;

public interface WechatUserMapper {

    void create(WechatUser wechatUser);

    void update(WechatUser wechatUser);

    void updateGuidByUnionId(String guid, String unionId);

    long countByUnionIdAndGuidNotNull(String unionId);

    List<WechatUser> queryByUnionIdAndGuidNotNull(String unionId);

    WechatUser queryOne(WechatUserQO qo);

    List<WechatUser> queryList(WechatUserQO qo);

    int deleteByWxCodeAndGuidIsNull(@Param("wxCode")String wxCode);

}

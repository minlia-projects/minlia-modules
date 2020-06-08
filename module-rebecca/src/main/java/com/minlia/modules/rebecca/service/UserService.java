package com.minlia.modules.rebecca.service;


import com.minlia.cloud.code.Code;
import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.bean.to.UserCTO;
import com.minlia.modules.rebecca.bean.to.UserUTO;
import com.minlia.modules.rebecca.enumeration.UserUpdateTypeEcnum;

import java.util.Set;

/**
 * 用户接口
 */
public interface UserService {

    User create(UserCTO cto);

    User update(UserUTO uto, UserUpdateTypeEcnum userUpdateType);

    User update(User user, UserUpdateTypeEcnum userUpdateType);

    void changeCellphone(User user, String newCellphone, String captcha);

    void changeEmail(User user, String newEmail, String captcha);

    int delete(String guid);

    Boolean locked(String guid);

    Boolean disabled(String guid);

    void grant(String guid, Set<Long> roles);

    Boolean updateOrg(String guid, Long orgId);



}

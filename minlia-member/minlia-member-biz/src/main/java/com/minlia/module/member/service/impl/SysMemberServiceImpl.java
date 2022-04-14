package com.minlia.module.member.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.module.member.bean.SysMemberQro;
import com.minlia.module.member.bean.vo.SysMemberInfoVo;
import com.minlia.module.member.constant.SysMemberConstants;
import com.minlia.module.member.entity.SysMemberEntity;
import com.minlia.module.member.event.SysUserAuthEvent;
import com.minlia.module.member.mapper.SysMemberMapper;
import com.minlia.module.member.service.SysMemberService;
import com.minlia.module.realname.bean.SysRealNameCro;
import com.minlia.module.realname.service.SysRealNameService;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.rebecca.user.bean.MemberRegisterRo;
import com.minlia.module.rebecca.user.bean.UserRegisterRo;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.enums.SysRegisterTypeEnum;
import com.minlia.module.rebecca.user.service.SysUserRegisterService;
import com.minlia.module.rebecca.user.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 会员信息 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-12-28
 */
@Service
@RequiredArgsConstructor
public class SysMemberServiceImpl extends ServiceImpl<SysMemberMapper, SysMemberEntity> implements SysMemberService {

    private final CaptchaService captchaService;
    private final SysUserService sysUserService;
    private final SysRealNameService sysRealNameService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SysUserRegisterService sysUserRegisterService;

    @Override
    public void create(SysUserEntity sysUserEntity) {
        this.save(SysMemberEntity.builder().uid(sysUserEntity.getId()).build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response register(MemberRegisterRo registerRo) {
        Response<SysUserEntity> response = sysUserRegisterService.register(UserRegisterRo.builder()
                .type(SysRegisterTypeEnum.CELLPHONE)
                .areaCode(registerRo.getAreaCode())
                .cellphone(registerRo.getCellphone())
                .vcode(registerRo.getVcode())
                .inviteCode(registerRo.getInviteCode())
                .roleCode(SysMemberConstants.DEFAULT_ROLE)
                .build());
        ApiAssert.state(response.isSuccess(), response.getCode(), response.getMessage());
        this.save(SysMemberEntity.builder().uid(response.getPayload().getId()).build());
        return response;
    }

    //@Override
    //@Transactional(rollbackFor = Exception.class)
    //public Response login(MemberLoginRequest loginRequest) {
    //    Response<SysUserEntity> response = sysUserRegisterService.register(UserRegisterRo.builder()
    //            .type(SysRegisterTypeEnum.CELLPHONE)
    //            .areaCode(loginRequest.getAreaCode())
    //            .cellphone(loginRequest.getCellphone())
    //            .vcode(loginRequest.getVcode())
    //            .inviteCode(loginRequest.getInviteCode())
    //            .roleCode(SysMemberConstants.DEFAULT_ROLE)
    //            .build());
    //    ApiAssert.state(response.isSuccess(), response.getCode(), response.getMessage());
    //    this.save(SysMemberEntity.builder().uid(response.getPayload().getId()).build());
    //    return response;
    //}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response realName(SysRealNameCro cro) {
        cro.setUid(SecurityContextHolder.getUid());
        Response realNameResponse = sysRealNameService.auth(cro);

        SysMemberEntity entity = this.getByUid(SecurityContextHolder.getUid());
        entity.setName(cro.getName());
        entity.setIdNumber(cro.getIdNumber());
        entity.setRealName(realNameResponse.isSuccess());
        this.updateById(entity);

        if (realNameResponse.isSuccess()) {
            //发布认证成功事件
            SysUserAuthEvent.onAuthed(entity.getUid());
        }
        return realNameResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setPassword(String password, String verificationCode) {
        SysMemberEntity entity = this.getByUid(SecurityContextHolder.getUid());
        Response response = captchaService.validity(SecurityContextHolder.getUserContext().getAreaCode() + SecurityContextHolder.getUserContext().getCellphone(), verificationCode);
        ApiAssert.state(response.isSuccess(), response.getCode(), response.getMessage());
        entity.setSecondaryPassword(bCryptPasswordEncoder.encode(password));
        return this.updateById(entity);
    }

    @Override
    public boolean verifyPassword(String password) {
        SysMemberEntity entity = this.getByUid(SecurityContextHolder.getUid());
        return bCryptPasswordEncoder.matches(password, entity.getSecondaryPassword());
    }

    @Override
    public SysMemberInfoVo me() {
        return this.baseMapper.selectDetailsByUid(SecurityContextHolder.getUid());
    }

    @Override
    public Page<SysMemberInfoVo> page(SysMemberQro qro) {
        return this.baseMapper.selectDetailsPage(qro.getPage(), qro);
    }

    @Override
    public SysMemberEntity getByUid(Long uid) {
        return this.getOne(Wrappers.<SysMemberEntity>lambdaQuery().eq(SysMemberEntity::getUid, uid));
    }

}
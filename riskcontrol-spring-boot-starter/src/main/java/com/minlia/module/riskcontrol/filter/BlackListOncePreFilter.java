package com.minlia.module.riskcontrol.filter;

import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.riskcontrol.constant.RiskCode;
import com.minlia.module.riskcontrol.enums.RiskLevelEnum;
import com.minlia.module.riskcontrol.event.RiskBlackIpEvent;
import com.minlia.module.riskcontrol.service.KieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(urlPatterns = "/*", filterName = "blackListOncePreFilter")
public class BlackListOncePreFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.info("请求地址：{} method {}", httpServletRequest.getRequestURI(), httpServletRequest.getMethod());
        //IP范围
//        RiskIpScopeEvent riskIpScopeEvent = new RiskIpScopeEvent();
//        KieService.execute(riskIpScopeEvent);
//        ApiAssert.state(riskIpScopeEvent.isMatched(), RiskCode.Message.BLACK_IP_SCOPE.code(), RiskCode.Message.BLACK_IP_SCOPE.i18nKey());

        //风控黑名单
        RiskBlackIpEvent riskBlackIpEvent = new RiskBlackIpEvent();
        KieService.execute(riskBlackIpEvent);
        ApiAssert.state(riskBlackIpEvent.getLevel() != RiskLevelEnum.DANGER, RiskCode.Message.BLACK_IP);


        ////黑名单IP
        //RiskBlackUrlService riskBlackUrlService = ContextHolder.getContext().getBean(RiskBlackUrlService.class);
        //if (!riskBlackUrlService.contain(RiskTypeEnum.WHITE, httpServletRequest.getRequestURI())) {
        //    RiskBlackIpEvent riskBlackIpEvent = new RiskBlackIpEvent(httpServletRequest.getRequestURI());
        //}


//        StatelessKieSession kieSession = ReloadDroolsRulesService.kieContainer.newStatelessKieSession();
//        //IP范围
//        RiskIpListService riskIpListService = ContextHolder.getContext().getBean(RiskIpListService.class);
//        RiskRecordService riskRecordService = ContextHolder.getContext().getBean(RiskRecordService.class);
//        kieSession.setGlobal("riskRecordService", riskRecordService);
//        kieSession.setGlobal("riskIpListService", riskIpListService);
//        RiskIpScopeEvent riskIpScopeEvent = new RiskIpScopeEvent();
//        riskIpScopeEvent.setSceneValue(riskIpScopeEvent.getIp());
//        kieSession.execute(riskIpScopeEvent);
//        ApiAssert.state(riskIpScopeEvent.isMatched(), RiskCode.Message.BLACK_IP_SCOPE.code(), RiskCode.Message.BLACK_IP_SCOPE.i18nKey());

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {

    }

}
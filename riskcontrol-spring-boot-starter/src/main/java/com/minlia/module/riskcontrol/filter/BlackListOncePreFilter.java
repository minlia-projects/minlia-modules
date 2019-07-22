package com.minlia.module.riskcontrol.filter;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.riskcontrol.constant.RiskCode;
import com.minlia.module.riskcontrol.enums.RiskTypeEnum;
import com.minlia.module.riskcontrol.event.RiskBlackIpEvent;
import com.minlia.module.riskcontrol.event.RiskIpScopeEvent;
import com.minlia.module.riskcontrol.service.KieService;
import com.minlia.module.riskcontrol.service.RiskBlackUrlService;
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
        log.info("请求地址：{}", httpServletRequest.getRequestURI());
        //IP范围
//        RiskIpScopeEvent riskIpScopeEvent = new RiskIpScopeEvent();
//        KieService.execute(riskIpScopeEvent);
//        ApiAssert.state(riskIpScopeEvent.isMatched(), RiskCode.Message.BLACK_IP_SCOPE.code(), RiskCode.Message.BLACK_IP_SCOPE.i18nKey());

        //黑名单IP
        RiskBlackUrlService riskBlackUrlService = ContextHolder.getContext().getBean(RiskBlackUrlService.class);
        if (!riskBlackUrlService.contain(RiskTypeEnum.WHITE, httpServletRequest.getRequestURI())) {
            RiskBlackIpEvent riskBlackIpEvent = new RiskBlackIpEvent(httpServletRequest.getRequestURI());
            KieService.execute(riskBlackIpEvent);
            ApiAssert.state(!riskBlackIpEvent.isMatched(), RiskCode.Message.BLACK_IP.code(), RiskCode.Message.BLACK_IP.i18nKey());
        }


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

        //黑名单
//        RiskBlackUrlService riskBlackUrlService = ContextHolder.getContext().getBean(RiskBlackUrlService.class);
//        if (!riskBlackUrlService.contain(RiskTypeEnum.WHITE, httpServletRequest.getRequestURI())) {
//            RiskBlackListService riskBlackListService = ContextHolder.getContext().getBean(RiskBlackListService.class);
//            kieSession.setGlobal("riskBlackListService", riskBlackListService);
//            kieSession.setGlobal("riskRecordService", riskRecordService);
//            RiskBlackIpEvent riskBlackIpEvent = new RiskBlackIpEvent();
//            riskBlackIpEvent.setScene(httpServletRequest.getRequestURI());
//            kieSession.execute(riskBlackIpEvent);
//            ApiAssert.state(!riskBlackIpEvent.isBlack(), RiskCode.Message.BLACK_IP.code(), RiskCode.Message.BLACK_IP.i18nKey());
//        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {

    }

}
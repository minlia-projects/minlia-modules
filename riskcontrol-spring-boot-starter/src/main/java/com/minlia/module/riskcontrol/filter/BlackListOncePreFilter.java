package com.minlia.module.riskcontrol.filter;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.drools.service.ReloadDroolsRulesService;
import com.minlia.module.riskcontrol.constant.RiskCode;
import com.minlia.module.riskcontrol.enums.RiskTypeEnum;
import com.minlia.module.riskcontrol.event.RiskBlackIpEvent;
import com.minlia.module.riskcontrol.event.RiskIpScopeEvent;
import com.minlia.module.riskcontrol.service.RiskBlackListService;
import com.minlia.module.riskcontrol.service.RiskBlackUrlService;
import com.minlia.module.riskcontrol.service.RiskIpListService;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@Component
@WebFilter(urlPatterns = "/*", filterName = "blackListOncePreFilter")
public class BlackListOncePreFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        StatelessKieSession kieSession = ReloadDroolsRulesService.kieContainer.newStatelessKieSession();

        //IP范围
        RiskIpListService riskIpListService = ContextHolder.getContext().getBean(RiskIpListService.class);
        kieSession.setGlobal("riskIpListService", riskIpListService);
        RiskIpScopeEvent riskIpScopeEvent = new RiskIpScopeEvent();
        riskIpScopeEvent.setSceneValue(riskIpScopeEvent.getIp());
        kieSession.execute(riskIpScopeEvent);
        ApiAssert.state(riskIpScopeEvent.isMatched(), RiskCode.Message.BLACK_IP_SCOPE.code(), RiskCode.Message.BLACK_IP_SCOPE.i18nKey());

        //黑名单
        RiskBlackUrlService riskBlackUrlService = ContextHolder.getContext().getBean(RiskBlackUrlService.class);
        if (!riskBlackUrlService.contain(RiskTypeEnum.WHITE, httpServletRequest.getRequestURI())) {
            RiskBlackListService riskBlackListService = ContextHolder.getContext().getBean(RiskBlackListService.class);
            kieSession.setGlobal("riskBlackListService", riskBlackListService);
            RiskBlackIpEvent riskBlackIpEvent = new RiskBlackIpEvent();
            riskBlackIpEvent.setScene(httpServletRequest.getRequestURI());
            kieSession.execute(riskBlackIpEvent);
            ApiAssert.state(!riskBlackIpEvent.isBlack(), RiskCode.Message.BLACK_IP.code(), RiskCode.Message.BLACK_IP.i18nKey());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {

    }

}
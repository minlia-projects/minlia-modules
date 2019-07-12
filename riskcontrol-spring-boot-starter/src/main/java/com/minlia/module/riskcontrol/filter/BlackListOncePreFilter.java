package com.minlia.module.riskcontrol.filter;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.drools.service.ReloadDroolsRulesService;
import com.minlia.module.riskcontrol.constant.RiskCode;
import com.minlia.module.riskcontrol.entity.RiskBlackUrl;
import com.minlia.module.riskcontrol.event.RiskBlackIpEvent;
import com.minlia.module.riskcontrol.service.RiskBlackListService;
import com.minlia.module.riskcontrol.service.RiskBlackUrlService;
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

        System.out.println(httpServletRequest.getServletPath());

        RiskBlackUrlService riskBlackUrlService = ContextHolder.getContext().getBean(RiskBlackUrlService.class);
        if (!riskBlackUrlService.contain(RiskBlackUrl.EnumType.WHITE, httpServletRequest.getRequestURI())) {
            StatelessKieSession kieSession = ReloadDroolsRulesService.kieContainer.newStatelessKieSession();
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
////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.minlia.modules.security.authentication.handler;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import com.minlia.modules.security.authentication.jwt.extractor.JwtHeaderTokenExtractor;
//import com.minlia.modules.security.autoconfiguration.WebSecurityConfig;
//import com.minlia.modules.security.model.token.RawAccessJwtToken;
//import com.minlia.modules.security.model.token.TokenCacheUtils;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//import org.springframework.util.Assert;
//
//public class DisableMultipleSecurityContextLogoutHandler implements LogoutHandler {
//    protected final Log logger = LogFactory.getLog(this.getClass());
//    private boolean invalidateHttpSession = true;
//    private boolean clearAuthentication = true;
//
//    public DisableMultipleSecurityContextLogoutHandler() {
//    }
//
//    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//
//        System.out.println("aa");
//
//
////        String guid = authentication.getName();
////        TokenCacheUtils.kill(guid);
//
////        Assert.notNull(request, "HttpServletRequest required");
////        if (this.invalidateHttpSession) {
////            HttpSession session = request.getSession(false);
////            if (session != null) {
////                this.logger.debug("Invalidating session: " + session.getId());
////                session.invalidate();
////            }
////        }
////
////        if (this.clearAuthentication) {
////            SecurityContext context = SecurityContextHolder.getContext();
////            context.setAuthentication((Authentication) null);
////        }
////
////        SecurityContextHolder.clearContext();
//    }
//
//    public boolean isInvalidateHttpSession() {
//        return this.invalidateHttpSession;
//    }
//
//    public void setInvalidateHttpSession(boolean invalidateHttpSession) {
//        this.invalidateHttpSession = invalidateHttpSession;
//    }
//
//    public void setClearAuthentication(boolean clearAuthentication) {
//        this.clearAuthentication = clearAuthentication;
//    }
//}

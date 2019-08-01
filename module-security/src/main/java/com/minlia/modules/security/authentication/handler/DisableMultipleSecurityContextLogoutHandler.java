//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.minlia.modules.security.authentication.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisableMultipleSecurityContextLogoutHandler implements LogoutHandler {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private boolean invalidateHttpSession = true;
    private boolean clearAuthentication = true;

    public DisableMultipleSecurityContextLogoutHandler() {
    }

    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        System.out.println("aa");


//        String guid = authentication.getName();
//        TokenCacheUtils.kill(guid);

//        Assert.notNull(request, "HttpServletRequest required");
//        if (this.invalidateHttpSession) {
//            HttpSession session = request.getSession(false);
//            if (session != null) {
//                this.logger.debug("Invalidating session: " + session.getId());
//                session.invalidate();
//            }
//        }
//
//        if (this.clearAuthentication) {
//            SecurityContext context = SecurityContextHolder.getContext();
//            context.setAuthentication((Authentication) null);
//        }
//
//        SecurityContextHolder.clearContext();
    }

    public boolean isInvalidateHttpSession() {
        return this.invalidateHttpSession;
    }

    public void setInvalidateHttpSession(boolean invalidateHttpSession) {
        this.invalidateHttpSession = invalidateHttpSession;
    }

    public void setClearAuthentication(boolean clearAuthentication) {
        this.clearAuthentication = clearAuthentication;
    }
}

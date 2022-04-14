//package com.minlia.modules.security.authentication.provider;
//
//import com.minlia.modules.security.authentication.service.AuthenticationService;
//import com.minlia.modules.security.code.SecurityCode;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.authentication.ProviderNotFoundException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
///**
// * @author garen
// * @version 1.0
// * @description
// * @date 2019/7/31 5:46 PM
// */
//@Component
//public class CellphoneCaptchaAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
//
//    @Autowired
//    private AuthenticationService authenticationService;
//
//    @Autowired
//    @Qualifier("defaultUserDetailsService")
//    private UserDetailsService userDetailsService;
//
//    /**
//     * 附件检查：验证码有效性.
//     *
//     * @param userDetails
//     * @param authentication
//     * @throws AuthenticationException
//     */
//    @Override
//    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        if (this.authenticationService == null) {
//            this.logger.debug("Authentication failed: no credentials provided");
//            throw new ProviderNotFoundException(SecurityCode.Exception.PROVIDER_AUTHENTICATION_SERVICE_NOT_FOUND.message("cellphoneCaptchaAuthenticationService"));
//        }
//
//        if (authentication.getCredentials() == null) {
//            this.logger.debug("Authentication failed: no credentials provided");
//            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
//        } else {
////            if (!authenticationService.authentication(authentication)) {
////                this.logger.debug("Authentication failed: password does not match stored value");
////                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
////            }
//        }
//    }
//
//    @Override
//    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        UserDetails loadedUser;
//        try {
//            loadedUser = this.getUserDetailsService().loadUserByUsername(username);
//        } catch (UsernameNotFoundException var6) {
////            if (authentication.getCredentials() != null) {
////                String presentedPassword = authentication.getCredentials().toString();
////                this.passwordEncoder.isPasswordValid(this.userNotFoundEncodedPassword, presentedPassword, (Object) null);
////            }
//
//            throw var6;
//        } catch (Exception var7) {
//            throw new InternalAuthenticationServiceException(var7.getMessage(), var7);
//        }
//
//        if (loadedUser == null) {
//            throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
//        } else {
//            return loadedUser;
//        }
//    }
//
//    protected UserDetailsService getUserDetailsService() {
//        return this.userDetailsService;
//    }
//
//}

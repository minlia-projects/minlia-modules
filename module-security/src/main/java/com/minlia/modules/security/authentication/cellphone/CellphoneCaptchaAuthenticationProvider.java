package com.minlia.modules.security.authentication.cellphone;

import com.minlia.modules.security.authentication.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author garen
 */
@Component
@RequiredArgsConstructor
public class CellphoneCaptchaAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationService cellphoneVerificationCodeAuthenticationService;

    /**
     * 具体实现请参见 @see RbacAuthenticationService
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return cellphoneVerificationCodeAuthenticationService.authentication(authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
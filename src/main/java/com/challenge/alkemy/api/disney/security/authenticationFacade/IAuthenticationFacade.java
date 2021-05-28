package com.challenge.alkemy.api.disney.security.authenticationFacade;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}

package com.blackyaga.security.service.auth.authorization;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("hasAuthority('"+ Role.Secured.USER+"')")
public @interface UserAuthorization {
}

package com.blackyaga.security.service.config;

import com.blackyaga.security.service.auth.AuthService;
import org.lognet.springboot.grpc.security.AuthenticationSchemeSelector;
import org.lognet.springboot.grpc.security.GrpcSecurity;
import org.lognet.springboot.grpc.security.GrpcSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

@Configuration
public class GrpcSecurityConfig extends GrpcSecurityConfigurerAdapter {
    private final String PREFIX_ADMIN="ADMIN";
    private final String PREFIX_USER="USER";


    @Autowired
    private AuthService authService;
    @Override
    public void configure(GrpcSecurity builder) throws Exception {
        builder.authenticationSchemeSelector(new AuthenticationSchemeSelector() {
            @Override
            public Optional<Authentication> getAuthScheme(CharSequence authorization) {
                var prefix=authorization.toString().split(" ")[0];
                Optional<Authentication> authentication=null;
                if (prefix.equals(PREFIX_ADMIN)){
                     authentication=adminPrefix(authorization);
                }else if(prefix.equals(PREFIX_USER)){
                    authentication=userPrefix(authorization);
                }else
                    authentication=Optional.empty();
                return authentication;
            }
        });
    }

    private Optional<Authentication> adminPrefix(CharSequence authorization){
        var token=authorization.toString().substring(PREFIX_ADMIN.length()+1);
        if (token==null)
            return Optional.empty();
        var auth=authService.getAdmin(token);
        if (auth==null)
            return Optional.empty();
        var a=new UsernamePasswordAuthenticationToken(auth,null,auth.getAuthorities());
        return Optional.of(a);
    }

    private Optional<Authentication> userPrefix(CharSequence authorization){
        var token=authorization.toString().substring(PREFIX_USER.length()+1);
        if (token==null)
            return Optional.empty();
        var auth=authService.getUser(token);
        if (auth==null)
            return Optional.empty();
        var a=new UsernamePasswordAuthenticationToken(auth,null,auth.getAuthorities());
        return Optional.of(a);
    }
}

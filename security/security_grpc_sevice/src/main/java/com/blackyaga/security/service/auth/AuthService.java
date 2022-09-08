package com.blackyaga.security.service.auth;

import com.blackyaga.security.service.auth.authorization.AdminAuth;
import com.blackyaga.security.service.auth.authorization.UserAuth;
import com.blackyaga.security.service.model.Admin;
import com.blackyaga.security.service.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {
    private List<User> users=new ArrayList<>();
    private List<Admin> admins=new ArrayList<>();

    public AdminAuth getAdmin(String token){
        var admin=admins.stream().filter(a -> a.getToken().equals(token)).findFirst();
        if (admin.isEmpty())
            return null;
        AdminAuth auth=new AdminAuth();
        auth.setAdmin(admin.get());
     return auth;
    }
    public UserAuth getUser(String token){
        var user=users.stream().filter(u -> u.getToken().equals(token)).findFirst();
        if (user.isEmpty())
            return null;
        UserAuth auth=new UserAuth();
        auth.setUser(user.get());
        return auth;
    }
    public String adminAuthenticate(Credentials credentials){
        Admin admin=new Admin();
        admin.setPassword(credentials.getPassword());
        admin.setUsername(credentials.getUsername());
        admin.setToken(UUID.randomUUID().toString());
        admins.add(admin);
        return admin.getToken();
    }
    public String userAuthenticate(Credentials credentials){
        User user=new User();
        user.setPassword(credentials.getPassword());
        user.setUsername(credentials.getUsername());
        user.setToken(UUID.randomUUID().toString());
        users.add(user);
        return user.getToken();
    }

    public AdminAuth getAdminAuth(){
        return (AdminAuth) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public UserAuth getUserAuth(){
        return (UserAuth) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

}

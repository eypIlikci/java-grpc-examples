package com.blackyaga.security.service.auth.authorization;

public enum Role{
    USER(Secured.USER),
    ADMIN(Secured.ADMIN)
    ;
    private String role;
    Role(String role) {
        this.role = role;

    }
    public String getAuthority() {
        return name();
    }
    public static class Secured{
        public static final String ADMIN= "ADMIN";
        public static final String USER="USER";
    }
}

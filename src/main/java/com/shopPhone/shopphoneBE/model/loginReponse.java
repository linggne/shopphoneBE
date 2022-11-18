package com.shopPhone.shopphoneBE.model;

public class loginReponse {
    private String token;
    private String role;

    
    public loginReponse(String token, String role) {
        this.token = token;
        this.role = role;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    
}

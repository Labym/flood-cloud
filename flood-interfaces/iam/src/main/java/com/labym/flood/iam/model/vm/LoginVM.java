package com.labym.flood.iam.model.vm;

import lombok.Data;

@Data
public class LoginVM {
    private String username;
    private String password;
    private String captcha;
    private boolean rememberMe;
}

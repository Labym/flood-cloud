package com.labym.flood.uc.model.vm;

import lombok.Data;

@Data
public class LoginVM {
    private String username;
    private String password;
    private CaptchaVM captcha;
    private boolean rememberMe;
}

package com.labym.flood.iam.model.vo;

import lombok.Data;

@Data
public class LoginVO {
    private String username;
    private String password;
    private String captcha;
    private boolean rememberMe;
}

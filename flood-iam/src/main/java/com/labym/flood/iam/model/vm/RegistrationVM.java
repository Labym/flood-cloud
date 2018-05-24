package com.labym.flood.iam.model.vm;

import lombok.Data;

@Data
public class RegistrationVM {
    private String email;
    private String mobile;
    private String password;
    private String confirm;
    private CaptchaVM captcha;
}

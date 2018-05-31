package com.labym.flood.uc.model.vm;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.labym.flood.uc.config.Constants.LOGIN_REGEX;

@Data
public class RegistrationVM {
    @NotNull(message = "email is null")
    @Pattern(regexp = LOGIN_REGEX,message = "not validate email")
    private String email;
    private String mobile;
    @NotNull(message = "password is null")
    @Size(min = 6,max = 20,message = "password length must between 6 and 20")
    private String password;
    private String confirm;
    @NotNull(message = "captcha is null")
    private CaptchaVM captcha;
}

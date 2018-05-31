package com.labym.flood.uc.model.vm;

import lombok.Data;

@Data
public class TokenVO {

    public TokenVO(String token){
        this.token=token;
    }

    private String token;

    private String type="Bearer";
}

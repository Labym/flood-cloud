package com.labym.flood.iam.model.vm;

import lombok.Data;

@Data
public class TokenVO {

    public TokenVO(String token){
        this.token=token;
    }

    private String token;
}

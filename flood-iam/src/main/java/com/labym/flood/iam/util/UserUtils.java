package com.labym.flood.iam.util;

import org.springframework.util.StringUtils;

public class UserUtils {

    public static final long NEVER_EXPIRED = -1L;

    public static boolean accountNonExpired(Long expireAt) {
        if (null == expireAt || expireAt.equals(NEVER_EXPIRED)) {
            return true;
        }
        return System.currentTimeMillis()<expireAt;
    }

    public static boolean checkPassword(String password){
        if (StringUtils.isEmpty(password)) {
            return false;
        }

        int length=password.length();
        if(length<6||length>20){
            return false;
        }
        return true;
    }

}

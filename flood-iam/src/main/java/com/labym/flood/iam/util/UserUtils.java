package com.labym.flood.iam.util;

public class UserUtils {

    public static final long NEVER_EXPIRED = -1L;

    public static boolean accountNonExpired(Long expireAt) {
        if (null == expireAt || expireAt.equals(NEVER_EXPIRED)) {
            return true;
        }

        return System.currentTimeMillis()<expireAt;
    }
}

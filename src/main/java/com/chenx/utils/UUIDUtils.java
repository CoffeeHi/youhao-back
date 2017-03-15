package com.chenx.utils;

import java.util.UUID;

public final class UUIDUtils {

    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        return s.replace("-","");
    }

}

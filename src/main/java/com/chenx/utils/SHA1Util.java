package com.chenx.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
public class SHA1Util {
    public static String getSHA1(String data){
        StringBuffer buf = new StringBuffer("");
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());

            byte[] bits = md.digest();
            for(int i=0;i<bits.length;i++){
                int a = bits[i];
                if(a<0) a+=256;
                if(a<16) buf.append("0");
                buf.append(Integer.toHexString(a));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return buf.toString();
        }
    }
}

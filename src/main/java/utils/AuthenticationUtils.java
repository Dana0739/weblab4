package utils;

import org.apache.commons.codec.digest.DigestUtils;

public final class AuthenticationUtils {

    public static String encode(String password) {
        String md5Hex = DigestUtils.md5Hex(password);
        return md5Hex;
    }
}
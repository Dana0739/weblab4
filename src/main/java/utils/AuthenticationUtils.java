package utils;

import org.apache.commons.codec.digest.DigestUtils;

public final class AuthenticationUtils {

    public static String encode(String password) {
        return DigestUtils.md5Hex(password);
    }
}
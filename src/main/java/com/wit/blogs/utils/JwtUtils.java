package com.wit.blogs.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
@Slf4j
public class JwtUtils {
    public static String generateKeyAsString(){
        SecretKey key = Jwts.SIG.HS256.key().build();
        String secretString = Encoders.BASE64.encode(key.getEncoded());
        log.info("Secret key generated: " + secretString);
        return  secretString;
    }
}

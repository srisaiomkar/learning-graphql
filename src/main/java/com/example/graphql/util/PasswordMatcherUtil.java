package com.example.graphql.util;

import org.bouncycastle.crypto.generators.OpenBSDBCrypt;

import java.nio.charset.StandardCharsets;

public class PasswordMatcherUtil {
    public static Boolean isPasswordMatch(String givenPassword, String dbPassword){
        return OpenBSDBCrypt.checkPassword(dbPassword, givenPassword.getBytes(StandardCharsets.UTF_8));
    }
}

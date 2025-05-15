package com.buct_ai_bd_se2025.managementsystem.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String encryptPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String rawPassword, String Password) {
        return rawPassword.equals(Password);
    }
}

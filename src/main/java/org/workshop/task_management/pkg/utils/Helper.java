package org.workshop.task_management.pkg.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Helper {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Generate Bcrypt hash from plain text password
    public static String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    // Verify plain password against encoded hash
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }

}

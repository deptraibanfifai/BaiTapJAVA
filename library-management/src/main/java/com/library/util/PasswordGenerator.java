package com.library.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordGenerator {
    public static void main(String[] args) {
        String[] passwords = {"admin123", "123456"};
        for (String password : passwords) {
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            System.out.println("Password: " + password);
            System.out.println("Hash: " + hashed);
            System.out.println("---");
        }
    }
}
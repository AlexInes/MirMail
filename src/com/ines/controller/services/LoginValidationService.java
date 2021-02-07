package com.ines.controller.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginValidationService {
    private static final String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final Pattern pattern = Pattern.compile(regex);

    public static boolean passwordValidated(String password){
        return !password.isEmpty();
    }

    public static boolean emailValidated(String email){
        Matcher matcher = pattern.matcher(email);
        return !email.isEmpty() && (matcher.matches());
    }
}

package com.project.ecommerce.utils;

import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

import static com.project.ecommerce.bootstrap.Constants.EMAIL_PATTERN;

@RequiredArgsConstructor
public class Validator {

    public static boolean isAlphaNumeric(String data) {

        if (data.length() > 1) {
            for (int i = 0; i < data.length(); i++) {
                char c = data.charAt(i);
                if (!Character.isLetterOrDigit(c))
                    return false;
            }
            return true;
        }

        return false;
    }

    public static boolean isValidPasswordFormat(String password) {
        if (isAlphaNumeric(password) && password.length() > 5) return true;
        return false;
    }

    public static boolean isValidEmailFormat(String email) {
        return Pattern.compile(EMAIL_PATTERN)
                .matcher(email)
                .matches();
    }

}

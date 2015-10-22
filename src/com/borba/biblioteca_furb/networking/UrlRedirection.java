package com.borba.biblioteca_furb.networking;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.borba.biblioteca_furb.LoginInvalidoException;

public class UrlRedirection {

    public String getUrl(String responseContent) throws LoginInvalidoException {
        try {
            Matcher matcher = Pattern.compile(".*location.replace\\('(.*?)'\\).*?").matcher(responseContent);
            matcher.find();

            return matcher.group(1);
        } catch (IllegalStateException e) {
            throw new LoginInvalidoException();
        }
    }

}

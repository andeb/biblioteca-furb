package com.borba.biblioteca_furb.networking;

import java.util.Date;

import org.apache.http.cookie.Cookie;

public class CookieImpl implements Cookie {

    private final String mCookieValue;

    public CookieImpl(String cookieValue) {
        mCookieValue = cookieValue;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public boolean isPersistent() {
        return false;
    }

    @Override
    public boolean isExpired(Date date) {
        return false;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public String getValue() {
        return mCookieValue;
    }

    @Override
    public int[] getPorts() {
        return null;
    }

    @Override
    public String getPath() {
        return "/";
    }

    @Override
    public String getName() {
        return "PHPSESSID";
    }

    @Override
    public Date getExpiryDate() {
        return null;
    }

    @Override
    public String getDomain() {
        return "bu.furb.br";
    }

    @Override
    public String getCommentURL() {
        return null;
    }

    @Override
    public String getComment() {
        return null;
    }
}

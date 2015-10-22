package com.borba.biblioteca_furb.networking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

public class CookieStoreImpl implements CookieStore {

    List<Cookie> mLista = new ArrayList<Cookie>();

    public CookieStoreImpl(Cookie cookie) {
        mLista.add(cookie);
    }

    @Override
    public List<Cookie> getCookies() {
        return mLista;
    }

    @Override
    public boolean clearExpired(Date date) {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public void addCookie(Cookie cookie) {
    }

}

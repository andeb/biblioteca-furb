package com.borba.biblioteca_furb;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;

import com.borba.biblioteca_furb.networking.CookiesFromUrl;
import com.borba.biblioteca_furb.networking.LoginRequest;
import com.borba.biblioteca_furb.networking.ResponseContent;
import com.borba.biblioteca_furb.networking.UrlRedirection;

public class LoginService {

    private final AbstractHttpClient mHttpClient;

    public LoginService(AbstractHttpClient httpClient) {
        this.mHttpClient = httpClient;
    }

    public List<Cookie> getCookies(String user, String password) throws Exception {
        HttpUriRequest request = new LoginRequest().getRequest(user, password);
        HttpResponse response = mHttpClient.execute(request);
        String content = new ResponseContent().getFullContent(response);
        String novaUrl = new UrlRedirection().getUrl(content);

        return new CookiesFromUrl(mHttpClient).getCookies(novaUrl);
    }

}

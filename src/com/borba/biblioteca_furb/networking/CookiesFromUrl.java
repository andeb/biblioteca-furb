package com.borba.biblioteca_furb.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;

import com.borba.biblioteca_furb.LoginInvalidoException;

public class CookiesFromUrl {

    private final AbstractHttpClient mHttpClient;

    public CookiesFromUrl(AbstractHttpClient httpClient) {
        this.mHttpClient = httpClient;
    }

    public List<Cookie> getCookies(String url) throws ClientProtocolException, IOException, LoginInvalidoException {
        HttpResponse httpResponse = mHttpClient.execute(new HttpGet(url));

        String content = getFullContent(httpResponse);
        if (content.contains("Pessoa não identificada ou não possui mais vínculo com a instituição!")) {
            throw new LoginInvalidoException("Pessoa não identificada ou não possui mais vínculo com a instituição.");
        }
        // FIXME: Quando receber "location.replace('index.php');", trocar pela mensagem do "alert()".  
        // 
        // <script language="JavaScript">  alert('Pessoa não identificada ou não possui mais vínculo com a instituição!');  location.replace('index.php');</script>
        return mHttpClient.getCookieStore().getCookies();
    }

    public String getFullContent(HttpResponse response) throws IllegalStateException, IOException {
        HttpEntity entity = response.getEntity();

        BufferedReader input = new BufferedReader(new InputStreamReader(entity.getContent(), "iso-8859-1"));

        StringBuilder sb = new StringBuilder(1024);
        String line;
        while ((line = input.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

}

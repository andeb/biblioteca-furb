package com.borba.biblioteca_furb.networking;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

public class LoginRequest {

    private static final String loginUrl = "https://www.furb.br/scripts/ssl_ldap/ssl_ldap_bc.php";
    private static final String paginaUrl = "http://bu.furb.br/consulta/servicosUsuario/loginServicos.php?acao=validar";

    public HttpUriRequest getRequest(String usuario, String senha) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(loginUrl);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("acao", "validar"));
        nvps.add(new BasicNameValuePair("pagina", paginaUrl));
        nvps.add(new BasicNameValuePair("login", usuario));
        nvps.add(new BasicNameValuePair("senha", senha));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        return httpPost;
    }
}

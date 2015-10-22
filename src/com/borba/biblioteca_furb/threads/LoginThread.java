package com.borba.biblioteca_furb.threads;

import java.util.List;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import com.borba.biblioteca_furb.LoginInvalidoException;
import com.borba.biblioteca_furb.LoginService;
import com.borba.biblioteca_furb.activities.LoginActivity;

public class LoginThread extends Thread {

    private final LoginActivity mLoginActivity;
    private final String mUsuario;
    private final String mSenha;

    public LoginThread(LoginActivity mLoginActivity, String mUsuario, String mSenha) {
        this.mLoginActivity = mLoginActivity;
        this.mUsuario = mUsuario;
        this.mSenha = mSenha;
    }

    @Override
    public void run() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        LoginService loginService = new LoginService(httpClient);

        try {
            List<Cookie> cookies = loginService.getCookies(mUsuario, mSenha);
            mLoginActivity.loginComSucesso(cookies);
        } catch (LoginInvalidoException e) {
            String message = e.getMessage() == null ? "Nome de usuário ou senha incorretos." : e.getMessage();
            mLoginActivity.loginSemSucesso(message);
        } catch (Exception e) {
            e.printStackTrace();
            mLoginActivity.loginSemSucesso("Falha ao fazer login.");
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

}

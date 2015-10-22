package com.borba.biblioteca_furb.activities;

import java.util.List;

import org.apache.http.cookie.Cookie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.borba.biblioteca_furb.Preferencias;
import com.borba.biblioteca_furb.threads.LoginThread;
import com.borba.furb.biblioteca.R;

public class LoginActivity extends Activity {

    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca_furb_login);

        Preferencias preferencias = new Preferencias(getPreferences(Context.MODE_PRIVATE));
        String usuario = preferencias.getUsuario();
        String senha = preferencias.getSenha();

        if (!usuario.equals("") && !senha.equals("")) {
            setUsuario(usuario);
            setSenha(senha);
            fazLogin(usuario, senha);
        }
    }

    private void fazLogin(String usuario, String senha) {
        mProgressDialog = ProgressDialog.show(this, "", "Fazendo login...", true);
        new LoginThread(this, usuario, senha).start();
    }

    public void loginComSucesso(List<Cookie> cookies) {
        Preferencias preferencias = new Preferencias(getPreferences(Context.MODE_PRIVATE));
        preferencias.salvaLogin(getUsuario(), getSenha());

        mProgressDialog.dismiss();

        Intent intent = new Intent(this, ListaLivrosActivity.class);
        intent.putExtra("cookie", cookies.get(0).getValue());

        startActivity(intent);
    }

    public void loginSemSucesso(final String text) {
        mProgressDialog.dismiss();

        final Activity thisActivity = this;
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(thisActivity).
                /**/setTitle("Biblioteca FURB").
                /**/setMessage(text).
                /**/setPositiveButton("OK", null).
                /**/show();
            }

        });
    }

    /**
     * @param view
     */
    public void onLogin(View view) {
        onLogin();
    }

    public void onLogin() {
        fazLogin(getUsuario(), getSenha());
    }

    private String getSenha() {
        return ((EditText) findViewById(R.id.edit_password)).getText().toString();
    }

    private void setSenha(String senha) {
        ((EditText) findViewById(R.id.edit_password)).setText(senha);
    }

    @SuppressLint("DefaultLocale")
    private String getUsuario() {
        return ((EditText) findViewById(R.id.edit_username)).getText().toString().toLowerCase();
    }

    private void setUsuario(String usuario) {
        ((EditText) findViewById(R.id.edit_username)).setText(usuario);
    }

}

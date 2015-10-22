package com.borba.biblioteca_furb;

import android.content.SharedPreferences;

public class Preferencias {

    public static final String sharedPrefUsuario = "usuario";
    public static final String sharedPrefSenha = "senha";

    SharedPreferences mSharedPrefs;

    public Preferencias(SharedPreferences mSharedPrefs) {
        this.mSharedPrefs = mSharedPrefs;
    }

    public String getUsuario() {
        return mSharedPrefs.getString(sharedPrefUsuario, "");
    }

    public String getSenha() {
        return mSharedPrefs.getString(sharedPrefSenha, "");
    }

    public void salvaLogin(String usuario, String senha) {
        SharedPreferences.Editor editor = mSharedPrefs.edit();

        editor.putString(sharedPrefUsuario, usuario);
        editor.putString(sharedPrefSenha, senha);

        editor.commit();
    }
}

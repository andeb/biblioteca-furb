package com.borba.biblioteca_furb;

public class Registro {

    private String mCodigo;
    private String mTitulo;
    private String mData;

    public Registro(String codigo, String titulo, String data) {
        this.mCodigo = codigo;
        this.mTitulo = titulo;
        this.mData = data;
    }

    @Override
    public String toString() {
        String s = "";
        if (mTitulo.length() >= 30) {
            s += mTitulo.substring(0, 30) + "...";
        } else {
            s += mTitulo;
        }
        return s + "\nData fim: " + mData;
    }

    public String getCodigo() {
        return mCodigo;
    }
}

package com.borba.biblioteca_furb.threads;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.AbstractHttpClient;

import android.util.Log;

import com.borba.biblioteca_furb.RenovacaoService;
import com.borba.biblioteca_furb.activities.ListaLivrosActivity;

public class RenovaLivrosThread extends Thread {

    private final ListaLivrosActivity mListaLivrosActivity;
    private final List<String> mRegistros;
    private final AbstractHttpClient mHttpClient;

    public RenovaLivrosThread(ListaLivrosActivity listaLivrosActivity, List<String> registros, AbstractHttpClient httpClient) {
        this.mListaLivrosActivity = listaLivrosActivity;
        this.mRegistros = registros;
        this.mHttpClient = httpClient;
    }

    @Override
    public void run() {
        RenovacaoService renovacaoService = new RenovacaoService(mHttpClient);
        List<String> naoRenovados = new ArrayList<String>();

        try {
            for (String registro : mRegistros) {
                if (!renovacaoService.renova(registro)) {
                    naoRenovados.add(registro);
                }
            }

            if (naoRenovados.size() == 0) {
                mListaLivrosActivity.renovacaoConcluida("Livros renovados com sucesso.");
            } else if (naoRenovados.size() == 1) {
                mListaLivrosActivity.renovacaoConcluida( //
                                                         "Um livro n�o pode ser renovado " + //
                                                         "por j� possuir reservas: " + naoRenovados);
            } else {
                mListaLivrosActivity.renovacaoConcluida( //
                                                         "Alguns livros n�o puderam ser renovados " + //
                                                         "por j� possu�rem reservas: " + naoRenovados);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("renova_livros", e.getMessage(), e);
            mListaLivrosActivity.renovacaoConcluida( //
                                                     "Houve um erro ao realizar a renova��o. " + //
                                                     "Tente novamente mais tarde ou, se o problema persistir, " + //
                    "entre em contato comigo atrav�s do Google Play.");
        }
    }

}

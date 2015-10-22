package com.borba.biblioteca_furb.threads;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.AbstractHttpClient;

import com.borba.biblioteca_furb.Registro;
import com.borba.biblioteca_furb.RegistrosRepository;
import com.borba.biblioteca_furb.activities.ListaLivrosActivity;

public class ListaLivrosThread extends Thread {

    private final ListaLivrosActivity mListaLivrosActivity;
    private final AbstractHttpClient mHttpClient;

    public ListaLivrosThread(ListaLivrosActivity listaLivrosActivity, AbstractHttpClient httpClient) {
        this.mListaLivrosActivity = listaLivrosActivity;
        this.mHttpClient = httpClient;
    }

    @Override
    public void run() {
        RegistrosRepository repository = new RegistrosRepository(mHttpClient);

        try {
            List<String[]> codigos = repository.buscaRegistros();
            List<Registro> registros = new ArrayList<Registro>();

            for (String[] codigo : codigos) {
                String buscaTitulo = repository.buscaTitulo(codigo[0]);
                registros.add(new Registro(codigo[0], buscaTitulo, codigo[1]));
            }

            mListaLivrosActivity.setRegistros(registros);
        } catch (Exception e) {
            e.printStackTrace();
            mListaLivrosActivity.renovacaoConcluida( //
                                                     "Houve um erro ao listar os livros. " + //
                                                     "Tente novamente mais tarde ou, se o problema persistir, " + //
                    "entre em contato comigo através do Google Play.");
        }
    }

}

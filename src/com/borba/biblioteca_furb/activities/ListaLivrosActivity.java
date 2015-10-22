package com.borba.biblioteca_furb.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.borba.biblioteca_furb.Registro;
import com.borba.biblioteca_furb.networking.CookieImpl;
import com.borba.biblioteca_furb.networking.CookieStoreImpl;
import com.borba.biblioteca_furb.threads.ListaLivrosThread;
import com.borba.biblioteca_furb.threads.RenovaLivrosThread;
import com.borba.furb.biblioteca.R;

public class ListaLivrosActivity extends ListActivity {

    private AbstractHttpClient mHttpClient;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        Intent intent = getIntent();
        String cookie = intent.getStringExtra("cookie");

        mHttpClient = new DefaultHttpClient();
        mHttpClient.setCookieStore(new CookieStoreImpl(new CookieImpl(cookie)));

        mProgressDialog = ProgressDialog.show(this, "", "Carregando livros...", true);
        new ListaLivrosThread(this, mHttpClient).start();
    }

    public void setRegistros(List<Registro> registros) {
        ArrayAdapter<Registro> adapter = new ArrayAdapter<Registro>(this, android.R.layout.simple_list_item_multiple_choice, registros);
        setAdaptador(adapter);

        //    try {
        //        runOnUiThread(new Runnable() {
        //
        //            @Override
        //            public void run() {
        //                Utility.setListViewHeightBasedOnChildren(getListView());
        //            }
        //
        //        });
        //    } catch (Throwable t) {
        //        t.printStackTrace();
        //        Log.e("renova", t.getMessage(), t);
        //    }
        mProgressDialog.dismiss();
    }

    public void setAdaptador(final ListAdapter adapter) {
        final ListaLivrosActivity thisActivity = this;

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                thisActivity.setListAdapter(adapter);
                ListView listView = thisActivity.getListView();

                for (int i = 0; i < listView.getCount(); i++) {
                    listView.setItemChecked(i, true);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lista_livros, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // por enquanto, só há um item a ser selecionado.
        renovaLivrosSelecionados();
        return true;
    }

    public void renovaLivrosSelecionados() {
        mProgressDialog = ProgressDialog.show(this, "", "Renovando livros...", true);

        SparseBooleanArray checkedItems = getListView().getCheckedItemPositions();
        List<String> registros = new ArrayList<String>();

        for (int i = 0; i < checkedItems.size(); i++) {
            if (checkedItems.valueAt(i)) {
                Registro registro = (Registro) getListAdapter().getItem(checkedItems.keyAt(i));
                registros.add(registro.getCodigo());
            }
        }

        new RenovaLivrosThread(this, registros, mHttpClient).start();
    }

    public void renovacaoConcluida(final String mensagem) {
        mProgressDialog.dismiss();

        final Activity thisActivity = this;
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(thisActivity).setTitle("Biblioteca FURB") //
                .setMessage(mensagem).setPositiveButton("OK", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = ListaLivrosActivity.this.getIntent();
                        ListaLivrosActivity.this.finish();
                        ListaLivrosActivity.this.startActivity(intent);
                    }
                }).show();
            }

        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHttpClient.getConnectionManager().shutdown();
    }
}

package com.borba.biblioteca_furb;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;

import com.borba.biblioteca_furb.networking.ResponseContent;

public class RenovacaoService {

    private static final String urlBase = "http://bu.furb.br/consulta/servicosUsuario/";
    private static final String urlRenovacao = urlBase + "servicos.php?tpServico=renovar&nrRegistro=%s";

    private final AbstractHttpClient mHttpClient;

    public RenovacaoService(AbstractHttpClient httpClient) {
        this.mHttpClient = httpClient;
    }

    public boolean renova(String registro) throws ClientProtocolException, IOException {
        HttpGet httpGet = new HttpGet(String.format(urlRenovacao, registro));
        HttpResponse response = mHttpClient.execute(httpGet);

        return !(new ResponseContent().getFullContent(response).contains("Obra possui reservas"));
    }
}

package com.borba.biblioteca_furb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;

import com.borba.biblioteca_furb.networking.ResponseContent;

public class RegistrosRepository {

    private static final String urlSituacao = "http://bu.furb.br/consulta/servicosUsuario/situacao_usuario.php";
    private static final String urlDados = "http://bu.furb.br/consulta/novaConsulta/recuperaMfn.php?NrRegistro=%s";

    private static final Pattern pattern1 = Pattern.compile("NrRegistro=(\\w*)\"");
    private static final Pattern pattern2 = Pattern.compile("<td>(\\d\\d/\\d\\d/\\d\\d\\d\\d \\d\\d:\\d\\d:\\d\\d)</td>");

    private final AbstractHttpClient mHttpClient;

    public RegistrosRepository(AbstractHttpClient httpClient) {
        this.mHttpClient = httpClient;
    }

    public List<String[]> buscaRegistros() throws Exception {
        HttpGet httpGet = new HttpGet(urlSituacao);
        HttpResponse response = mHttpClient.execute(httpGet);

        List<String> conteudo = new ResponseContent().getLines(response);

        //
        String registro = null, data = null;

        List<String[]> registros = new ArrayList<String[]>();
        for (String linha : conteudo) {
            if (registro == null) {
                Matcher matcher = pattern1.matcher(linha);
                if (matcher.find()) {
                    registro = matcher.group(1);
                }
            } else {
                Matcher matcher = pattern2.matcher(linha);
                if (matcher.find()) {
                    if (data == null) { // achou data inicial
                        data = matcher.group(1);
                    } else { // achou data final, reseta busca
                        data = matcher.group(1);

                        registros.add(new String[] { registro, data });

                        registro = data = null;
                    }
                }
            }
        }

        return registros;
    }

    public String buscaTitulo(String registro) throws ClientProtocolException, IOException {
        HttpGet httpGet = new HttpGet(String.format(urlDados, registro));
        HttpResponse response = mHttpClient.execute(httpGet);

        String conteudo = new ResponseContent().getFullContent(response);

        Matcher matcher = Pattern.compile("tulo</th><td>(.*?)</td>").matcher(conteudo);
        matcher.find();
        String titulo = matcher.group(1);

        return titulo;
    }

}

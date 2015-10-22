package com.borba.biblioteca_furb.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

public class ResponseContent {

    public String getFullContent(HttpResponse response) throws IllegalStateException, IOException {
        HttpEntity entity = response.getEntity();

        BufferedReader input = new BufferedReader(new InputStreamReader(entity.getContent(), "iso-8859-1"));

        StringBuilder sb = new StringBuilder(1024);
        String line;
        while ((line = input.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public List<String> getLines(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();

        BufferedReader input = new BufferedReader(new InputStreamReader(entity.getContent(), "iso-8859-1"));

        List<String> linhas = new ArrayList<String>();
        String linha;
        while ((linha = input.readLine()) != null) {
            linhas.add(linha);
        }
        return linhas;
    }
}

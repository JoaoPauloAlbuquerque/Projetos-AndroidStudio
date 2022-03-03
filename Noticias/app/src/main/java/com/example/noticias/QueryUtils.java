package com.example.noticias;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {

    public static ArrayList<Objeto> getArrayList(String stringUrl){
        Log.e("Status: ", "getArrayList()");
        URL url = getUrl(stringUrl);
        String json = "";

        if(url != null){
            try {
                json = getJson(url);
            } catch (IOException e){
                Log.e("Erro: ", "ao obter JSON "+e);
            }
        }

        ArrayList<Objeto> list = null;

        if(json != null || !json.isEmpty()){
            list = getArray(json);
        }

        return list;

    }

    private static URL getUrl(String stringUrl){
        Log.e("Status: ", "getUrl()");
        URL url;
        try{
            url = new URL(stringUrl);
            return url;
        } catch (MalformedURLException e) {
            Log.e("Erro: ", "ao obter URL " + e.getMessage());
        } catch (Exception ex){
            Log.e("Erro: ", "URL - " + ex.getMessage());
        }
        return null;
    }

    private static String getJson(URL stringUrl) throws IOException{
        Log.e("Status: ", "getJson()");
        String json = "";

        HttpURLConnection urlConnection = null;
        InputStream stream = null;

        try{
            urlConnection = (HttpURLConnection) stringUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            //pesquisar sobre
            urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:221.0) Gecko/20100101 Firefox/31.0");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            stream = urlConnection.getInputStream();
            json = getString(stream);
        } catch (IOException e) {
            Log.e("Erro: ", "ao abrir conexão " + urlConnection.getResponseCode());
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(stream != null){
                stream.close();
            }
        }

        return json;
    }

    private static String getString(InputStream stream) throws IOException {
        Log.e("Status: ", "getString()");
        StringBuilder json = new StringBuilder();

        if(stream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String linha = bufferedReader.readLine();
            while(linha != null){
                json.append(linha);
                linha = bufferedReader.readLine();
            }
        }
        return json.toString();
    }

    private static ArrayList<Objeto> getArray(String stringJson){
        Log.e("Status: ", "getArray()");
        ArrayList<Objeto> list = new ArrayList<>();

        try {
            JSONObject objeto = new JSONObject(stringJson);
            JSONArray array = objeto.getJSONArray("articles");
            Log.e("tamanho JSONArray: ", "" + array.length());
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                list.add(new Objeto()
                        .setTitulo(obj.getString("title"))
                        .setDesc(obj.getString("description"))
                );
            }
        } catch (JSONException e) {
            Log.e("Erro JSON: ", "" + e.getMessage());
        }
        return list;
    }
}

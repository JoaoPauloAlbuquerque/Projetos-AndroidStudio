package com.example.noticias;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
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
import java.sql.Connection;
import java.util.ArrayList;

public class QueryUtils {

    public static ArrayList<Objeto> lista = new ArrayList<>();

    public static ArrayList<Objeto> getArrayList(String stringUrl){
        Log.e("Status: ", "getArrayList()");
        lista.clear();
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

        lista = list;
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

        try {
            urlConnection = getConnection(stringUrl);
            stream = urlConnection.getInputStream();
            json = getString(stream);
        } catch (Exception e){
            Log.e("Erro: ", "ao abrir conexão - " + urlConnection.getResponseCode());
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

    private static HttpURLConnection getConnection(URL url) throws IOException {
        HttpURLConnection urlConnection = null;

        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        //pesquisar sobre
        urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:221.0) Gecko/20100101 Firefox/31.0");
        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(15000);
        urlConnection.connect();

        return urlConnection;
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
                        .setImg(getImg(obj.getString("urlToImage")))
                        .setTitulo(obj.getString("title"))
                        .setDesc(obj.getString("description"))
                );
            }
        } catch (JSONException e) {
            Log.e("Erro JSON: ", "" + e.getMessage());
        } catch (IOException e) {
            Log.e("Erro: ", "ao processar imagens - " + e.getMessage());
        }
        return list;
    }

    private static Bitmap getImg(String urlImg) throws IOException{
        URL url = getUrl(urlImg);
        Bitmap img = null;

        HttpURLConnection urlConnection = null;

        try{
            urlConnection = getConnection(url);
            img = restoreImg(urlConnection);
            Log.e("TAMANHO FINAL: ", "LARGURA - "+img.getWidth()+" | ALTURA - "+img.getHeight());
        } catch (IOException e) {
            Log.e("Erro: ", "ao obter imagem - "+urlConnection.getResponseCode());
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }

        return img;
    }

    private static Bitmap restoreImg(HttpURLConnection urlConnection) throws IOException{
        Bitmap img = BitmapFactory.decodeStream(urlConnection.getInputStream());
        img = getTamanhoBitmap(img, 200, 100);
        return img;
    }

    private static Bitmap getTamanhoBitmap(Bitmap img, int recLargura, int recAltura){
        int largura = img.getWidth();
        int altura = img.getHeight();
        int mult = 1;

        Log.e("TAMANHO INICIAL: ", "LARGURA - "+largura+" | ALTURA - "+altura);
        if(largura > recLargura || altura > recAltura){
            int metadeLargura = largura / 2;
            int metadeAltura = altura / 2;
            Log.e("TAMANHO MININO: ", "LARGURA - "+recLargura+" | ALTURA - "+recAltura);
            while((metadeLargura / mult) >= recLargura && (metadeAltura / mult) >= recAltura){
                mult *= 2;
                Log.e("DIVISOR DE TAMANHO: ", ""+mult);
            }
        }

        return Bitmap.createScaledBitmap(img, largura/mult, altura/mult, false);
    }
}
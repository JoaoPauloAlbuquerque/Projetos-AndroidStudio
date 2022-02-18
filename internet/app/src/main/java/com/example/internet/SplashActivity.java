package com.example.internet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Conexao conexao = new Conexao();
        conexao.execute();
    }

    public void ini(ArrayList<Objeto> list){
        Intent i = new Intent(this, MainActivity.class);
        i.putParcelableArrayListExtra("objetos", list);
        startActivity(i);
    }

    private class Conexao extends AsyncTask<URL, Void, ArrayList<Objeto>>{
        /**
         * constantes para separar o local do terremoto
         */
        public static final String VALOR_SEPARADOR = "Perto de";
        public static final String SEPARADOR_LOCAL = "of";
        /**
         * contante com a URL da API
         */
        public static final String URL_ = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson";

        private ArrayList<Objeto> list;

        /**
         * este método é chamado logo após o onPreExecute(),
         * é neste método que são executadas as ações demoradas da classe
         * @param urls
         * @return
         */
        @Override
        protected ArrayList<Objeto> doInBackground(URL... urls) {
            //essa String recebe o valor do JSON
            String json = "";
            //atribuindo o JSON na String
            try {
                json = getJson();
            }catch (IOException e){
                Log.e("Erro: ", "ao fechar conexão - " + e);
            }
            //artibuindo à lista o Array de Objetos
            list = getList(json);

            return list;
        }

        /**
         * este método é executado por ultimo na classe.
         * ele está sendo usado para poder popular o RecyclerView
         * com os valores do JSON
         * @param objetos - Arrays de Objetos que recebe do doInBackground()
         */
        @Override
        protected void onPostExecute(ArrayList<Objeto> objetos) {
            ini(objetos);
        }

        /**
         * este método retornar uma String com o JSON já configurado
         * primeiro ele cria a conexão com o servidor, em seguida configura o
         * JSON chamando o método getStringJson()
         * @return
         * @throws IOException
         */
        private String getJson() throws IOException {
            //criando a URL
            URL url = getUrl(URL_);
            //variáveis para poder configurar a conexão
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                //tipo de retorno "GET" para a API
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                //esta variável recebe um fluxo de bytes
                inputStream = urlConnection.getInputStream();
                //adicionando o JSON na String de retorno do método apartir do fluxo de bytes
                jsonResponse = getStringJson(inputStream);
            } catch (IOException e) {
                Log.e("Erro ao pegar conexão: ", ""+e);
            } finally {
                //finalizando as conexões
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // para executar isto, é preciso colocar uma exceção IOException
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * este método cria a URL
         * @param stringUrl
         * @return
         */
        private URL getUrl(String stringUrl){
            URL url = null;
            try{
                url = new URL(stringUrl);
            } catch (MalformedURLException e) {
                Log.e("Erro: ", "ao obter URL - " + e);
                return null;
            }
            return url;
        }

        /**
         * este método faz a leitura da string JSON da variável inputStream
         * @param inputStream
         * @return
         * @throws IOException
         */
        private String getStringJson(InputStream inputStream) throws IOException{
            //StringBuilder ajusta o próprio tamanho para poder caber o conteúdo da variável
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                //esta variável recebe o fluxo de bytes e decodifica em caracteres usando o charset
                //usado como uma ponte entre fluxos de bytes e fluxos de caracteres
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                //esta variável recebe o fluxo de bytes e amazena em buffer para que possa
                //ser feita uma leitura eficiente
                BufferedReader reader = new BufferedReader(inputStreamReader);
                //aqui estou lendo a primeira linha do buffer e colocando na String "line"
                String line = reader.readLine();
                //este loop ler todas as linhas do BufferedReader e concatena na StringBuilder
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        /**
         * este método recebe uma String contendo o JSON e faz
         * todas as buscas necessárias e organiza os resultados em um ArrayList<Objetos>,
         * e retorna esse Array para o método doInBackground()
         * @param json
         * @return
         */
        private ArrayList<Objeto> getList(String json){
            //Array de retorno
            ArrayList<Objeto> lista = new ArrayList<>();
            try {
                //cria um JSONObject com a String JSON informada
                JSONObject jObj = new JSONObject(json);
                //seleciona um Array de JSON dentro do JSONObject com a tag "features"
                JSONArray jArray = jObj.getJSONArray("features");
                //inicia o loop para percorrer o JSONArray
                for (int i = 0; i < jArray.length(); i++) {
                    //seleciona um JSONObject dentro do Array de JSON com a tag "properties"
                    JSONObject obj = jArray.getJSONObject(i).getJSONObject("properties");
                    //seleciona e organiza todos os valores dentro do ArrayList<Objeto>
                    //esse ArrayList<Objeto> vai popular o RecyclerView
                    double mag = obj.getDouble("mag");
                    //seleciono apenas os terremos com magnitude maiores ou iguais á 3.0
                    if(mag>= 3.0f) {
                        lista.add(new Objeto()
                                .setMagnitude(mag)
                                .setLocalPrimario(getLocal(obj.getString("place"))[0])
                                .setLocalSecundario(getLocal(obj.getString("place"))[1])
                                .setData(new SimpleDateFormat("dd/MM/yyyy").format(new Date(obj.getLong("time"))))
                                .setHora(new SimpleDateFormat("h:mm a").format(new Date(obj.getLong("time"))))
                                .setUrl(obj.getString("url")));
                    }
                }
            } catch (JSONException e) {
                Log.e("ERRO JSON: ", ""+e.getMessage());
            }

            return lista;
        }

        /**
         * este método separa o local em duas String
         * @param s - recebe uma String com o local inteiro, para poder dividi-lo em dois
         * @return - tem como retorno um Array de String de tamanho 2
         */
        private String[] getLocal(String s){

            String localPrimario = "";
            String localSecundario = "";

            String[] local = new String[2];

            //se a String do local contiver a Strind determinada como separador,
            //será divida em duas string.
            //caso não, será atribuido ao local primário um valor pré-determinado pela constante,
            //e para o local secundário, será atribuido o local completo.
            if(s.contains(SEPARADOR_LOCAL)){
                localPrimario = s.split(SEPARADOR_LOCAL)[0] + SEPARADOR_LOCAL;
                localSecundario = s.split(SEPARADOR_LOCAL)[1];
            } else {
                localPrimario = VALOR_SEPARADOR;
                localSecundario = s;
            }

            local[0] = localPrimario;
            local[1] = localSecundario;

            return local;
        }
    }
}
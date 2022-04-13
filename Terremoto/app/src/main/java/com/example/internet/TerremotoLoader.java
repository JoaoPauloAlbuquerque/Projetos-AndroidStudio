package com.example.internet;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class TerremotoLoader extends AsyncTaskLoader<ArrayList<Objeto>> {

    private String url;
    public TerremotoLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    /**
     * é recomendado que sempre no método onStartLoading(),
     * chamar o método forceLoad()
     */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Objeto> loadInBackground() {
        if(url == null){
            return null;
        }

        ArrayList<Objeto> list = QueryUtils.getArrayList(url);
        return list;
    }
}

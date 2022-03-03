package com.example.noticias;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.AsyncTaskLoader;

import java.util.ArrayList;

public class NoticiasLoader extends AsyncTaskLoader<ArrayList<Objeto>> {

    public NoticiasLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        Log.e("Status: ", "onStartLoading()");
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Objeto> loadInBackground() {
        Log.e("Status: ", "loadInBackground()");
        ArrayList<Objeto> list = QueryUtils.getArrayList(SplashActivity.URL);

        return list;
    }
}

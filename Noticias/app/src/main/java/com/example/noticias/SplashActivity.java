package com.example.noticias;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Objeto>> {

    public static final String URL = "https://newsapi.org/v2/everything?q=ucrania&language=pt&apiKey=2937df1d4f9e4194ad42591cf6179db9";

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progress = new ProgressDialog(this);
        progress.show();

        LoaderManager loaderManager = this.getLoaderManager();
        loaderManager.initLoader(1, null, this);

    }

    private void iniMainActivity(ArrayList<Objeto> list){
        Intent i = new Intent(this, MainActivity.class);
        i.putParcelableArrayListExtra("objeto", list);
        startActivity(i);
        this.finish();
    }

    @Override
    public Loader<ArrayList<Objeto>> onCreateLoader(int id, Bundle args) {
        Log.e("Status: ", "onCreateLoader()");
        return new NoticiasLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Objeto>> loader, ArrayList<Objeto> data) {
        progress.dismiss();
        if(data != null){
            iniMainActivity(data);
            return;
        }
        Log.e("Erro: ", "Valor DATA null");
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Objeto>> loader) {

    }
}
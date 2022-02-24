package com.example.internet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Objeto>>{

    private static final int TERREMOTO_LOADER_ID = 1;
    private static final String URL_ = "https://earthquake.usgs.gov/fdsnws/event/1/query?minmag=5&format=geojson";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LoaderManager loaderManager = this.getLoaderManager();
        loaderManager.initLoader(TERREMOTO_LOADER_ID, null, this);
    }

    public void ini(ArrayList<Objeto> list) {
        Intent i = new Intent(this, MainActivity.class);
        i.putParcelableArrayListExtra("objetos", list);
        startActivity(i);
        this.finish();
    }

    @Override
    public Loader<ArrayList<Objeto>> onCreateLoader(int id, Bundle args) {
        Log.e("passou: ", "onCreateLoader()");
        return new TerremotoLoader(this, URL_);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Objeto>> loader, ArrayList<Objeto> data) {
        ini(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Objeto>> loader) {}
}
package com.example.internet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Objeto>>{

    private static final int TERREMOTO_LOADER_ID = 1;
    private static final String URL_ = "https://earthquake.usgs.gov/fdsnws/event/1/query";

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Carregando dados");
        pDialog.show();

        LoaderManager loaderManager = this.getLoaderManager();
        loaderManager.initLoader(TERREMOTO_LOADER_ID, null, this);
    }

    private void iniMainActivity(ArrayList<Objeto> list) {
        Intent i = new Intent(this, MainActivity.class);
        i.putParcelableArrayListExtra("objetos", list);
        startActivity(i);
        this.finish();
    }

    private void iniNotConnection(){
        Intent i = new Intent(this, NotConnection.class);
        startActivity(i);
        this.finish();
    }

    @Override
    public Loader<ArrayList<Objeto>> onCreateLoader(int id, Bundle args) {
        Log.e("passou: ", "onCreateLoader()");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String minMag = sharedPreferences.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default)
        );

        String orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(URL_);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("minmag", minMag);
        uriBuilder.appendQueryParameter("orderby", orderBy);
        uriBuilder.appendQueryParameter("limit", "100");

        return new TerremotoLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Objeto>> loader, ArrayList<Objeto> data) {
        Log.e("passou: ", "onLoaderFinished()");
        pDialog.dismiss();
        if(data == null || data.size() == 0){
            iniNotConnection();
            return;
        }
        iniMainActivity(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Objeto>> loader) {
        Log.e("passou: ", "onLoaderReset()");
    }
}
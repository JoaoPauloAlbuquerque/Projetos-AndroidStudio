package com.example.internet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

public class NotConnection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_connection);

        Snackbar.make(findViewById(R.id.not_connection), "Erro ao processar dados", 5000).show();
    }
}
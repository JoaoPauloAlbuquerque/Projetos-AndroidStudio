package com.example.calculadora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import java.nio.file.Files;

public class MainActivity extends AppCompatActivity {

    AppCompatButton botaoPrimeiroGrau, botaoSegundoGrau, botaoCalculadora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniCoomponentes();
    }

    private void iniCoomponentes() {
        botaoPrimeiroGrau = findViewById(R.id.botao_primeiro_grau);
        botaoSegundoGrau = findViewById(R.id.botao_segundo_grau);
        botaoCalculadora = findViewById(R.id.botao_calculadora);
    }

    public void abrirPrimeiroGrau(View v) {
        Intent i = new Intent(MainActivity.this, PrimeiroGrauActivity.class);
        startActivity(i);
    }

    public void abrirSegundoGrau(View v) {
        Intent i = new Intent(MainActivity.this, SegundoGrauActivity.class);
        startActivity(i);
    }

    public void abrirCalculadora(View v) {
        Intent i = new Intent(MainActivity.this, CalculadoraActivity.class);
        startActivity(i);
    }

}
package com.example.controle;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.controle.dados.DadosContract.*;
import com.example.controle.dados.DbHelp;

public class AddDadosCartaoActivity extends AppCompatActivity {

    private EditText editDiaPagamento;
    private EditText editDiaFechamento;
    private Button btnOk;

    DbHelp help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dados_cartao);

        iniComponents();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = help.getWritableDatabase();
                int diaPagamento = Integer.parseInt(editDiaPagamento.getText().toString());
                int diaFechamento = Integer.parseInt(editDiaFechamento.getText().toString());

                //CRIAR O MÉTODO UPDATE

            }
        });
        SQLiteDatabase db = help.getReadableDatabase();

        String[] colunas = {DadosCartaoEntry.COLUNA_DIA_PAGAME,
                DadosCartaoEntry.COLUNA_DIA_FECHAME
        };

        Cursor cursor = db.query(DadosCartaoEntry.NOME_TABELA,
                colunas,
                null,
                null,
                null,
                null,
                null
        );

        try {
            if (cursor.getCount() != 0) {
                updateUIDadosCartao(cursor);
            }
        } catch (Exception e){

        } finally {
            cursor.close();
        }
    }

    private void iniComponents(){
        editDiaPagamento = findViewById(R.id.add_dados_cartao_edit_dia_pagamento);
        editDiaFechamento = findViewById(R.id.add_dados_cartao_edit_dia_fechamento);
        btnOk = findViewById(R.id.add_dados_cartao_button_ok);
        help = new DbHelp(this);
    }

    private void updateUIDadosCartao(Cursor cursor) throws Exception{
        int idColunaDiaPagamento = cursor.getColumnIndex(DadosCartaoEntry.COLUNA_DIA_PAGAME);
        int idColunaDiaFechamento = cursor.getColumnIndex(DadosCartaoEntry.COLUNA_DIA_FECHAME);
        cursor.moveToPosition(0);
        int diaPagamento = cursor.getInt(idColunaDiaPagamento);
        int diaFechamento = cursor.getInt(idColunaDiaFechamento);
        editDiaPagamento.setText(diaPagamento);
        editDiaFechamento.setText(diaFechamento);
    }

}
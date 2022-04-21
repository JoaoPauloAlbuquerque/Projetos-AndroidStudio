package com.example.controle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controle.dados.DbHelp;

import java.util.Calendar;

import com.example.controle.dados.DadosContract.*;
import com.example.controle.objetos.Objeto;

public class AddCompraActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText descricao;
    private EditText valorCompra;
    private EditText quantParcelas;
    private ImageButton buttonData;
    private TextView data;

    private Button buttonOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_compra);
        this.iniComponent();
        buttonData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddCompraActivity.this,
                        AddCompraActivity.this,
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelp dbHelp = new DbHelp(AddCompraActivity.this);
                SQLiteDatabase db = dbHelp.getWritableDatabase();
                ContentValues values = new ContentValues();

                Objeto obj = new Objeto().setDescricao(descricao.getText().toString())
                        .setValorCompra(Double.parseDouble(valorCompra.getText().toString()))
                        .setQuantParcelas(Integer.parseInt(quantParcelas.getText().toString()))
                        .setData(data.getText().toString());

                values.put(DadosCompraEntry.COLUNA_DESCRICAO, obj.getDescricao());
                values.put(DadosCompraEntry.COLUNA_VALOR_COMPRA, String.valueOf(obj.getValorCompra()));
                values.put(DadosCompraEntry.COLUNA_PARCELAS, String.valueOf(obj.getQuantParcelas()));
                values.put(DadosCompraEntry.COLUNA_DATA_COMPRA, obj.getData());

                long newRowId = db.insert(DadosCompraEntry.NOME_TABELA, null, values);
                if(newRowId != -1){
                    Toast.makeText(AddCompraActivity.this, "Linha " + newRowId + " inserida com sucesso", Toast.LENGTH_SHORT).show();
                    MainActivity.updateUi(obj);
                } else {
                    Toast.makeText(AddCompraActivity.this, "Erro ao inserir dados", Toast.LENGTH_SHORT).show();
                }
                AddCompraActivity.this.finish();
            }
        });
    }

    private void iniComponent(){
        descricao = findViewById(R.id.add_compra_edit_onde_comprou);
        valorCompra = findViewById(R.id.add_compra_edit_quanto_pagou);
        quantParcelas = findViewById(R.id.add_compra_edit_quanti_parcelas);
        buttonData = findViewById(R.id.add_compra_button_calendar);
        data = findViewById(R.id.add_compra_data);
        buttonOk = findViewById(R.id.add_compra_button_ok);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String dat = dayOfMonth + "/" + (month + 1) + "/" + year;
        data.setText(dat);
    }
}
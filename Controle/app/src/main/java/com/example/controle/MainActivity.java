package com.example.controle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controle.adapter.MyAdapter;
import com.example.controle.dados.DbHelp;
import com.example.controle.objetos.Objeto;
import com.example.controle.utils.CalcUtils;

import java.util.ArrayList;
import java.util.List;

import com.example.controle.dados.DadosContract.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private static MyAdapter myAdapter;
    private List<Objeto> list;
    private static TextView valorTotalGasto;
    private DbHelp mDbHelp;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onStart() {
        super.onStart();
        DbHelp help = new DbHelp(this);
        SQLiteDatabase db = help.getReadableDatabase();

        while(true) {
            Cursor cursor = db.query(DadosCartaoEntry.NOME_TABELA,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "ZERO", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this, AddDadosCartaoActivity.class);
                startActivity(i);
            } else {
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.iniComponents();
        this.configureElementosIniciais();

        floatingActionButton.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AddCompraActivity.class);
            startActivity(i);
        });

    }

    public static void updateUi(Objeto obj){
        myAdapter.list.add(0, obj);
        myAdapter.notifyItemInserted(0);
        valorTotalGasto.setText(String.valueOf(CalcUtils.calcularTotalGasto(myAdapter.list)));
    }

    private void iniComponents(){
        this.recyclerView = findViewById(R.id.recycler_view);
        this.valorTotalGasto = findViewById(R.id.valor_total);
        floatingActionButton = findViewById(R.id.floating_action_button);

        this.mDbHelp = new DbHelp(this);
        this.list = this.mostrarInfo();
        this.myAdapter = new MyAdapter(this.list);
    }

    private void configureElementosIniciais(){
        this.recyclerView.setAdapter(myAdapter);
        this.valorTotalGasto.setText(String.valueOf(CalcUtils.calcularTotalGasto(this.list)));
    }

    @NonNull
    private List<Objeto> mostrarInfo(){
        SQLiteDatabase db = mDbHelp.getReadableDatabase();
        List<Objeto> list = new ArrayList<>();

        String[] colunas = {DadosCompraEntry.COLUNA_DESCRICAO,
                DadosCompraEntry.COLUNA_DATA_COMPRA,
                DadosCompraEntry.COLUNA_VALOR_COMPRA,
                DadosCompraEntry.COLUNA_PARCELAS
        };

        Cursor cursor = db.query(DadosCompraEntry.NOME_TABELA,
                colunas,
                null,
                null,
                null,
                null,
                DadosCompraEntry._ID + " DESC"
        );
        try{
            list = this.getListTableDadosCompra(cursor);
        } catch (Exception e){
            Toast.makeText(this, "Erro ao buscar dados", Toast.LENGTH_SHORT).show();
        } finally {
            cursor.close();
        }
        return list;
    }

    private List<Objeto> getListTableDadosCompra(Cursor cursor) throws Exception{
        List<Objeto> list = new ArrayList<>();
        int idDescricao = cursor.getColumnIndex(DadosCompraEntry.COLUNA_DESCRICAO);
        int idDadaCompra = cursor.getColumnIndex(DadosCompraEntry.COLUNA_DATA_COMPRA);
        int idValorCompra = cursor.getColumnIndex(DadosCompraEntry.COLUNA_VALOR_COMPRA);
        int idParcelas = cursor.getColumnIndex(DadosCompraEntry.COLUNA_PARCELAS);
        while(cursor.moveToNext()){
            list.add(new Objeto().setDescricao(cursor.getString(idDescricao))
                    .setData(cursor.getString(idDadaCompra))
                    .setValorCompra(Double.parseDouble(cursor.getString(idValorCompra)))
                    .setQuantParcelas(cursor.getInt(idParcelas))
            );
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_config) {
            Toast.makeText(this, "configuração", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
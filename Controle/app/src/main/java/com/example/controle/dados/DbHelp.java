package com.example.controle.dados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.controle.dados.DadosContract.*;

public class DbHelp extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "controle.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelp(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE_DADOS_CARTAO = "CREATE TABLE " + DadosCartaoEntry.NOME_TABELA + " (" +
                DadosCartaoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DadosCartaoEntry.COLUNA_DIA_PAGAME + " INTEGER NOT NULL DEFAULT 30," +
                DadosCartaoEntry.COLUNA_DIA_FECHAME + " INTEGER NOT NULL DEFAULT 20);";

        String SQL_CREATE_TABLE_DADOS_COMPRA = "CREATE TABLE " + DadosCompraEntry.NOME_TABELA + " (" +
                DadosCompraEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DadosCompraEntry.COLUNA_DESCRICAO + " TEXT," +
                DadosCompraEntry.COLUNA_DATA_COMPRA + " TEXT," +
                DadosCompraEntry.COLUNA_VALOR_COMPRA + " TEXT," +
                DadosCompraEntry.COLUNA_PARCELAS + " INTEGER);";

        String SQL_CREATE_TABLE_DADOS_DEVENDO = "CREATE TABLE " + DadosDevendoEntry.NOME_TABELA + " (" +
                DadosDevendoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DadosDevendoEntry.COLUNA_MES + " TEXT," +
                DadosDevendoEntry.COLUNA_VALOR_TOTAL + " TEXT," +
                DadosDevendoEntry.COLUNA_VALOR_MES_ATUAL + " TEXT);";

        db.execSQL(SQL_CREATE_TABLE_DADOS_CARTAO);
        db.execSQL(SQL_CREATE_TABLE_DADOS_COMPRA);
        db.execSQL(SQL_CREATE_TABLE_DADOS_DEVENDO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

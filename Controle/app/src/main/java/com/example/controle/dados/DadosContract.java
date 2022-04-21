package com.example.controle.dados;

import android.provider.BaseColumns;

public class DadosContract {

    public static final class DadosCartaoEntry implements BaseColumns{
        public static final String NOME_TABELA = "dados_cartao";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUNA_DIA_PAGAME = "dia_pagame";
        public static final String COLUNA_DIA_FECHAME = "dia_fechame";

    }

    public static final class DadosCompraEntry implements BaseColumns{
        public static final String NOME_TABELA = "dados_compra";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUNA_DESCRICAO = "descricao";
        public static final String COLUNA_DATA_COMPRA = "data_compra";
        public static final String COLUNA_VALOR_COMPRA = "valor_compra";
        public static final String COLUNA_PARCELAS = "parcelas";
    }

    public static final class DadosDevendoEntry implements BaseColumns{
        public static final String NOME_TABELA = "dados_devendo";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUNA_MES = "mes";
        public static final String COLUNA_VALOR_TOTAL = "valor_total";
        public static final String COLUNA_VALOR_MES_ATUAL = "valor_mes_atual";
    }

}

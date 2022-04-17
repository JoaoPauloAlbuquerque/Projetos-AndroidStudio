package com.example.controle.objetos;

public class Objeto {

    private String descricao;
    private String data;
    private double valorCompra;
    private int quantParcelas;

    public Objeto(String descricao, String data, double valorCompra, int quantParcelas) {
        this.descricao = descricao;
        this.data = data;
        this.valorCompra = valorCompra;
        this.quantParcelas = quantParcelas;
    }

    public Objeto() {

    }

    public String getDescricao() {
        return descricao;
    }

    public Objeto setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public String getData() {
        return data;
    }

    public Objeto setData(String data) {
        this.data = data;
        return this;
    }

    public double getValorCompra() {
        return valorCompra;
    }

    public Objeto setValorCompra(double valorCompra) {
        this.valorCompra = valorCompra;
        return this;
    }

    public int getQuantParcelas() {
        return quantParcelas;
    }

    public Objeto setQuantParcelas(int quantParcelas) {
        this.quantParcelas = quantParcelas;
        return this;
    }
}

package com.example.estudo.inetrnet.objetos;

public class ObjetoTerremoto {

    private String magnitude;
    private String localidade;
    private String data;

    public ObjetoTerremoto(String magnitude, String localidade, String data) {
        this.magnitude = magnitude;
        this.localidade = localidade;
        this.data = data;
    }

    public ObjetoTerremoto(){}

    public String getMagnitude() {
        return magnitude;
    }

    public ObjetoTerremoto setMagnitude(String magnitude) {
        this.magnitude = magnitude;
        return this;
    }

    public String getLocalidade() {
        return localidade;
    }

    public ObjetoTerremoto setLocalidade(String localidade) {
        this.localidade = localidade;
        return this;
    }

    public String getData() {
        return data;
    }

    public ObjetoTerremoto setData(String data) {
        this.data = data;
        return this;
    }
}

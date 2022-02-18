package com.example.calculadora.objetos;

public class ObjetoPrimeiroGrau {

    private double a, b, c;

    private double x;

    private double resultadoPrimeiraParte;

    public ObjetoPrimeiroGrau(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void calcular(){
        resultadoPrimeiraParte = c - b;
        x = resultadoPrimeiraParte / a;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getX() {
        return x;
    }

    public double getResultadoPrimeiraParte() {
        return resultadoPrimeiraParte;
    }
}

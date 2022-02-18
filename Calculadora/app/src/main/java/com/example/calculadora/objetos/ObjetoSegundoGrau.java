package com.example.calculadora.objetos;

import android.util.Log;

public class ObjetoSegundoGrau {

    private double a;
    private double b;
    private double c;

    private String msg;

    private double delta;

    private double resultadoContaPrimeiraParteDelta;
    private double resultadoContaSegundaParteDelta;

    private double x1;
    private double x2;

    private double resultadoX1Cima;
    private double resultadoX1Baixo;
    private double resultadoX2Cima;
    private double resultadoX2Baixo;


    public ObjetoSegundoGrau(double a, double b, double c) {

        this.a = a;
        this.b = b;
        this.c = c;

    }

    public double calcularDelta() {
        resultadoContaPrimeiraParteDelta = b * b;
        resultadoContaSegundaParteDelta = 4 * a * c;
        delta = resultadoContaPrimeiraParteDelta - resultadoContaSegundaParteDelta;
        return delta;
    }

    public int calcularRaizes(double d) {

        if (d < 0) {
            msg = "Para delta negativo igual a (" + String.format("%.2f", d) + ") não existe raiz real.";
            return -1;
        } else if (d == 0) {
            msg = "Para delta igual a zero, as duas raizes são iguais.";
            resultadoX1Cima = (-b) + (Math.sqrt(d));
            Log.e("ERRO AO CALCULAR", "B=" + b + " D=" + d + " RESULT=" + resultadoX1Cima);
            resultadoX1Baixo = 2 * a;
            Log.e("ERRO AO CALCULAR", "A=" + a + " RESULT=" + resultadoX1Baixo);

            x1 = resultadoX1Cima / resultadoX1Baixo;
            return 0;
        } else {
            resultadoX1Cima = (-b) + (Math.sqrt(d));
            Log.e("ERRO AO CALCULAR", "B=" + b + " D=" + d + " RESULT=" + resultadoX1Cima);
            resultadoX1Baixo = 2 * a;
            Log.e("ERRO AO CALCULAR", "A=" + a + " RESULT=" + resultadoX1Baixo);

            resultadoX2Cima = -b - Math.sqrt(d);
            resultadoX2Baixo = 2 * a;

            x1 = resultadoX1Cima / resultadoX1Baixo;
            x2 = resultadoX2Cima / resultadoX2Baixo;
            return 1;
        }
    }

    public String getMsg() {
        return msg;
    }

    public double getResultadoX1Cima() {
        return resultadoX1Cima;
    }

    public double getResultadoX1Baixo() {
        return resultadoX1Baixo;
    }

    public double getResultadoX2Cima() {
        return resultadoX2Cima;
    }

    public double getResultadoX2Baixo() {
        return resultadoX2Baixo;
    }

    public double getRetultadoContaPrimeiraParteDelta() {
        return resultadoContaPrimeiraParteDelta;
    }

    public double getRetultadoContaSegundaParteDelta() {
        return resultadoContaSegundaParteDelta;
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

    public double getDelta() {
        return delta;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }
}

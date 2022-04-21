package com.example.controle.utils;

import com.example.controle.objetos.Objeto;

import java.util.List;

public class CalcUtils {

    public static double calcularTotalGasto(List<Objeto> list){
        double total = 0;
        for(Objeto obj : list){
            total += obj.getValorCompra();
        }
        return total;
    }

}

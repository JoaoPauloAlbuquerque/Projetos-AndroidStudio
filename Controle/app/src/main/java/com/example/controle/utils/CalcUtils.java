package com.example.controle.utils;

import com.example.controle.objetos.Objeto;

import java.text.DecimalFormat;
import java.util.List;

public class CalcUtils {

    public static double calcularTotalGasto(List<Objeto> list){
        double total = 0;
        for(Objeto obj : list){
            total += obj.getValorCompra();
        }
        return total;
    }
    
    public static String doubleFormat(double n){
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(n);
    }

}

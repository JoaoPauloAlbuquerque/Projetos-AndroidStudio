package com.example.estudo.model;

import com.example.estudo.R;

import java.util.Arrays;
import java.util.List;

public class Objeto {

    private String txt;
    private int img;

    public String getTxt() {
        return txt;
    }

    public int getImg() {
        return img;
    }

    public Objeto setTxt(String txt) {
        this.txt = txt;
        return this;
    }

    public Objeto setImg(int img) {
        this.img = img;
        return this;
    }

    public static class CriarObjetos{

        public static List<Objeto> criar(){
            return Arrays.asList(
                    new Objeto()
                            .setTxt("Persistência")
                            .setImg(R.drawable.logo_),

                    new Objeto()
                            .setTxt("Mateus")
                            .setImg(R.drawable.logo_),

                    new Objeto()
                            .setTxt("João Paulo")
                            .setImg(R.drawable.logo_),

                    new Objeto()
                            .setTxt("Empresa")
                            .setImg(R.drawable.logo_),

                    new Objeto()
                            .setTxt("Tecnologia")
                            .setImg(R.drawable.logo_),

                    new Objeto()
                            .setTxt("Referência")
                            .setImg(R.drawable.logo_),

                    new Objeto()
                            .setTxt("Atualidade")
                            .setImg(R.drawable.logo_),

                    new Objeto()
                            .setTxt("Meta")
                            .setImg(R.drawable.logo_),

                    new Objeto()
                            .setTxt("Alcance")
                            .setImg(R.drawable.logo_),

                    new Objeto()
                            .setTxt("Perceria")
                            .setImg(R.drawable.logo_),

                    new Objeto()
                            .setTxt("Referência")
                            .setImg(R.drawable.logo_),

                    new Objeto()
                            .setTxt("Desenvolvimento")
                            .setImg(R.drawable.logo_),

                    new Objeto()
                            .setTxt("Organização")
                            .setImg(R.drawable.logo_),

                    new Objeto()
                            .setTxt("Estrutura")
                            .setImg(R.drawable.logo_),

                    new Objeto()
                            .setTxt("Solução")
                            .setImg(R.drawable.logo_),

                    new Objeto()
                            .setTxt("Velocidade")
                            .setImg(R.drawable.logo_)
            );
        }

    }

}

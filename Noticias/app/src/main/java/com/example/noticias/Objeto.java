package com.example.noticias;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Objeto {

    private String titulo;
    private String desc;
    private Bitmap img;

    public Objeto(){}

    public String getTitulo() {
        return titulo;
    }

    public Objeto setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Objeto setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public Bitmap getImg() {
        return img;
    }

    public Objeto setImg(Bitmap img) {
        this.img = img;
        return this;
    }
}

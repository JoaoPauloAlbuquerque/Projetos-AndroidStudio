package com.example.noticias;

import android.os.Parcel;
import android.os.Parcelable;

public class Objeto implements Parcelable {

    private String titulo;
    private String desc;
    private int img;

    protected Objeto(Parcel in) {
        titulo = in.readString();
        desc = in.readString();
        img = in.readInt();
    }
    public Objeto(){}

    public static final Creator<Objeto> CREATOR = new Creator<Objeto>() {
        @Override
        public Objeto createFromParcel(Parcel in) {
            return new Objeto(in);
        }

        @Override
        public Objeto[] newArray(int size) {
            return new Objeto[size];
        }
    };

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

    public int getImg() {
        return img;
    }

    public Objeto setImg(int img) {
        this.img = img;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(desc);
        dest.writeInt(img);
    }
}

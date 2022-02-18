package com.example.internet;

import android.os.Parcel;
import android.os.Parcelable;

public class Objeto implements Parcelable {

    private double magnitude;
    private String localPrimario;
    private String localSecundario;
    private String data;
    private String hora;
    private String url;

    public Objeto() {
    }

    protected Objeto(Parcel in) {
        magnitude = in.readDouble();
        localPrimario = in.readString();
        localSecundario = in.readString();
        data = in.readString();
        hora = in.readString();
        url = in.readString();
    }

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

    public double getMagnitude() {
        return magnitude;
    }

    public Objeto setMagnitude(double magnitude) {
        this.magnitude = magnitude;
        return this;
    }

    public String getLocalPrimario () {
        return localPrimario;
    }

    public Objeto setLocalPrimario(String localPrimario) {
        this.localPrimario = localPrimario;
        return this;
    }

    public String getLocalSecundario() {
        return localSecundario;
    }

    public Objeto setLocalSecundario(String localSecundario) {
        this.localSecundario = localSecundario;
        return this;
    }

    public String getData() {
        return data;
    }

    public Objeto setData(String data) {
        this.data = data;
        return this;
    }

    public String getHora() {
        return hora;
    }

    public Objeto setHora(String hora){
        this.hora = hora;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Objeto setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(magnitude);
        dest.writeString(localPrimario);
        dest.writeString(localSecundario);
        dest.writeString(data);
        dest.writeString(hora);
        dest.writeString(url);
    }
}

package com.example.internet;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    private ArrayList<Objeto> list;
    private Context context;

    public Adapter (ArrayList<Objeto> list, Context context){
        this.list = list;
        this.context = context;
    }

    private int getMagnitudeColor(double magnitude){
        int color;
        int colorMagnitude = (int) magnitude;
        switch(colorMagnitude){
            case 0:
            case 1:
                color = R.color.magnitude1;
                break;
            case 2:
                color = R.color.magnitude2;
                break;
            case 3:
                color = R.color.magnitude3;
                break;
            case 4:
                color = R.color.magnitude4;
                break;
            case 5:
                color = R.color.magnitude5;
                break;
            case 6:
                color = R.color.magnitude6;
                break;
            case 7:
                color = R.color.magnitude7;
                break;
            case 8:
                color = R.color.magnitude8;
                break;
            case 9:
                color = R.color.magnitude9;
                break;
            default:
                color = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(this.context, color);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.construir(list.get(position));
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView magnitude;
        private TextView localPrimario;
        private TextView localSecundario;
        private TextView data;
        private TextView hora;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            magnitude = itemView.findViewById(R.id.magnitude);
            localPrimario = itemView.findViewById(R.id.local_primario);
            localSecundario = itemView.findViewById(R.id.local_secundario);
            data = itemView.findViewById(R.id.data);
            hora = itemView.findViewById(R.id.hora);

            itemView.setOnClickListener(v -> {
                String url = list.get(getAdapterPosition()).getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                v.getContext().startActivity(i);
            });

        }

        public void construir(Objeto obj){
            magnitude.setText(formatDecimal(obj.getMagnitude()));
            setColorMagnitude(magnitude, obj);
            localPrimario.setText(obj.getLocalPrimario());
            localSecundario.setText(obj.getLocalSecundario());
            data.setText(obj.getData());
            hora.setText(obj.getHora());
        }

        private void setColorMagnitude(TextView v, Objeto obj){
            GradientDrawable pintar = (GradientDrawable) v.getBackground();
            int cor = getMagnitudeColor(obj.getMagnitude());
            pintar.setColor(cor);
        }

        private String formatDecimal(double s){
            DecimalFormat format = new DecimalFormat("0.0");
            return format.format(s);
        }
    }

}

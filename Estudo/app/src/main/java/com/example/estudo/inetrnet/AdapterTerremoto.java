package com.example.estudo.inetrnet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estudo.R;
import com.example.estudo.inetrnet.objetos.ObjetoTerremoto;

import java.util.ArrayList;

public class AdapterTerremoto extends RecyclerView.Adapter<AdapterTerremoto.MyViewHolder>{

    private ArrayList<ObjetoTerremoto> list;

    public AdapterTerremoto(ArrayList<ObjetoTerremoto> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_terremoto, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.biulder(list, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView magnitude;
        private TextView localidade;
        private TextView data;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            magnitude = (TextView) itemView.findViewById(R.id.magnitude);
            localidade = (TextView) itemView.findViewById(R.id.locale);
            data = (TextView) itemView.findViewById(R.id.data_terremoto);
        }

        public void biulder(ArrayList<ObjetoTerremoto> liste, int position){
            magnitude.setText(String.valueOf(liste.get(position).getMagnitude()));
            localidade.setText(liste.get(position).getLocalidade());
            data.setText(liste.get(position).getData());
        }
    }
}

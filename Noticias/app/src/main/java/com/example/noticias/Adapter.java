package com.example.noticias;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private ArrayList<Objeto> list;

    public Adapter(ArrayList<Objeto> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_noticias, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.construir(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView titulo;
        TextView desc;

        public MyViewHolder(@NonNull View item) {
            super(item);

            img = item.findViewById(R.id.img);
            titulo = item.findViewById(R.id.titulo);
            desc = item.findViewById(R.id.descricao);

        }

        public void construir(Objeto obj){
            img.setImageResource(obj.getImg());
            titulo.setText(obj.getTitulo());
            desc.setText(obj.getDesc());
        }
    }

}

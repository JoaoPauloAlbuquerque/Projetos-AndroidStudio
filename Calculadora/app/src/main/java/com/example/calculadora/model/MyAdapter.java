package com.example.calculadora.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.calculadora.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    ArrayList<String> lista;

    public MyAdapter(ArrayList<String> lista){
        this.lista = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.construir(lista.get(position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.recyclerview_item);
        }

        public void construir(String s){
            item.setText(s);
        }
    }

}

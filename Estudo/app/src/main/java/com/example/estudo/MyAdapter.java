package com.example.estudo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estudo.model.Objeto;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private List<Objeto> itens;

    public MyAdapter (List<Objeto> itens){
        this.itens = itens;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Objeto obj = itens.get(position);
        holder.construir(obj);
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txt;
        private ImageView img;

        public MyViewHolder(@NonNull View v) {
            super(v);

            txt = (TextView) v.findViewById(R.id.text_recycler_item);
            img = (ImageView) v.findViewById(R.id.photo);

        }

        public void construir(Objeto obj){
            txt.setText(obj.getTxt());
            img.setImageResource(obj.getImg());
        }
    }
}

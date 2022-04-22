package com.example.controle.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controle.R;
import com.example.controle.objetos.Objeto;
import com.example.controle.utils.CalcUtils;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    public List<Objeto> list;

    public MyAdapter(List<Objeto> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_page_home, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.biulder(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView onde;
        private TextView data;
        private TextView valor;
        private TextView quantParcela;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            onde = itemView.findViewById(R.id.recyclerview_item_onde);
            data = itemView.findViewById(R.id.recyclerview_item_data);
            valor = itemView.findViewById(R.id.recyclerview_item_valor_parcelas);
            quantParcela = itemView.findViewById(R.id.recyclerview_item_parcelas);
        }

        private void biulder(Objeto obj){
            onde.setText(obj.getDescricao());
            data.setText(obj.getData());
            String v = "R$" + CalcUtils.doubleFormat(obj.getValorCompra());
            String p = obj.getQuantParcelas() + "x";
            valor.setText(v);
            quantParcela.setText(p);
        }
    }
}
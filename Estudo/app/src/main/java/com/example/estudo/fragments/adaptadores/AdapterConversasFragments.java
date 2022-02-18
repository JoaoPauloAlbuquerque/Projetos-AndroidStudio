package com.example.estudo.fragments.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estudo.R;
import com.example.estudo.fragments.objetos.ObjetoFragmentConversasWhatsapp;

import java.util.List;

public class AdapterConversasFragments extends RecyclerView.Adapter<AdapterConversasFragments.MyViewHolderFragmets>{

    private List<ObjetoFragmentConversasWhatsapp> lista;

    public AdapterConversasFragments(List<ObjetoFragmentConversasWhatsapp> lista){
        this.lista = lista;
    }

    @NonNull
    @Override
    public MyViewHolderFragmets onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_whatsapp_conversas, parent, false);
        return new MyViewHolderFragmets(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderFragmets holder, int position) {
        holder.construir(position);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    class MyViewHolderFragmets extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView nome;
        private TextView mensagem;
        private TextView data;
        private boolean lido;
        private View bolinha;

        public MyViewHolderFragmets(@NonNull View itemView) {
            super(itemView);

            iniComponentes(itemView);
        }

        private void iniComponentes(View v){
            img = v.findViewById(R.id.icon_whatsapp);
            nome = v.findViewById(R.id.contato_whatsapp);
            mensagem = v.findViewById(R.id.mensagem_whatsapp);
            data = v.findViewById(R.id.data_whatsapp);
            bolinha = v.findViewById(R.id.bolinha_whatsapp);

        }

        public void construir(int position){
            this.img.setImageResource(R.drawable.ic_person);
            this.nome.setText(lista.get(position).getNome());
            this.mensagem.setText(lista.get(position).getMensagem());
            this.data.setText(lista.get(position).getData());

            if(lista.get(position).isLido()){
                bolinha.setVisibility(View.INVISIBLE);
            }
        }
    }

}

package com.example.estudo.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.estudo.R;
import com.example.estudo.fragments.adaptadores.AdapterConversasFragments;
import com.example.estudo.fragments.objetos.ObjetoFragmentConversasWhatsapp;

public class ConversasFragmentsActivity extends Fragment {

    private RecyclerView recyclerView;
    private ObjetoFragmentConversasWhatsapp objetoFragmentConversasWhatsapp;
    private AdapterConversasFragments adapterConversasFragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragments_conversas, container, false);

        //CODE THE CLASS

        iniComponentes(v);

        recyclerView.setAdapter(adapterConversasFragments);

        return v;
    }

    private void iniComponentes(View v){
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_whatsapp);
        objetoFragmentConversasWhatsapp = new ObjetoFragmentConversasWhatsapp();
        adapterConversasFragments = new AdapterConversasFragments(ObjetoFragmentConversasWhatsapp.Construir.contruir());
    }
}
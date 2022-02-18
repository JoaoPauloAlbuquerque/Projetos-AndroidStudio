package com.example.estudo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.estudo.model.Objeto;

public class Recycler_Activity extends AppCompatActivity {

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        popularRecycler();
    }

    private void popularRecycler(){
        rv = findViewById(R.id.recycler_view);
        rv.setAdapter(new MyAdapter(Objeto.CriarObjetos.criar()));
    }
}
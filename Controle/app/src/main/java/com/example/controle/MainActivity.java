package com.example.controle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.controle.adapter.MyAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.iniComponents();
        this.recyclerView.setAdapter(myAdapter);
    }

    private void iniComponents(){
        this.recyclerView = findViewById(R.id.recycler_view);
        this.myAdapter = new MyAdapter();
    }
}
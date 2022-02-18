package com.example.internet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private ArrayList<Objeto> list;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recycler);
        list = getIntent().getParcelableArrayListExtra("objetos");

        adapter = new Adapter(list, this.getApplicationContext());
        recycler.setAdapter(adapter);
    }
}
package com.example.noticias;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rc;
    private Adapter adapter;
    private ArrayList<Objeto> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = getIntent().getParcelableArrayListExtra("objeto");

        rc = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new Adapter(list);
        rc.setAdapter(adapter);
    }
}
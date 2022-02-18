package com.example.placar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView p1;
    private TextView p2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p1 = (TextView) findViewById(R.id.p1);
        p2 = (TextView) findViewById(R.id.p2);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);//Salva Activity
    }


    public void add1p1(View view){
        int p = Integer.valueOf(p1.getText().toString());
        p += 1;
        p1.setText(String.valueOf(p));
    }

    public void add3p1(View view){
        int p = Integer.valueOf(p1.getText().toString());
        p += 3;
        p1.setText(String.valueOf(p));
    }

    public void add6p1(View view){
        int p = Integer.valueOf(p1.getText().toString());
        p += 6;
        p1.setText(String.valueOf(p));
    }

    public void add9p1(View view){
        int p = Integer.valueOf(p1.getText().toString());
        p += 9;
        p1.setText(String.valueOf(p));
    }

    public void add12p1(View view){
        int p = Integer.valueOf(p1.getText().toString());
        p += 12;
        p1.setText(String.valueOf(p));
    }

    public void zerarp1(View view){
        String p = "0";
        p1.setText(p);
    }

//===========================================================================

    public void add1p2(View view){
        int p = Integer.valueOf(p2.getText().toString());
        p += 1;
        p2.setText(String.valueOf(p));
    }

    public void add3p2(View view){
        int p = Integer.valueOf(p2.getText().toString());
        p += 3;
        p2.setText(String.valueOf(p));
    }

    public void add6p2(View view){
        int p = Integer.valueOf(p2.getText().toString());
        p += 6;
        p2.setText(String.valueOf(p));
    }

    public void add9p2(View view){
        int p = Integer.valueOf(p2.getText().toString());
        p += 9;
        p2.setText(String.valueOf(p));
    }

    public void add12p2(View view){
        int p = Integer.valueOf(p2.getText().toString());
        p += 12;
        p2.setText(String.valueOf(p));
    }

    public void zerarp2(View view){
        String p = "0";
        p2.setText(p);
    }
}
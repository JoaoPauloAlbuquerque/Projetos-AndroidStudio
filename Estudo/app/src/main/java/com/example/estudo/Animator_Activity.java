 package com.example.estudo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

 public class Animator_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
    }

     @Override
     public void finish() {
         super.finish();
         overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);
     }
 }
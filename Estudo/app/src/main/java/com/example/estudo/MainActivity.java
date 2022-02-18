package com.example.estudo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.estudo.fragments.FragmentsActivity;
import com.example.estudo.inetrnet.TerremotoActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void abrirRecycler(View v){
        Intent i = new Intent(MainActivity.this, Recycler_Activity.class);
        startActivity(i);
    }

    public void abrirAnimator(View v){
        Intent i = new Intent(MainActivity.this, Animator_Activity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(
                getApplicationContext(),
                R.anim.fade_in,
                R.anim.mover_direita);
        ActivityCompat.startActivity(MainActivity.this, i, activityOptionsCompat.toBundle());
    }

    public void abrirTelaAudio(View v){
        Intent i = new Intent(MainActivity.this, AudioActivity.class);
        startActivity(i);
    }

    public void abrirTelaFragments(View v){
        Intent i = new Intent(MainActivity.this, FragmentsActivity.class);
        startActivity(i);
    }

    public void abrirInternet(View v){
        Intent i = new Intent(MainActivity.this, TerremotoActivity.class);
        startActivity(i);
    }
}
package com.example.estudo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Splash_Activity extends AppCompatActivity {

    private ImageView logo;
    private Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = (ImageView) findViewById(R.id.logo);
        anim = AnimationUtils.loadAnimation(this, R.anim.mover_logo);

        logo.setAnimation(anim);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash_Activity.this, MainActivity.class);
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita );
                ActivityCompat.startActivity(Splash_Activity.this, i, activityOptionsCompat.toBundle());
                finish();
            }
        }, 4000);

    }
}
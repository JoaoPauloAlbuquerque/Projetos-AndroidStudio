package com.example.estudo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class AudioActivity extends AppCompatActivity {

    //criação dos objetos necessários na activity
    private ImageView img;
    private MediaPlayer play;
    private boolean tocando;

    private AudioManager audioManager;

    //este médoto verifica quando a música finalizar
    //quando finalizar, ele limpa o objeto MediaPlayer para liberar o espaço em memória
    private MediaPlayer.OnCompletionListener mpList = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            pararAudio();
        }
    };

    //variável que contra o recebimento de focus do MediaPlayer
    private AudioManager.OnAudioFocusChangeListener audioManegerFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                //pausa a musica
                play.pause();
                tocando = false;
            } else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                //retomar a musica
                play.start();
                tocando = true;
            } else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                //finaliza a musica e libera recurso
                pararAudio();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        Log.e("AudioActivity", "onCreate()");

        tocando = false;
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        img = findViewById(R.id.play);
        //este método é acionado quando o usuário clica no ícone de play na acitivity
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int resultado = audioManager.requestAudioFocus(audioManegerFocusListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                if(resultado == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                    if (play == null) {
                        //instancias dos objetos
                        play = MediaPlayer.create(AudioActivity.this, R.raw.meia_noite);
                        play.setOnCompletionListener(mpList);
                    }
                    //se a música estiver tocando, ao clicar novamente no ícone, ele a pausara
                    //e mudará o ícone da imagem para pausado;

                    //caso a música estiver pausada ele iniciará a música e mudará o icone para tocando
                    if(!tocando){
                        play.start();
                        img.setImageResource(R.drawable.ic_pause);
                        tocando = true;
                    } else {
                        play.pause();
                        img.setImageResource(R.drawable.ic_play);
                        tocando = false;
                    }
                }
            }
        });
    }


    private void pararAudio(){
        if(play != null) {
            play.release();
            play = null;
            audioManager.abandonAudioFocus(audioManegerFocusListener);
            img.setImageResource(R.drawable.ic_play);
            tocando = false;
        }
    }

    /**
     * CICLO DE VIDA DE UMA ACTIVITY
     */

    //primeiro ela passa pelo método onCreate() logo acima

    //em seguida ela passa pelo estado iniciado onStarted();
    //nesse estado já é possivel visualisar a activity.
    @Override
    protected void onStart() {
        super.onStart();

        Log.e("AudioActivity", "onStart()");
    }

    //depois passa pelo estado resumido onStart();
    //neste estado já é possivel visualizar as animações e interagir com a activity.
    //este estado pode ficar ativo por minutos ou até horas, até que o usuário mude a activity.
    @Override
    protected void onResume() {
        super.onResume();

        Log.e("AudioActivity", "onResume()");
    }

    //quando o usuário resolver trocar de acitivity ou de aplicativo (sem fechar o mesmo)
    //ela vai para o estado pausado onPause();
    //desse estado ela pode voltar para o onResume() e voltar para atividade normalmente.
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("AudioActivity", "onPause()");
    }

    //caso o usuário não volte para a acitivity, ela chama o modo stop onStop();
    //que para a activivy;
    //apenas neste momento que a activity não está mais visível para o usuário.
    @Override
    protected void onStop() {
        super.onStop();

        Log.e("AudioActivity", "onStop()");

        pararAudio();
    }

    //se caso o usuário demore para voltar para a acitivity, o android determina que não é mais necessário
    //usar os recursos para a acitivity e então ele destroi ela com o onDestroy();
    //em seguida caso o usuário resolva voltar para a activity é chamado o método onRestart() e retoma o ciclo novamente.
    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e("AudioActivity", "onDestroy()");
    }

    //este método é chamado quando o usuário resolve voltar para a acitivity despois de um tempo
    //antes que o onDestroy() destrua ela.
    @Override
    protected void onRestart() {
        super.onRestart();

        Log.e("AudioActivity", "onRestart()");
    }

    //este método é chamado quando o usuário finaliza a activity
    @Override
    public void finish() {
        super.finish();

        pararAudio();

        Log.e("AudioActivity", "finish()");
    }
}
package com.example.estudo.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.estudo.R;
import com.example.estudo.fragments.adaptadores.SimpleFragmentsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class FragmentsActivity extends AppCompatActivity {

    private ViewPager2 vp;
    private SimpleFragmentsAdapter adapterFragments;

    private TabLayout tabLayout;
    private TabLayoutMediator tabLayoutMediator;

    private FloatingActionButton botaoFlutuante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);

        botaoFlutuante = findViewById(R.id.botao_flutuante);

        vp = (ViewPager2) findViewById(R.id.view_pager);

        adapterFragments = new SimpleFragmentsAdapter(this, criarListaFragmentos());

        vp.setAdapter(adapterFragments);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        //adiciona os titulos das tabs
        tabLayoutMediator = new TabLayoutMediator(tabLayout, vp, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0){
                    tab.setIcon(R.drawable.ic_camera);
                    tab.getIcon().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
                } else if (position == 1) {
                    tab.setText("Conversas");
                } else if (position == 2) {
                    tab.setText("Status");
                } else {
                    tab.setText("Chamadas");
                }
            }
        });
        tabLayoutMediator.attach();

        tabLayout.getTabAt(1).select();

        //Log.e("CHILD", (LinearLayout) tabLayout.getChildAt(0).toString());

        //pego o tabItem em que eu quero modificar e converto para um LinearLayout
        LinearLayout layout = (LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(0);
        //atribuindo o LayoutParams do LinearLayout no layoutParams para que possamos alterar as configurações
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        //definindo o peso como zero do tabItem
        layoutParams.weight = 0;
        //definindo a largura como wrap_content do tabItem
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        //adicionando os parâmentros ao LinearLayout
        layout.setLayoutParams(layoutParams);
    }

    private List<Fragment> criarListaFragmentos(){
        return Arrays.asList(
                new CameraFragmentActivity(),
                new ConversasFragmentsActivity(),
                new StatusFragmentsActivity(),
                new ChamadasFragmentsActivity()
        );
    }

}
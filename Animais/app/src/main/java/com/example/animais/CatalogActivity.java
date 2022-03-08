package com.example.animais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Configure o FAB para abrir o EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infle as opções de menu do arquivo res/menu/menu_catalog.xml.
        // Isso adiciona itens de menu à barra de aplicativos.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // O usuário clicou em uma opção de menu no menu flutuante da barra de aplicativos
        switch (item.getItemId()) {
            // Responda a um clique na opção de menu "Inserir dados fictícios"
            case R.id.action_insert_dummy_data:
                // Do nothing for now
                return true;
            // Responda a um clique na opção de menu "Excluir todas as entradas"
            case R.id.action_delete_all_entries:
                // Não faça nada por enquanto
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
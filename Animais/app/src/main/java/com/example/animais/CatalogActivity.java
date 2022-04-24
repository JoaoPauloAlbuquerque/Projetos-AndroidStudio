package com.example.animais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.animais.data.PetContract.PetEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CatalogActivity extends AppCompatActivity {

    public ListView petListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        Log.e("STATUS", "onCreate()");

        // Find the ListView which will be populated with the pet data
        petListView = (ListView) findViewById(R.id.list);

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
    protected void onStart() {
        super.onStart();
        Log.e("STATUS", "onStart()");
        displayDatabaseInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("STATUS", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("STATUS", "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("STATUS", "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("STATUS", "onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("STATUS", "onRestart()");
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        // SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //Selecionando os nomes das colunas para passar como parâmetro na consulta
        String[] projection = {PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
                PetEntry.COLUMN_PET_GENDER,
                PetEntry.COLUMN_PET_WEIGHT
        };

        //Executando a consulta no banco de dados
        /*Cursor cursor = db.query(PetEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );*/

        Cursor cursor = getContentResolver().query(PetEntry.CONTENT_URI, projection, null, null, null);

        // cursor.moveToFirst() - move o cursor para a primeira linha do resultado
        // cursor.moveToLast() - move o cursor para a ultima linha do resultado
        // cursor.moveToPosition(2) - move o cursor para uma linha expecífica, no exemplo, move para a terceira linha
        // cursor.moveToNext() - move o cursor para a próxima linha


        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        PetCursorAdapter adapter = new PetCursorAdapter(this, cursor);

        // Attach the adapter to the ListView.
        petListView.setAdapter(adapter);
    }

    private void insertData(){
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, "half");
        values.put(PetEntry.COLUMN_PET_BREED, "pastor alemao");
        values.put(PetEntry.COLUMN_PET_GENDER, PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_PET_WEIGHT, 14);
        
        Uri uri = getContentResolver().insert(PetEntry.CONTENT_URI, values);

        if(uri != null){
            Toast.makeText(this, getString(R.string.editor_insert_pet_successful), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.editor_insert_pet_failed), Toast.LENGTH_SHORT).show();
        }
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
                insertData();
                displayDatabaseInfo();
                return true;
            // Responda a um clique na opção de menu "Excluir todas as entradas"
            case R.id.action_delete_all_entries:
                // Não faça nada por enquanto
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
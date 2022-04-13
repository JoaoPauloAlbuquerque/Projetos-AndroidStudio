package com.example.animais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.animais.data.PetContract.PetEntry;
import com.example.animais.data.PetDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CatalogActivity extends AppCompatActivity {

    private PetDbHelper mDbHelper;
    public static TextView displayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        Log.e("STATUS", "onCreate()");

        mDbHelper = new PetDbHelper(this);
        displayView = (TextView) findViewById(R.id.text_view_pet);

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
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //Selecionando os nomes das colunas para passar como parâmetro na consulta
        String[] projection = {PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
                PetEntry.COLUMN_PET_GENDER,
                PetEntry.COLUMN_PET_WEIGHT
        };

        //Executando a consulta no banco de dados
        Cursor cursor = db.query(PetEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );


        // cursor.moveToFirst() - move o cursor para a primeira linha do resultado
        // cursor.moveToLast() - move o cursor para a ultima linha do resultado
        // cursor.moveToPosition(2) - move o cursor para uma linha expecífica, no exemplo, move para a terceira linha
        // cursor.moveToNext() - move o cursor para a próxima linha

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            //TextView displayViw = (TextView) findViewById(R.id.text_view_pet);
            displayView.setText("A tablea de pets contém " + cursor.getCount() + " pets.\n\n");
            displayView.append(PetEntry._ID + " - " +
                    PetEntry.COLUMN_PET_NAME + " - " +
                    PetEntry.COLUMN_PET_BREED + " - " +
                    PetEntry.COLUMN_PET_GENDER + " - " +
                    PetEntry.COLUMN_PET_WEIGHT + "\n"
            );

            //Recuperando os ID's das colunas resultantes da consulta
            int idColumnIndex = cursor.getColumnIndex(PetEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);

            //Loop para percorrer o objeto cursor.
            //Chamando o método moveToNext() ele vai sempre para a
            //próxima linha do cursor (se existir)
            while(cursor.moveToNext()){
                //Recuperando os valores da linha selecionada
                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);

                //Imprimindo os valores na tela
                displayView.append("\n" + currentId + " - " +
                        currentName + " - " +
                        currentBreed + " - " +
                        currentGender + " - " +
                        currentWeight
                );
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void insertData(){
        PetDbHelper petDbHelper = new PetDbHelper(this);
        SQLiteDatabase db = petDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, "half");
        values.put(PetEntry.COLUMN_PET_BREED, "pastor alemao");
        values.put(PetEntry.COLUMN_PET_GENDER, PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_PET_WEIGHT, 14);
        
        long newRowId = db.insert(PetEntry.TABLE_NAME, null, values);
        if(newRowId != -1){
            Log.e("SQL", "linha " + newRowId + " inserida com sucesso");
        } else {
            Log.e("SQL", "Erro ao inserir linha");
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
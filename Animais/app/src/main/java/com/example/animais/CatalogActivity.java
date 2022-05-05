package com.example.animais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.animais.data.PetContract.PetEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.widget.SimpleCursorAdapter;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public ListView petListView;

    public static final int PET_LOADER = 0;

    private PetCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        Log.e("STATUS", "onCreate()");

        // Find the ListView which will be populated with the pet data
        petListView = (ListView) findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);

        mCursorAdapter = new PetCursorAdapter(this, null);
        petListView.setAdapter(mCursorAdapter);

        getLoaderManager().initLoader(PET_LOADER, null, this);

        // Configure o FAB para abrir o EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                Uri currentPetUri = ContentUris.withAppendedId(PetEntry.CONTENT_URI, id);
                intent.setData(currentPetUri);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("STATUS", "onStart()");
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

    /**
     * Método auxiliar para excluir todos os animais de estimação no banco de dados.
     */
    private void deleteAllPets() {
        int rowsDeleted = getContentResolver().delete(PetEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
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
                return true;
            // Responda a um clique na opção de menu "Excluir todas as entradas"
            case R.id.action_delete_all_entries:
                deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED
        };

        return new CursorLoader(this,
                PetEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        this.mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.mCursorAdapter.swapCursor(null);
    }
}
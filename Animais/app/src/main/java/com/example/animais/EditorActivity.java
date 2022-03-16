package com.example.animais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.animais.data.PetContract.PetEntry;
import com.example.animais.data.PetDbHelper;

public class EditorActivity extends AppCompatActivity {

    /** Campo EditText para inserir o nome do animal de estimação */
    private EditText mNameEditText;

    /** Campo EditText para inserir a raça do animal */
    private EditText mBreedEditText;

    /** Campo EditText para inserir o peso do animal */
    private EditText mWeightEditText;

    /** Campo EditText para inserir o sexo do animal */
    private Spinner mGenderSpinner;

    private PetDbHelper mDbHelper;

    /**
     * Gênero do animal de estimação. Os valores possíveis são:
     * 0 para sexo desconhecido, 1 para masculino, 2 para feminino.
     */
    private int mGender = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mDbHelper = new PetDbHelper(this);

        // Encontre todas as visualizações relevantes das quais precisaremos ler a entrada do usuário
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);
        mWeightEditText = (EditText) findViewById(R.id.edit_pet_weight);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        setupSpinner();

    }

    /**
     * Configure o spinner suspenso que permite ao usuário selecionar o sexo do animal de estimação.
     */
    private void setupSpinner() {
        // Crie adaptador para spinner. As opções da lista são do array String que ele usará
        // o spinner usará o layout padrão
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Especifique o estilo de layout suspenso - exibição de lista simples com 1 item por linha
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Aplique o adaptador ao spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Defina o inteiro mSelected para os valores constantes
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = PetEntry.GENDER_MALE; // masculino
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = PetEntry.GENDER_FEMALE; // feminino
                    } else {
                        mGender = PetEntry.GENDER_UNKNOWN; // desconhecido
                    }
                }
            }

            // Como AdapterView é uma classe abstrata, onNothingSelected deve ser definido
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0; // desconhecido
            }
        });
    }

    private void insertPets(){
        String name = mNameEditText.getText().toString().trim();
        String breed = mBreedEditText.getText().toString().trim();
        String stringWeight = mWeightEditText.getText().toString().trim();
        String stringGender = mGenderSpinner.getSelectedItem().toString();
        int weight = Integer.parseInt(stringWeight);

        Log.e("SPINNER", "intem selecionado - " + stringGender);
        int gender;
        if(stringGender.equals(getString(R.string.gender_male))){
            gender = 1;
        } else if(stringGender.equals(getString(R.string.gender_female))){
            gender = 2;
        } else {
            gender = 0;
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, name);
        values.put(PetEntry.COLUMN_PET_BREED, breed);
        values.put(PetEntry.COLUMN_PET_GENDER, gender);
        values.put(PetEntry.COLUMN_PET_WEIGHT, weight);

        long newRowId = db.insert(PetEntry.TABLE_NAME, null, values);

        if(newRowId < 0){
            Toast.makeText(this, "Erro ao inserir pet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Pet " + newRowId + " inserido com sucesso", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infle as opções de menu do arquivo res/menu/menu_editor.xml.
        // Isso adiciona itens de menu à barra de aplicativos.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // O usuário clicou em uma opção de menu no menu flutuante da barra de aplicativos
        switch (item.getItemId()) {
            // Responda a um clique na opção de menu "Salvar"
            case R.id.action_save:
                insertPets();
                finish();
                return true;
            // Responda a um clique na opção de menu "Excluir"
            case R.id.action_delete:
                // Não faça nada por enquanto
                return true;
            // Responda a um clique no botão de seta "Para cima" na barra de aplicativos
            case android.R.id.home:
                // Navegue de volta para a atividade pai (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
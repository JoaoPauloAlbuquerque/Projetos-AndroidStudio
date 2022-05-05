package com.example.animais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.animais.data.PetContract.PetEntry;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int EXISTING_PET_LOADER = 1;

    /** Campo EditText para inserir o nome do animal de estimação */
    private EditText mNameEditText;

    /** Campo EditText para inserir a raça do animal */
    private EditText mBreedEditText;

    /** Campo EditText para inserir o peso do animal */
    private EditText mWeightEditText;

    /** Campo EditText para inserir o sexo do animal */
    private Spinner mGenderSpinner;

    /**
     * Gênero do animal de estimação. Os valores possíveis são:
     * 0 para sexo desconhecido, 1 para masculino, 2 para feminino.
     */
    private int mGender = 0;

    /** URI de Conteúdo para o pet existente (nulo se é um novo pet) */
    private Uri mCurrentPetUri;

    /** Sinalizador booleano que controla se o pet foi editado (true) ou não (false) */
    private boolean mPetHasChanged = false;

    /**
     * OnTouchListener que escuta qualquer toque do usuário em uma View, implicando que eles estão modificando
     * a visualização e alteramos o booleano mPetHasChanged para true.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mPetHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Encontre todas as visualizações relevantes das quais precisaremos ler a entrada do usuário
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);
        mWeightEditText = (EditText) findViewById(R.id.edit_pet_weight);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        setupSpinner();

        mNameEditText.setOnTouchListener(mTouchListener);
        mBreedEditText.setOnTouchListener(mTouchListener);
        mWeightEditText.setOnTouchListener(mTouchListener);
        mGenderSpinner.setOnTouchListener(mTouchListener);

        Intent intent = getIntent();
        mCurrentPetUri = intent.getData();

        if(mCurrentPetUri == null){
            this.setTitle(R.string.editor_activity_title_new_pet);

            // Invalida o menu de opções, então a opção de menu "Delete" pode ser ocultada.
            // (Não faz sentido deletar um pet que não foi criado ainda.)
            // ele chama o método onPrepareOptionsMenu()
            invalidateOptionsMenu();
        } else {
            this.setTitle(R.string.editor_activity_title_edit_pet);
            getLoaderManager().initLoader(EXISTING_PET_LOADER, null, this);
        }
    }

    /**
     * Mostra uma caixa de diálogo que avisa o usuário que há alterações não salvas que serão perdidas
     * se eles continuarem deixando o editor.
     *
     * @param discardButtonClickListener é o ouvinte de cliques para o que fazer quando
     * o usuário confirma que deseja descartar suas alterações
     */
    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener){
        // Cria um AlertDialog.Builder e configura a mensagem e click listeners
        // para os botões positivos e negativos do dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // O usuário clicou no botão "Continuar editando", então, feche a caixa de diálogo
                // e continue editando o pet.
                if(dialog != null){
                    dialog.dismiss();
                }
            }
        });

        if(mPetHasChanged) {
            // Cria e mostra o AlertDialog
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    /**
     * Este método é chamado quando o botão Voltar é pressionado.
     */
    @Override
    public void onBackPressed() {
        // Se o pet não mudou, continue lidando com clique do botão "back"
        if(!this.mPetHasChanged){
            super.onBackPressed();
        }

        // Caso contrário, se houver alterações não salvas, configure uma caixa de diálogo para alertar o usuário.
        // Crie um click listener para lidar com o usuário, confirmando que mudanças devem ser descartadas.
        DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicou no botão "Discard", fecha a activity atual.
                finish();
            }
        };

        // Mostra o diálogo que diz que há mudanças não salvas
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    private void showDeleteConfirmationDialog() {
        // Crie um AlertDialog.Builder e defina a mensagem e clique em listeners
        // para os botões postivos e negativos na caixa de diálogo.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // O usuário clicou no botão "Excluir", então exclua o animal de estimação.
                deletePet();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // O usuário clicou no botão "Cancelar", então feche a caixa de diálogo
                // e continue editando o animal de estimação.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Cria e mostre o AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Realize a exclusão do pet no banco de dados.
     */
    private void deletePet() {
        // Somente execute a exclusão se este for um animal de estimação existente.
        if (mCurrentPetUri != null) {
            // Chama o ContentResolver para deletar o pet no dado URI de conteúdo.
            // Passa em nulo para o selection e selection args porque o URI de conteúdo do mCurrentPetUri
            // já identifica o pet que queremos por meio do id.
            int rowsDeleted = getContentResolver().delete(mCurrentPetUri, null, null);

            // Mostra uma mensagem toast dependendo se ou não o delete foi bem sucedido.
            if (rowsDeleted == 0) {
                // Se nenhum registro foi deletado, então houve um erro com o delete.
                Toast.makeText(this, getString(R.string.editor_delete_pet_failed), Toast.LENGTH_SHORT).show();
            } else {
                // Caso contrário, o delete foi bem sucedido e podemos mostrar um toast.
                Toast.makeText(this, getString(R.string.editor_delete_pet_successful), Toast.LENGTH_SHORT).show();
            }

            // Fecha a activity
            finish();
        }
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

    private void savePet(){
        String name = mNameEditText.getText().toString().trim();
        String breed = mBreedEditText.getText().toString().trim();
        String stringWeight = mWeightEditText.getText().toString().trim();
        String stringGender = mGenderSpinner.getSelectedItem().toString();
        int weight = 0;
        if (!TextUtils.isEmpty(stringWeight)) {
            weight = Integer.parseInt(stringWeight);
        }

        Log.e("SPINNER", "intem selecionado - " + stringGender);
        int gender;
        if(stringGender.equals(getString(R.string.gender_male))){
            gender = 1;
        } else if(stringGender.equals(getString(R.string.gender_female))){
            gender = 2;
        } else {
            gender = 0;
        }

        if (mCurrentPetUri == null && TextUtils.isEmpty(name) && TextUtils.isEmpty(breed) && TextUtils.isEmpty(stringWeight) && mGender == PetEntry.GENDER_UNKNOWN) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, name);
        values.put(PetEntry.COLUMN_PET_BREED, breed);
        values.put(PetEntry.COLUMN_PET_GENDER, gender);
        values.put(PetEntry.COLUMN_PET_WEIGHT, weight);

        if(mCurrentPetUri == null) {
            Uri uri = getContentResolver().insert(PetEntry.CONTENT_URI, values);

            if (uri != null) {
                Toast.makeText(this, getString(R.string.editor_insert_pet_successful), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_insert_pet_failed), Toast.LENGTH_SHORT).show();
            }
        } else {
            int rowsAffected = getContentResolver().update(this.mCurrentPetUri, values, null, null);
            
            if (rowsAffected > 0){
                Toast.makeText(this, getString(R.string.editor_update_pet_successful), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_update_pet_failed), Toast.LENGTH_SHORT).show();
            }
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
                savePet();
                finish();
                return true;
            // Responda a um clique na opção de menu "Excluir"
            case R.id.action_delete:
                // Mostra um dialog de confirmação para deleção
                showDeleteConfirmationDialog();
                return true;
            // Responda a um clique no botão de seta "Para cima" na barra de aplicativos
            case android.R.id.home:
                // Se o pet não mudou, continua navegando para cima, para a activity pai
                // que é a {@link CatalogActivity}.
                if(!this.mPetHasChanged) {
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
                }

                // Caso contrário, se houver alterações não salvas, configura um diálogo para alertar o usuário.
                // Cria um click listener para lidar com o usuário, confirmando que
                // mudanças devem ser descartadas.
                DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    }
                };

                // Mostra um diálogo que notifica o usuário de que há alterações não salvas
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Este método é chamado após invalidateOptionsMenu(), para que o
     * o menu pode ser atualizado (alguns itens do menu podem ser ocultados ou tornados visíveis).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        // Se este for um novo animal de estimação, oculte o item de menu "Excluir".
        if(mCurrentPetUri == null){
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Como o editor mostra todos os atributos de pet, defina uma projeção que contenha
        // todas as colunas da tabela pet
        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
                PetEntry.COLUMN_PET_GENDER,
                PetEntry.COLUMN_PET_WEIGHT };

        // Este loader executará o método de query do ContentProvider em uma thread de segundo plano
        return new CursorLoader(this,    // Contexto da activity Pai
                mCurrentPetUri,                 // Busca o URI de conteúdo para o pet corrente
                projection,                     // Colunas para incluir no Cursor resultante
                null,                   // Sem cláusula de selection
                null,                // Sem selection args
                null);                 // Ordem de seleção padrão
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.moveToFirst()){
            // Acha as colunas de atributos pet em que estamos interessados
            int nameColumnIndex = data.getColumnIndex(PetEntry.COLUMN_PET_NAME);
            int breedColumnIndex = data.getColumnIndex(PetEntry.COLUMN_PET_BREED);
            int genderColumnIndex = data.getColumnIndex(PetEntry.COLUMN_PET_GENDER);
            int weightColumnIndex = data.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);

            // Extrai o valor do Cursor para o índice de coluna dado
            String name = data.getString(nameColumnIndex);
            String breed = data.getString(breedColumnIndex);
            int gender = data.getInt(genderColumnIndex);
            int weight = data.getInt(weightColumnIndex);

            // Atualize as views na tela com os valores do banco de dados
            mNameEditText.setText(name);
            mBreedEditText.setText(breed);
            mWeightEditText.setText(Integer.toString(weight));

            // Gênero é um spinner dropdown, então mapeie o valor da constante do banco de dados
            // em uma das opções de dropdown (0 é Desconhecida, 1 é Masculino, 2 é Feminino).
            // Então chame setSelection() para que a opção seja mostrada na tela como a seleção corrente.
            switch (gender) {
                case PetEntry.GENDER_MALE:
                    mGenderSpinner.setSelection(1);
                    break;
                case PetEntry.GENDER_FEMALE:
                    mGenderSpinner.setSelection(2);
                    break;
                default:
                    mGenderSpinner.setSelection(0);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.mNameEditText.setText("");
        this.mBreedEditText.setText("");
        this.mGenderSpinner.setSelection(0);
        this.mWeightEditText.setText("");
    }
}
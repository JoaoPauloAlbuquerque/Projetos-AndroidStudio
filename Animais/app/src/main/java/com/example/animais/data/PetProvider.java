package com.example.animais.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.animais.data.PetContract.PetEntry;

/**
 * Ao criar esta classe, deve-se referencia-la no arquivo manifest.
 * ultilizando a tag <provider>.
 */
public class PetProvider extends ContentProvider {

    /** Tag para as mensagens de log */
    public static final String LOG_TAG = PetProvider.class.getSimpleName();

    // Código de correspondência de URI para o URI de conteúdo da tabela de animais de estimação
    public static final int PETS = 100;
    // Código de correspondência de URI para o URI de conteúdo de um único animal de estimação na tabela de animais de estimação
    public static final int PET_ID = 101;

    // UriMatcher objeto para corresponder um URI de conteúdo a um código correspondente.
    // A entrada passada para o construtor representa o código a ser retornado para o URI raiz.
    // É comum usar NO_MATCH como entrada para este caso.
    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    //Inicializador estático. Isso é executado na primeira vez que qualquer coisa é chamada dessa classe.
    static {

        // As chamadas para addURI() vão aqui, para todos os padrões de URI de conteúdo que o provedor deve reconhecer.
        // Todos os caminhos adicionados ao UriMatcher têm um código correspondente para retornar quando uma
        // correspondência é mais afeiçoada.

        // O URI de conteúdo no formato "content://com.exemple.android.pets/pets" será mapeado para o
        // código inteiro {@link #PETS}. Este URI é usado para fornecer acesso a UMA única linha da tabela pets.
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS, PETS);

        // O URI de conteúdo no formato "content://com.exemple.android.pets/pets/#" será mapeado para o
        // código inteiro {@link #PET_ID}. Este URI é usado para fornecer acesso a UMA única linha da tabela pets.

        // Nesse caso, o curinga "#" é usado onde "#" pode ser substituído por um inteiro.
        // Por exemplo, "content://com.example.android.pets/pets/3" corresponde, mas
        // "content:com.example.android.pets/pets" (sem um número no final) não corresponde.
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS + "/#", PET_ID);
    }

    private PetDbHelper mDbHelp;

    @Override
    public boolean onCreate() {
        mDbHelp = new PetDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase db = mDbHelp.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match){
            case PETS:
                // Para o código PETS, consulte a tabela pets diretamente com o dado
                //projeção, seleção, argumentos de seleção e ordem de classificação. O cursor
                //pode conter várias linhas da tabela de animais de estimação
                cursor = db.query(PetEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PET_ID:
                // Para o código PET_ID, extraia o ID do URI.
                //Para um exemplo de URI como "content://com.example.android.pets/pets/3",
                //a seleção será "_id=?" e o argumento de seleção será um array String contendo o ID real de 3 neste caso.

                //Para cada "?" na seleção, precisamos ter um elemento na seleção
                //argumentos que irão preencher o "?". Como temos 1 ponto de interrogação na
                //seleção, temos 1 String no array String dos argumentos de seleção.
                selection = PetEntry._ID + "=?";
                selectionArgs = new String[] {
                        String.valueOf(ContentUris.parseId(uri))
                };
                // Isto irá realizar uma consulta na tabela pets onde o _id é igual a 3 para retornar um
                //Cursor que contém essa linha da tabela.
                cursor = db.query(PetEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Não é possível consultar URI desconhecido: " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        int match = sUriMatcher.match(uri);

        switch(match){
            case PETS:
                return insertPet(uri, values);
            default:
                throw new IllegalArgumentException("Erro ao inserir pet no provedor: " + uri);
        }
    }

    /**
     * Insira um pet no banco de dados com os dados content values. Retorne o novo content URI
     * para aquele específico registro do banco de dados.
     */
    private Uri insertPet(Uri uri, ContentValues values) {

        SQLiteDatabase db = mDbHelp.getWritableDatabase();

        String name = values.getAsString(PetEntry.COLUMN_PET_NAME);
        if(name == null){
            throw new IllegalArgumentException("Pet requires a name");
        }

        Integer gender = values.getAsInteger(PetEntry.COLUMN_PET_GENDER);
        if(gender == null || !PetEntry.isValidGender(gender)){
            throw new IllegalArgumentException("Pet requires valid gender");
        }

        Integer weight = values.getAsInteger(PetEntry.COLUMN_PET_WEIGHT);
        if(weight != null && weight < 0){
            throw new IllegalArgumentException("Pet requires valid weight");
        }

        long id = db.insert(PetEntry.TABLE_NAME, null, values);

        if(id != -1){
            // Uma vez que sabemos o ID do novo registro na tabela,
            // retornamos o novo URI com o ID concatenado ao fim dele
            Log.e(LOG_TAG, "Pet inserido " + id + " com sucesso");
            return ContentUris.withAppendedId(uri, id);
        } else {
            Log.e(LOG_TAG, "Erro ao inserir pet");
            return null;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

package com.example.animais;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.animais.data.PetContract;

/**
 * {@link PetCursorAdapter} é um adaptador para uma visualização de lista ou grade
 * que usa um {@link Cursor} de dados de animais de estimação como fonte de dados. Este adaptador sabe
 * como criar itens de lista para cada linha de dados de animais de estimação no {@link Cursor}.
 */
public class PetCursorAdapter extends CursorAdapter {

    /**
     * Constrói um novo {@link PetCursorAdapter}.
     *
     * @param context O contexto
     * @param c O cursor do qual obter os dados.
     */
    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    /**
     * Cria uma nova visualização de item de lista em branco. Nenhum dado está definido (ou vinculado) às visualizações ainda.
     *
     * contexto do aplicativo de contexto @param
     * @param cursor O cursor do qual obter os dados. O cursor já está
     * movido para a posição correta.
     * @param parent O pai ao qual a nova visualização está anexada
     * @return a visualização de item de lista recém-criada.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * Este método liga os dados do animal de estimação (na linha atual apontada pelo cursor) ao dado
     * layout de item de lista. Por exemplo, o nome do animal de estimação atual pode ser definido no nome TextView
     * no layout do item de lista.
     *
     * @param view Visão existente, retornada anteriormente pelo método newView()
     * contexto do aplicativo de contexto @param
     * @param cursor O cursor do qual obter os dados. O cursor já foi movido para o
     * linha correta.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtName = view.findViewById(R.id.name);
        TextView txtBreed = view.findViewById(R.id.summary);
        String name = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetEntry.COLUMN_PET_NAME));
        String breed = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetEntry.COLUMN_PET_BREED));

        // Se a raça do animal de estimação for uma string vazia ou nula, use algum texto padrão
        // que diz "Raça desconhecida", então o TextView não fica em branco.
        if (TextUtils.isEmpty(breed)) {
            breed = context.getString(R.string.unknown_breed);
        }

        txtName.setText(name);
        txtBreed.setText(breed);
    }
}

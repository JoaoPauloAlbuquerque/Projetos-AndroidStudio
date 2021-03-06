package com.example.animais.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class PetContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.pets";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PETS = "pets";

    public static final class PetEntry implements BaseColumns{
        public static final String TABLE_NAME = "pets";

        /**
         * O URI de conteúdo para acessar os dados do animal de estimação no provedor
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

        /**
         * O tipo MIME do {@link #CONTENT_URI} para uma lista de pets.
         */
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        /**
         * O tipo MIME do {@link #CONTENT_URI} para um único pet.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        /**
         * colunas da tabela pets
         */
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PET_NAME = "name";
        public static final String COLUMN_PET_BREED = "breed";
        public static final String COLUMN_PET_GENDER = "gender";
        public static final String COLUMN_PET_WEIGHT = "weight";

        /**
         * Possíveis valores da coluna gênero da tabela pets
         */
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        /**
         * retorna sim se o dado gênero é {@link #GENDER_MALE}, {@link #GENDER_FEMALE}
         *  ou {@link #GENDER_UNKNOWN}
         */
        public static boolean isValidGender(int gender){
            if(gender == GENDER_MALE || gender == GENDER_FEMALE || gender == GENDER_UNKNOWN){
                return true;
            }
            return false;
        }

    }

}

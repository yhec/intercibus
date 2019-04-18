package com.example.ausias.intercibus.basedades;

import android.provider.BaseColumns;

/**
 * Aquesta classe serveix per definir el nom de les taules i el de les seves columnes
 * @author Antonio de Dios Durán
 * @version 1.0
 */

public class FeedReaderContract {

    /**
     * Constructor privat per evitar que algú faci una instància accidentalment de la classe
     */
    private FeedReaderContract() {
    }

    /**
     *Classe interna que defineix els continguts de la taula
     */
    public static class FeedEntry implements BaseColumns {

        public static final String TABLE_NAME = "Usuaris";
        public static final String COLUMN_ID = "_ID";
        public static final String COLUMN_USERNAME = "Username";
        public static final String COLUMN_PASSWORD = "Password";
    }
}
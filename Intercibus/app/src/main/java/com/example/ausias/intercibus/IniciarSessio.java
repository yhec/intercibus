package com.example.ausias.intercibus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ausias.intercibus.basedades.BBDDHelper;
import com.example.ausias.intercibus.classes.JSONParser;
import com.example.ausias.intercibus.classes.Usuari;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * Aquesta classe serveix perquè l'usuari pugui iniciar sessió amb el seu username i password
 */
public class IniciarSessio extends AppCompatActivity {

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //ids
    private static final String TAG_SUCCESS = "success";
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sessio);

        etUsername = (EditText) findViewById(R.id.etnomUsuari);
        etPassword = (EditText) findViewById(R.id.etpass);
    }

    /**
     * Mètode que obté el username i el password i comprova si aquest existeix a la base de dades
     * @param view Paràmtre que fa referència a una vista
     */
    public void loginUsuari(View view) {

        // Obtenir camps username i password
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        BBDDHelper bd = new BBDDHelper(this);

        // Si la BBDD SQLite té 1 fila o més, ho esborrem tot
        if (bd.teContingut() >= 1) {
            this.deleteDatabase(BBDDHelper.DATABASE_NAME);
        }

        // Guardem l'usuari introduït
        bd.guarda(username, password);

        // Inicialitzar AsyncTask amb les dades recollides
        AsyncLogin asyncLogin = new AsyncLogin();
        asyncLogin.execute(username, password);
    }

    private class AsyncLogin extends AsyncTask<String, String, Boolean> {


        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... args) {
            // Construir els paràmetres
            HashMap<String, String> params = new HashMap<String, String>();

            String username = args[0];
            String password = args[1];

            params.put("username", username);
            params.put("password", password);

            // getting JSON Object
            JSONObject json = jsonParser.makeHttpRequest(Constants.LOGIN_URL, "POST", params);

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);


                if (success == 1) {

                    return true;
                } else {

                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        public void onPostExecute(Boolean result) {

            if (result) {
                Intent intent = new Intent(IniciarSessio.this, PaginaPrincipal.class);
                startActivity(intent);
                IniciarSessio.this.finish();

            } else if (!result) {
                // Si les dades no són correctes, es mostrar un missatge d'error
                String message = getString(R.string.iniciSessioKO);
                Toast.makeText(IniciarSessio.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}


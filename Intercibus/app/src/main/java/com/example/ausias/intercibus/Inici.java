package com.example.ausias.intercibus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ausias.intercibus.basedades.BBDDHelper;
import com.example.ausias.intercibus.classes.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Aquesta classe serveix escollir iniciar sessió o registrar-se
 * @author Jessica Miranda
 * @version 1.0
 */
public class Inici extends AppCompatActivity {

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //ids
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BBDDHelper bd = new BBDDHelper(this);

        if (bd.teContingut() == 1) {
            List<String> usuari = bd.getUsuari();
            AsyncLogin asyncLogin = new AsyncLogin();
            asyncLogin.execute(usuari.get(0), usuari.get(1));
        }
        // Sí té més d'una fila, no sabem quin usuari utilitzar per iniciar sessió, per tant, s'esborra tot
        else if (bd.teContingut() > 1) {
            this.deleteDatabase(BBDDHelper.DATABASE_NAME);
        }

        setContentView(R.layout.activity_inici);
    }

    /**
     * Mètode que crida a l'activity Iniciar Sessió
     * @param view Paràmetre que fa referència a una vista
     */
    public void iniciSessio(View view){
        Intent intent = new Intent(this, IniciarSessio.class);
        startActivity(intent);
    }

    /**
     * Mètode que crida a l'activity Resgistre
     * @param view Paràmetre que fa referència a una vista
     */
    public void registre(View view){
        Intent intent = new Intent(this, Registre.class);
        startActivity(intent);
    }


    private class AsyncLogin extends AsyncTask<String, String, Boolean> {


        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... args) {
            // Building Parameters
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
                Intent intent = new Intent(Inici.this, PaginaPrincipal.class);
                startActivity(intent);
                Inici.this.finish();

            } else if (!result) {
                String message = getString(R.string.sessioAutoKO);
                Toast.makeText(Inici.this, message, Toast.LENGTH_LONG).show();
            }
        }

    }

}

package com.example.ausias.intercibus;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ausias.intercibus.classes.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Aquesta classe serveix perquè l'usuari pugui visualitzar tota la informació de les reserves rebudes
 * @author Antonio de Dios Durán
 * @version 1.0
 */
public class VeureInfoClient extends BaseActivity {

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //ids
    private static final String TAG_SUCCESS_RESULT = "success";
    private static final String TAG_MESSAGE_RESULT = "messageResultat";

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veure_info_client);

        int idReserva = getIntent().getExtras().getInt("idReserva");

        AsyncVeureInfoClient async = new AsyncVeureInfoClient();
        async.execute(idReserva);
    }

    private class AsyncVeureInfoClient extends AsyncTask<Integer, Void, List<String>> {

        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected List<String> doInBackground(Integer... args) {

            int idReserva = args[0];

            // Building Parameters
            HashMap<String, String> params = new HashMap<String, String>();

            //Enviem el username del usuari
            params.put("idReserva", String.valueOf(idReserva));

            JSONObject json = jsonParser.makeHttpRequest(Constants.VEURE_INFO_CLIENT, "POST", params);

            List<String> dadesClient = new ArrayList<>();

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS_RESULT);
                Log.d("success!!!!!!!!!!!!!", success+"");

                if (success == 1) {
                    //String message = json.getString(TAG_MESSAGE_RESULT);
                    //Recollim les dades del client
                    String nom = json.getString("nom");
                    String telefon = json.getString("telefon");
                    String email = json.getString("email");

                    dadesClient.add(nom);
                    dadesClient.add(telefon);
                    dadesClient.add(email);

                    return dadesClient;

                } else {
                    return dadesClient;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return dadesClient;
        }

        public void onPostExecute(List<String> dadesClient) {

            // Si el size es > 0, tenim dades
            if (dadesClient.size() > 0) {
                TextView tvNom = findViewById(R.id.tvNomClient);
                TextView tvTelefon = findViewById(R.id.tvTelefonClient);
                TextView tvEmail = findViewById(R.id.tvEmailClient);

                tvNom.setText(dadesClient.get(0));
                tvTelefon.setText(dadesClient.get(1));
                tvEmail.setText(dadesClient.get(2));
            }
            else {
                String message = getString(R.string.veureInfoUsuariKO);
                Toast.makeText(VeureInfoClient.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

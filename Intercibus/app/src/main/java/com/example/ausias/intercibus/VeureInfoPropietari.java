package com.example.ausias.intercibus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ausias.intercibus.classes.JSONParser;
import com.example.ausias.intercibus.classes.Reserva;
import com.example.ausias.intercibus.recyclers.AdaptadorRecyclerPropies;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Aquesta classe serveix perquè l'usuari pugui visualitzar tota la informació de les reserves realitzades
 * @author Antonio de Dios Durán
 * @version 1.0
 */
public class VeureInfoPropietari extends BaseActivity {
    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //ids
    private static final String TAG_SUCCESS_RESULT = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veure_info_propietari);

        int idReserva = getIntent().getExtras().getInt("idReserva");
        String idReservaString = String.valueOf(idReserva);

        AsyncInfoPropietari asyncInfoPropietari = new AsyncInfoPropietari();
        asyncInfoPropietari.execute(idReservaString);
    }

    private class AsyncInfoPropietari extends AsyncTask<String, Void, List<String>> {

        protected void onPreExecute(){

            super.onPreExecute();
        }

        @Override
        protected List<String> doInBackground(String... args) {

            String idReserva = args[0];

            // Building Parameters
            HashMap<String, String> params = new HashMap<String, String>();

            params.put("idReserva", idReserva);

            JSONObject json = jsonParser.makeHttpRequest(Constants.VEURE_INFO_PROPIETARI, "POST", params);

            List<String> dades = new ArrayList<>();

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS_RESULT);
                Log.d("success!!!!!!!!!!!!!", success+"");

                if (success == 1) {
                    //String message = json.getString(TAG_MESSAGE_RESULT);
                    //Recollim el numero de reserves

                    try {
                        String nom = json.getString("nom");
                        dades.add(nom);
                        String telefon = json.getString("telefon");
                        dades.add(telefon);
                        String email = json.getString("email");
                        dades.add(email);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

                    return dades;
                    }
                } catch (JSONException e1) {
                e1.printStackTrace();
            }


            return dades;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onPostExecute(List<String> dadesPropietari) {

            // Segons el resultat del doInBackground, es mostra el resultat obtingut

            if (dadesPropietari.size() > 0) {
                TextView tvNom = findViewById(R.id.tvNomPropietari);
                TextView tvTelefon = findViewById(R.id.tvTelefonPropietari);
                TextView tvEmail = findViewById(R.id.tvEmailPropietari);

                tvNom.setText(dadesPropietari.get(0));
                tvTelefon.setText(dadesPropietari.get(1));
                tvEmail.setText(dadesPropietari.get(2));
            }
            else {
                String message = getString(R.string.veureInfoUsuariKO);
                Toast.makeText(VeureInfoPropietari.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

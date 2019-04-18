package com.example.ausias.intercibus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ausias.intercibus.basedades.BBDDHelper;
import com.example.ausias.intercibus.classes.JSONParser;
import com.example.ausias.intercibus.classes.Reserva;
import com.example.ausias.intercibus.recyclers.AdaptadorRecyclerRebudes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Aquesta classe serveix perquè els usuaris puguin visualitzar les reserves que han rebut d'altres usuaris
 * @author Jessica Miranda
 * @version 1.0
 */
public class ReservaRebudes extends BaseActivity {

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //ids
    private static final String TAG_SUCCESS_RESULT = "success";
    private static final String TAG_MESSAGE_RESULT = "messageResultat";

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_solicitada);

        //Obtenim el nom usuari de la BD
        BBDDHelper bd = new BBDDHelper(this);
        List<String> usuari = bd.getUsuari();
        String username = usuari.get(0);

        Toolbar toolbarCard = (Toolbar) findViewById(R.id.toolbarCard);
        //toolbarCard.setTitle();

        AsyncReservesSolicitades asyncReservesSolicitades = new AsyncReservesSolicitades();
        asyncReservesSolicitades.execute(username);
    }

    private class AsyncReservesSolicitades extends AsyncTask<String, List<Reserva>, List<Reserva>> {

        protected void onPreExecute(){

            super.onPreExecute();
        }

        @Override
        protected List<Reserva> doInBackground(String... args) {

            String username = args[0];

            // Building Parameters
            HashMap<String, String> params = new HashMap<String, String>();

            //Enviem el username del usuari
            params.put("username", username);

            JSONObject json = jsonParser.makeHttpRequest(Constants.CONSULTA_RESERVES_SOLICITADES, "POST", params);

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS_RESULT);
                Log.d("success!!!!!!!!!!!!!", success+"");

                if (success == 1) {
                    //String message = json.getString(TAG_MESSAGE_RESULT);
                    //Recollim el numero de reserves
                    int numReserves = Integer.parseInt(json.getString("num_reserves"));
                    Log.d("numReserves", numReserves+" ");

                    //Recollim dades de les reserves
                    List<Reserva> reserves = new ArrayList<>();
                    for (int i = 0; i < numReserves; i ++) {
                        String nom = json.getString("nom" + i);
                        // Recollir foto
                        String foto = json.getString("foto" + i);
                        byte[] decodedString = Base64.decode(foto, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        // id_reserva
                        int id = Integer.parseInt(json.getString("reserva" + i));
                        float preu = Float.parseFloat(json.getString("preu" + i));
                        String entrada = json.getString("entrada" + i);
                        String sortida = json.getString("sortida" + i);

                        Reserva reserva = new Reserva(nom, bitmap, id, preu, entrada, sortida);
                        reserves.add(reserva);
                    }

                    return reserves;

                } else {
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onPostExecute(List<Reserva> reserves) {

            // Segons el resultat del doInBackground, es mostra el resultat obtingut
            if (reserves!=null && reserves.size() > 0) {
                rv = (RecyclerView) findViewById(R.id.recycler_reserves_solicitades);
                rv.setLayoutManager(new LinearLayoutManager(ReservaRebudes.this));

                AdaptadorRecyclerRebudes adap = new AdaptadorRecyclerRebudes(reserves);
                rv.setAdapter(adap);

            }

            else {
                String message = getString(R.string.noReserves);
                Toast.makeText(ReservaRebudes.this, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReservaRebudes.this, PaginaPrincipal.class);
                startActivity(intent);
            }


        }
    }
}

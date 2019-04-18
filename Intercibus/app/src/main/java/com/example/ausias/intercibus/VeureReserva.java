package com.example.ausias.intercibus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ausias.intercibus.classes.Extra;
import com.example.ausias.intercibus.classes.JSONParser;
import com.example.ausias.intercibus.classes.Reserva;
import com.example.ausias.intercibus.recyclers.RecyclerVeureEspai;
import com.example.ausias.intercibus.recyclers.RecyclerVeureReserva;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Aquesta classe serveix perquè l'usuari pugui veure informació més detallada de les reserves
 * @author Antonio de Dios Durán
 * @version 1.0
 */
public class VeureReserva extends BaseActivity {

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //ids
    private static final String TAG_SUCCESS_RESULT = "success";
    private static final String TAG_MESSAGE_RESULT = "message";

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veure_reserva);

        int idReserva = getIntent().getExtras().getInt("idReserva");

        AsyncVeureReserva async = new AsyncVeureReserva();
        async.execute(idReserva);

        rv = (RecyclerView) findViewById(R.id.recycler_serveis_reservats);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private class AsyncVeureReserva extends AsyncTask<Integer, Void, Reserva> {

        protected void onPreExecute(){

            super.onPreExecute();
        }

        @Override
        protected Reserva doInBackground(Integer... args) {

            int idReserva = args[0];

            // Building Parameters
            HashMap<String, String> params = new HashMap<String, String>();

            //Enviem l'id de la reserva
            params.put("reserva_id", String.valueOf(idReserva));

            JSONObject json = jsonParser.makeHttpRequest(Constants.VEURE_RESERVA, "POST", params);

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS_RESULT);
                Log.d("success!!!!!!!!!!!!!", success+"");

                if (success == 1) {
                    //String message = json.getString(TAG_MESSAGE_RESULT);

                    float preu = Float.parseFloat(json.getString("preutotal"));
                    String entrada = json.getString("entrada");
                    String sortida = json.getString("sortida");
                    String nomEspai = json.getString("espai");
                    String foto = json.getString("foto");
                    byte[] decodedString = Base64.decode(foto, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    int numServeis = Integer.parseInt(json.getString("num_serveis"));
                    //Recollim dades dels serveis
                    List<Extra> serveis = new ArrayList<>();
                    for (int i = 0; i < numServeis; i ++) {
                        String nomServei = json.getString("nom" + i);
                        float preuServei = Float.parseFloat(json.getString("preu" + i));

                        Extra extra = new Extra (nomServei, preuServei);
                        serveis.add(extra);
                    }

                    Reserva reserva = new Reserva (nomEspai, bitmap, preu, entrada, sortida, serveis);
                    return reserva;

                } else {
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onPostExecute(Reserva reserva) {

            // Segons el resultat del doInBackground, es mostra el resultat obtingut
            if (reserva != null) {

                TextView nomEspai = findViewById(R.id.tvNomEspaiReservat);
                TextView entrada = findViewById(R.id.tvEntradaReserva);
                TextView sortida = findViewById(R.id.tvSortidaReserva);
                TextView preu = findViewById(R.id.tvPreuTotal);

                TextView titolServeisReservats = findViewById(R.id.titolServeisReservats);

                if (reserva.getServeis().size() > 0) {
                    titolServeisReservats.setVisibility(VISIBLE);
                }
                else {
                    titolServeisReservats.setVisibility(INVISIBLE);
                }

                nomEspai.setText(reserva.getNomEspai());
                entrada.setText(reserva.getEntradaReserva());
                sortida.setText(reserva.getSortidaReserva());

                String preuString;
                if (reserva.getPreuTotal() % 1 == 0) {
                    preuString = String.valueOf((int) reserva.getPreuTotal()) + " €";
                }
                else {
                    preuString = String.valueOf(reserva.getPreuTotal()) + " €";
                }

                preu.setText(preuString);

                RecyclerVeureReserva adap = new RecyclerVeureReserva(reserva.getServeis());
                rv.setAdapter(adap);
            }
            else {
                String message = getString(R.string.noInfo);
                Toast.makeText(VeureReserva.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

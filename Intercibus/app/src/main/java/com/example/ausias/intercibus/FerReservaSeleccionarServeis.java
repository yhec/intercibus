package com.example.ausias.intercibus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ausias.intercibus.basedades.BBDDHelper;
import com.example.ausias.intercibus.classes.Extra;
import com.example.ausias.intercibus.classes.JSONParser;
import com.example.ausias.intercibus.classes.Reserva;
import com.example.ausias.intercibus.recyclers.RecyclerFerReservaSeleccionarServeis;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Aquesta classe serveix per seleccionar els serveis que volem al realitzar la reserva
 * @author Antonio de Dios Durán
 * @version 1.0
 */
public class FerReservaSeleccionarServeis extends BaseActivity {

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_NO_RESERVAT = "No s'ha pogut reservar l'espai.";

    RecyclerView rv;

    Reserva reserva;

    RecyclerFerReservaSeleccionarServeis adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fer_reserva_seleccionar_serveis);

        BBDDHelper bd = new BBDDHelper(this);
        List<String> usuari = bd.getUsuari();
        Log.d("username", usuari.get(0));

        int idEspai = getIntent().getExtras().getInt("id_espai");Log.d("IDESPAI!!!!!!!!!", idEspai+"");
        List<Extra> serveis = new ArrayList<>();
        int numServeis = getIntent().getExtras().getInt("numServeis");
        for (int i = 0; i < numServeis; i ++) {
            String nom = getIntent().getExtras().getString("servei" + i);
            float preu = getIntent().getExtras().getFloat("preu" + i);
            Extra servei = new Extra(nom, preu);
            serveis.add(servei);
        }
        float preuBase = getIntent().getExtras().getFloat("preuBase");
        DateTime entrada = DateTime.parse(getIntent().getExtras().getString("entrada"));
        DateTime sortida = DateTime.parse(getIntent().getExtras().getString("sortida"));

        reserva = new Reserva (idEspai, entrada, sortida, preuBase, usuari.get(0), serveis);
        rv = (RecyclerView) findViewById(R.id.recycler_reserva_serveis);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Mostrar dades
        TextView dataEntrada = findViewById(R.id.tvEntradaReserva);
        TextView dataSortida = findViewById(R.id.tvSortidaReserva);
        TextView preuTotal = findViewById(R.id.tvPreuTotal);

        dataEntrada.setText(reserva.toSQLString(reserva.getInici()));
        dataSortida.setText(reserva.toSQLString(reserva.getSortida()));

        if (reserva.getPreuTotal() % 1 == 0) {
            preuTotal.setText(String.valueOf((int)reserva.getPreuTotal()) + " €");
        }
        else {
            preuTotal.setText(String.valueOf(reserva.getPreuTotal()) + " €");
        }

        Log.d("SERVEIS", serveis.toString());
        adap = new RecyclerFerReservaSeleccionarServeis(serveis, reserva.getPreuTotal(), preuTotal);
        rv.setAdapter(adap);
    }

    /**
     * Mètode que per introduir el preu total de la reserva i afegir els serveis seleccionats
     * @param view Paràmetre que retorna una vista
     */
    public void ferReserva (View view) {
        // Posem el preu total a la reserva
        reserva.setPreuTotal(adap.preuTotal);
        List<Extra> serveis = new ArrayList<>();
        // Mirar quins serveis té seleccionats
        for (int i = 0; i < adap.extres.size(); i ++) {
            if (adap.extres.get(i).isMarcat()) {
                serveis.add(adap.extres.get(i));
            }
        }
        // Hi han serveis
        if (serveis.size() > 0) {
            reserva.setServeis(serveis);
            // S'executa l'AsyncTask per guardar la reserva a la BBDD
            FerReservaAsyncTask reservar = new FerReservaAsyncTask();
            reservar.execute(reserva);
        }
        // Error, s'ha de marcar algun servei
        else {
            String missatge = getString(R.string.noServeis);
            Toast.makeText(FerReservaSeleccionarServeis.this, missatge, Toast.LENGTH_LONG).show();
        }
    }

    private class FerReservaAsyncTask extends AsyncTask<Reserva, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Reserva... args) {
            Reserva reserva = args[0];

            // Building Parameters
            HashMap<String, String> params = new HashMap<String, String>();

            params.put("id_espai", String.valueOf(reserva.getIdEspai()));
            Log.d("ID", reserva.getIdEspai()+"");
            String entrada = reserva.toSQLString(reserva.getInici());
            String sortida = reserva.toSQLString(reserva.getSortida());
            Log.d("INICI", entrada + " SORTIDA: " + sortida);
            params.put("entrada", entrada);
            params.put("sortida", sortida);
            params.put("preuTotal", String.valueOf(reserva.getPreuTotal()));
            Log.d("PREU TOTAL", String.valueOf(reserva.getPreuTotal()));
            params.put("username", reserva.getUsername());
            Log.d("USERNAME", reserva.getUsername());
            params.put("num_serveis", String.valueOf(reserva.getServeis().size()));
            for (int i = 0; i < reserva.getServeis().size(); i ++) {
                params.put("servei" + i, reserva.getServeis().get(i).getNom());
                //params.put("preu" + i, String.valueOf(reserva.getServeis().get(i).getPreu()));
            }

            // getting JSON Object
            JSONObject json = jsonParser.makeHttpRequest(Constants.FER_RESERVA_URL,
                    "POST", params);

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    String message = json.getString(TAG_MESSAGE);
                    return message;
                } else {
                    String message = json.getString(TAG_MESSAGE);
                    return message;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String message) {
            // Segons el resultat del doInBackground, es mostra el resultat obtingut
            if (!message.isEmpty()) {
                if (message.equals(TAG_NO_RESERVAT)) {
                    String missatge = getString(R.string.reservaKO);
                    Toast.makeText(FerReservaSeleccionarServeis.this, missatge, Toast.LENGTH_LONG).show();
                }
                else {
                    String missatge = getString(R.string.reservaOK);
                    Toast.makeText(FerReservaSeleccionarServeis.this, missatge, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(FerReservaSeleccionarServeis.this, PaginaPrincipal.class);
                    startActivity(intent);
                }
            }
            else {
                String missatge = getString(R.string.reservaKO);
                Toast.makeText(FerReservaSeleccionarServeis.this, missatge, Toast.LENGTH_LONG).show();
            }
        }
    }
}

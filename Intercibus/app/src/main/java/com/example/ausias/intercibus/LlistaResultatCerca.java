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
import android.widget.Toast;

import com.example.ausias.intercibus.recyclers.AdaptadorRecyclerCerca;
import com.example.ausias.intercibus.classes.Cerca;
import com.example.ausias.intercibus.classes.Espai;
import com.example.ausias.intercibus.classes.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.example.ausias.intercibus.Constants.RESULTAT_URL;

/**
 * Aquesta classe serveix per mostrar els espais que obtenim del resultat de la recerca
 * @author  Jessica Miranda
 * @version 1.0
 */
public class LlistaResultatCerca extends BaseActivity {
    RecyclerView rv;
    List<String> llistaEspais;
    ArrayList<String> llistaFotos;
    List<Espai> espaisCercats;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //ids
    private static final String TAG_SUCCESS_RESULT = "successResultat";
    private static final String TAG_MESSAGE_RESULT = "messageResultat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_resultat_cerca);

        espaisCercats = new ArrayList<>();

        ArrayList<String> serveis = getIntent().getStringArrayListExtra("llistaServeis");
        String provincia = getIntent().getExtras().getString("provincia");
        Cerca cerca = new Cerca(serveis, provincia);

        AsyncFiltrarServeis asyncFiltrarServeis = new AsyncFiltrarServeis();
        asyncFiltrarServeis.execute(cerca);
    }

    private class AsyncFiltrarServeis extends AsyncTask<Cerca, String, String> {

        protected void onPreExecute(){

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Cerca... args) {
            List<String> llistaServeisSelect;
            String provincia;

            Cerca cerca = args[0];

            llistaServeisSelect = cerca.getServeis();
            provincia = cerca.getProvincia();

            // Building Parameters
            HashMap<String, String> params = new HashMap<String, String>();

            params.put("provincia", provincia);

            // Enviem el número de noms de serveis introduits
            params.put("num_serveis", Integer.toString(llistaServeisSelect.size()));

            // Enviem cada nom de serveis al parser
            for (int i = 0; i < llistaServeisSelect.size(); i ++) {
                params.put("servei"+i, llistaServeisSelect.get(i));
                //Log.d("Serveis!!!!!!!!!!!!", llistaServeisSelect.get(i));
            }

            JSONObject json = jsonParser.makeHttpRequest(Constants.RESULTAT_URL, "POST", params);
            //Log.d("ruta", RESULTAT_URL);

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS_RESULT);

                if (success == 1) {
                    String message = json.getString(TAG_MESSAGE_RESULT);
                    //Log.d("MESSAGE final:", message);
                    return message;

                } else {
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onPostExecute(String message) {

            // Segons el resultat del doInBackground, es mostra el resultat obtingut
            if (!message.equals(null)) {

                if (message.length() > 1) {
                    message = message.substring(1, message.length());

                    //Log.d("Message sin 2 puntos", message);
                    String[] espaisParts = message.split(":");
                    llistaEspais = Arrays.asList(espaisParts);
                    Log.d("Message length", llistaEspais.size() + "");
                    Log.d("Message", llistaEspais + "");

                    for (int i = 0; i < llistaEspais.size(); i += 9) {
                        Log.d("Espai"+i, llistaEspais.get(i));
                        int id = Integer.parseInt(llistaEspais.get(i));
                        Log.d("id", id + "");
                        String username = llistaEspais.get(i + 1);
                        Log.d("username", username);
                        String nom = llistaEspais.get(i + 2);
                        Log.d("nom", nom);
                        String descrip = llistaEspais.get(i + 3);
                        Log.d("descripcio", descrip);
                        int area = Integer.parseInt(llistaEspais.get(i + 4));
                        Log.d("area", area + "");
                        String direc = llistaEspais.get(i + 5);
                        Log.d("direccio", direc);
                        String ubi = llistaEspais.get(i + 6);
                        Log.d("provincia", ubi);
                        float preu = Float.parseFloat(llistaEspais.get(i + 7));
                        Log.d("preu", preu + "");
                        Log.d("preu"+(i+7), llistaEspais.get(i+7));

                        byte[] data = Base64.decode(llistaEspais.get(i + 8), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                        Espai espai = new Espai(id, nom, descrip, area, direc, ubi, preu, username, bitmap);
                        espaisCercats.add(espai);
                    }
                }

                else {
                    String missatge = getString(R.string.noEspais);
                    Toast.makeText(LlistaResultatCerca.this, missatge, Toast.LENGTH_SHORT).show();
                }

                rv = (RecyclerView) findViewById(R.id.recycler_id_recerca);
                rv.setLayoutManager(new LinearLayoutManager(LlistaResultatCerca.this));

                AdaptadorRecyclerCerca adap = new AdaptadorRecyclerCerca(espaisCercats);
                rv.setAdapter(adap);
            }
            else{
                String missatge = getString(R.string.noEspais);
                Toast.makeText(LlistaResultatCerca.this, missatge, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

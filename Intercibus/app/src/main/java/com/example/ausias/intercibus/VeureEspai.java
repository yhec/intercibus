package com.example.ausias.intercibus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ausias.intercibus.classes.Espai;
import com.example.ausias.intercibus.classes.Extra;
import com.example.ausias.intercibus.classes.JSONParser;
import com.example.ausias.intercibus.recyclers.RecyclerVeureEspai;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Aquesta classe serveix perquè els usuaris puguin visualit-zar tota la informació de l'espai
 * @author Antonio de Dios Durán
 * @version 1.0
 */
public class VeureEspai extends BaseActivity {

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    RecyclerView rv;

    Espai espaiFinal;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veure_espai);

        // Crear AsyncTask per connectar a la BBDD
        MostrarEspai mostrarEspai = new MostrarEspai();
        int id = getIntent().getExtras().getInt("id");
        String nom = getIntent().getExtras().getString("nom");
        String descripcio = getIntent().getExtras().getString("descripcio");
        int area = getIntent().getExtras().getInt("area");
        String direccio = getIntent().getExtras().getString("direccio");
        String provincia = getIntent().getExtras().getString("provincia");
        float preu = getIntent().getExtras().getFloat("preu");
        String username = getIntent().getExtras().getString("username");

        espaiFinal = new Espai(id, nom, descripcio, area, direccio, provincia, preu, username);
        mostrarEspai.execute(espaiFinal);

        rv = (RecyclerView) findViewById(R.id.recycler_serveis);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Mètode que crida a l'activity Fer Reserva
     * @param view Paràmetre que fa referència a una vista
     */
    public void reservarEspai(View view){
        Intent intent = new Intent(this, FerReserva.class);
        intent.putExtra("idEspai", espaiFinal.getId());
        intent.putExtra("numServeis", espaiFinal.getExtras().size());
        for (int i = 0; i < espaiFinal.getExtras().size(); i ++) {
            intent.putExtra("servei" + i, espaiFinal.getExtras().get(i).getNom());
            intent.putExtra("preu" + i, espaiFinal.getExtras().get(i).getPreu());
        }
        intent.putExtra("preuBase", espaiFinal.getPreu());
        startActivity(intent);
    }

    private class MostrarEspai extends AsyncTask<Espai,Void,Espai> {

        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Espai doInBackground(Espai... args) {
            Espai espai = args[0];

            // Building Parameters
            HashMap<String, String> params = new HashMap<String, String>();

            params.put("id", String.valueOf(espai.getId()));

            // getting JSON Object
            JSONObject json = jsonParser.makeHttpRequest(Constants.MOSTRAR_ESPAI_URL, "POST", params);

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // s'ha carregat l'espai
                    //String message = json.getString(TAG_MESSAGE);
                    // Recollim el número de serveis
                    int numServeis = Integer.parseInt(json.getString("num_serveis"));

                    List<Extra> serveis = new ArrayList<>();
                    for (int i = 0; i < numServeis; i ++) {
                        String nom = json.getString("servei" + i);
                        float preu = Float.parseFloat(json.getString("preu" + i));

                        Extra extra = new Extra(nom, preu);
                        serveis.add(extra);
                    }

                    espai.setExtras(serveis);

                    // Recollir foto
                    String foto = json.getString("foto");
                    byte[] decodedString = Base64.decode(foto, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    espai.setBitmap(bitmap);

                    return espai;
                } else {
                    // error
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(Espai espai) {

            Log.d("Espai Execute", espai.toString());
            // Segons el resultat del doInBackground, es mostra el resultat obtingut
            if (espai != null) {
                Log.d("if null", ":)");
                // Recibir datos: Nombre:Descripción:Area:Nº Servicios:Servicios:Precio:Dirección
                TextView nom = findViewById(R.id.tvNom);
                TextView descripcio = findViewById(R.id.tvDescripcio);
                TextView preu = findViewById(R.id.tvPreu2);
                TextView direccio = findViewById(R.id.tvDireccio);
                TextView area = findViewById(R.id.tvArea2);
                ImageView iv = findViewById(R.id.imageView);

                /*float preuTotal = 0;
                for (int i = 0; i < espai.getExtras().size(); i ++) {
                    preuTotal += espai.getExtras().get(i).getPreu();
                }
                preuTotal = espai.getPreu() + preuTotal;*/

                // Agafar el preu sense decimals si valen 0
                float preuFloat = espai.getPreu();
                String preuString;
                if (preuFloat % 1 == 0) {
                    preuString = String.valueOf((int)preuFloat);
                }
                else {
                    preuString = String.valueOf(preuFloat);
                }

                nom.setText(espai.getNom());
                descripcio.setText(espai.getDescripcio());
                area.setText(String.valueOf(espai.getArea() + " m²"));
                //preu.setText(String.valueOf(preuTotal));
                preu.setText(preuString + " €");
                direccio.setText(espai.getDireccio());
                iv.setImageBitmap(espai.getBitmap());

                espaiFinal.setExtras(espai.getExtras());
                RecyclerVeureEspai adap = new RecyclerVeureEspai(espai.getExtras());
                rv.setAdapter(adap);
            }
            else {
                String message = getString(R.string.carregarEspaiKO);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

}

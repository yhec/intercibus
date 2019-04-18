package com.example.ausias.intercibus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.Spinner;

import com.example.ausias.intercibus.classes.JSONParser;
import com.example.ausias.intercibus.classes.Servei;
import com.example.ausias.intercibus.recyclers.AdaptadorRecyclerServeis;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Aquesta classe serveis per poder filtrar els espais per provincia i serveis oferts
 * @author Jessica Miranda
 * @version 1.0
 */
public class PaginaRecercaServeis extends BaseActivity {

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //ids
    private static final String TAG_SUCCESS = "successLlista";
    private static final String TAG_MESSAGE = "messageLlista";

    Spinner spinner;
    String string="";
    List<Servei> llistaServeis;
    List<String> llistaServeisSelect;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_recerca_serveis);

        spinner = (Spinner) findViewById(R.id.spinnerProvincia);
        String[] provincies = {"Barcelona","Girona","Tarragona","Lleida"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, provincies));

        rv = (RecyclerView) findViewById(R.id.recycler_serveis);
        rv.setLayoutManager(new LinearLayoutManager(this));

        llistaServeis = new ArrayList<>();
        llistaServeisSelect = new ArrayList<>();

       ///Consulta per obtenir la llistaServeisSelect de serveis disponibles a la base de dades
        AsyncServeis asyncServeis = new AsyncServeis();
        asyncServeis.execute(string);

    }

    /**
     * Mètode que crida a l'activity Llista Resultat Cerca i li passa una llista de serveis i la provincia
     * @param view Paràmetre que fa referència a una vista
     */
    public void clicCercar(View view){
        String provincia = spinner.getSelectedItem().toString();

        ArrayList<String> arrayListServeisSelect = new ArrayList<>(llistaServeisSelect);
        Intent intent = new Intent(PaginaRecercaServeis.this, LlistaResultatCerca.class);
        intent.putStringArrayListExtra("llistaServeis", arrayListServeisSelect);
        intent.putExtra("provincia", provincia);
        startActivity(intent);
    }

    private class AsyncServeis extends AsyncTask<String, String, String> {

        protected void onPreExecute(){

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            // Building Parameters
            HashMap<String, String> params = new HashMap<String, String>();

            String string = args[0];

            params.put("string", string);

            // getting JSON Object
            JSONObject json = jsonParser.makeHttpRequest(Constants.LISTA_URL, "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);


                if (success == 1) {
                    String message = json.getString(TAG_MESSAGE);

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

            message = message.substring(1,message.length());

            if (!message.equals(null)) {

                //Log.d("=======", message);
                String[] parts = message.split(":");
                List<String> llista = Arrays.asList(parts);
                //Log.d("======ConsultaLlista", message);
                for (int i = 0; i < llista.size(); i++) {
                    Log.d("Serveis!!!!!!!!!!!!!!!!", llista.get(i));
                }

                for (int i = 0; i < llista.size(); i++) {
                    Servei serveiName = new Servei(llista.get(i));
                    //Log.d("Lista", llista.get(i));
                    llistaServeis.add(serveiName);
                }

                AdaptadorRecyclerServeis adap = new AdaptadorRecyclerServeis(llistaServeis, llistaServeisSelect);
                rv.setAdapter(adap);
            }
        }
    }
}

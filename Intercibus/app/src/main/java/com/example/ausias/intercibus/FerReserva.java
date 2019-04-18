package com.example.ausias.intercibus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ausias.intercibus.classes.DataReservada;
import com.example.ausias.intercibus.classes.DecoradorCalendari;
import com.example.ausias.intercibus.classes.Extra;
import com.example.ausias.intercibus.classes.JSONParser;
import com.example.ausias.intercibus.classes.ReservaUsuari;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Aquesta classe permet emmagatzemar una reserva a la base de dades
 */
public class FerReserva extends BaseActivity {

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_NO_RESERVES = "No hi han reserves.";

    MaterialCalendarView calendari;
    ReservaUsuari reserva = new ReservaUsuari();

    HashSet<CalendarDay> inici = new HashSet<>();

    // Llista per tenir totes les hores que es poden reservar
    List<String> hores = new ArrayList<String>();
    // Llista per tenir els DateTime de tots els dies i hores reservades
    List<DataReservada> diesReservats = new ArrayList<>();
    // Llista per tenir les hores que es mostraran en el desplegable segons el dia escollit
    List<String> horesMostrar = new ArrayList<>();

    Spinner spinner;

    // Id de l'espai
    int idEspai;
    // List dels serveis de l'espai
    List<Extra> serveis;
    // preu base de l'espai
    float preuBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fer_reserva);
        calendari = findViewById(R.id.calendarView);
        calendari.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                boolean reservat = false;
                horesMostrar.clear();
                horesMostrar.addAll(hores);
                if (inici.isEmpty()) {
                    reserva.setDataInici(date);
                    reserva.setDataSortida(date);
                    reserva.setDataIniciPosada(true);

                    // Obtenir la data seleccionada en sql.Date
                    java.util.Date dataUtil = date.getDate();
                    // Convertir la data al format de sql.Date
                    java.sql.Date data = new java.sql.Date(dataUtil.getTime());

                    /* Exemple de què retorna el compare del DateTimeComparator
                    int retVal = dateTimeComparator.compare(myDateOne, myDateTwo)
                    if(retVal == 0) both dates are equal
                    else if(retVal < 0) myDateOne is before myDateTwo (anterior)
                    else if(retVal > 0) myDateOne is after myDateTwo (posterior)
                    */

                    for (int i = 0; i < diesReservats.size(); i++) {
                        Log.d("Comparator", DateTimeComparator.getDateOnlyInstance().compare(diesReservats.get(i).getDataInici(), data) + "");
                        int entrada = DateTimeComparator.getDateOnlyInstance().compare(diesReservats.get(i).getDataInici(), data);
                        int sortida = DateTimeComparator.getDateOnlyInstance().compare(diesReservats.get(i).getDataSortida(), data);

                        if (entrada == 0) {
                            for (Iterator iterador = horesMostrar.iterator(); iterador.hasNext();) {
                                //for (int j = 0; j < horesMostrar.size(); j ++) {
                                LocalTime time = new LocalTime(LocalTime.parse(iterador.next() + ":00"));
                                Log.d("hour of day Inici", time.getHourOfDay()+" horaReservada: " + diesReservats.get(i).getDataInici().getHourOfDay());
                                if (time.getHourOfDay() >= diesReservats.get(i).getDataInici().getHourOfDay()) {
                                    if (sortida == 0 && time.getHourOfDay() <= diesReservats.get(i).getDataSortida().getHourOfDay()) {
                                        iterador.remove();
                                    }
                                    else if (sortida > 0) {
                                        iterador.remove();
                                    }
                                }
                            }
                        }
                        else if ((entrada < 0) && (sortida > 0)) {
                            horesMostrar.removeAll(horesMostrar);
                        }

                        else if (sortida == 0 && entrada < 0) {
                            for (Iterator iterador = horesMostrar.iterator(); iterador.hasNext();) {
                                LocalTime time = new LocalTime(LocalTime.parse(iterador.next() + ":00"));
                                if (time.getHourOfDay() <= diesReservats.get(i).getDataSortida().getHourOfDay()) {
                                    iterador.remove();
                                }
                            }
                        }
                    }

                }
                else {
                    reserva.setDataSortida(date);
                    reserva.setDataSortidaPosada(true);

                    Log.d("CALENDARIO", reserva.getDataInici().toString());
                    // Obtenir la data seleccionada en sql.Date
                    java.util.Date dataUtil = date.getDate();
                    // Convertir la data al format de sql.Date
                    java.sql.Date data = new java.sql.Date(dataUtil.getTime());

                    /* Exemple de què retorna el compare del DateTimeComparator
                    int retVal = dateTimeComparator.compare(myDateOne, myDateTwo)
                    if(retVal == 0) both dates are equal
                    else if(retVal < 0) myDateOne is before myDateTwo (anterior)
                    else if(retVal > 0) myDateOne is after myDateTwo (posterior)
                    */

                    for (int i = 0; i < diesReservats.size(); i++) {
                        Log.d("Comparator", DateTimeComparator.getDateOnlyInstance().compare(diesReservats.get(i).getDataInici(), data) + "");
                        int entrada = DateTimeComparator.getDateOnlyInstance().compare(diesReservats.get(i).getDataInici(), data);
                        int sortida = DateTimeComparator.getDateOnlyInstance().compare(diesReservats.get(i).getDataSortida(), data);

                        if (entrada == 0) {
                            for (Iterator iterador = horesMostrar.iterator(); iterador.hasNext();) {
                                //for (int j = 0; j < horesMostrar.size(); j ++) {
                                LocalTime time = new LocalTime(LocalTime.parse(iterador.next() + ":00"));
                                Log.d("hour of day Inici", time.getHourOfDay()+" horaReservada: " + diesReservats.get(i).getDataInici().getHourOfDay());
                                if (time.getHourOfDay() >= diesReservats.get(i).getDataInici().getHourOfDay()) {
                                    if (sortida == 0 && time.getHourOfDay() <= diesReservats.get(i).getDataSortida().getHourOfDay()) {
                                        iterador.remove();
                                    }
                                    else if (sortida > 0) {
                                        iterador.remove();
                                    }
                                }
                            }
                        }
                        else if ((entrada < 0) && (sortida > 0)) {
                            horesMostrar.removeAll(horesMostrar);
                        }

                        else if (sortida == 0 && entrada < 0) {
                            for (Iterator iterador = horesMostrar.iterator(); iterador.hasNext();) {
                                LocalTime time = new LocalTime(LocalTime.parse(iterador.next() + ":00"));
                                if (time.getHourOfDay() <= diesReservats.get(i).getDataSortida().getHourOfDay()) {
                                    iterador.remove();
                                }
                            }
                        }
                    }
                }

                spinner.setAdapter(new ArrayAdapter<String>(FerReserva.this, android.R.layout.simple_spinner_item, horesMostrar));
            }
        });

        hores.addAll(Arrays.asList("9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"));

        spinner = (Spinner) findViewById(R.id.horesDisponibles);

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hores));

        BuscarDatesLliuresAsyncTask buscarDates = new BuscarDatesLliuresAsyncTask();

        idEspai = getIntent().getExtras().getInt("idEspai");
        serveis = new ArrayList<>();
        int numServeis = getIntent().getExtras().getInt("numServeis");
        for (int i = 0; i < numServeis; i ++) {
            String nom = getIntent().getExtras().getString("servei" + i);
            float preu = getIntent().getExtras().getFloat("preu" + i);
            Extra servei = new Extra(nom, preu);
            serveis.add(servei);
        }
        preuBase = getIntent().getExtras().getFloat("preuBase");

        buscarDates.execute(String.valueOf(idEspai));
    }

    /**
     * Mètode que serveis per seleccionar una data de reserva
     * @param view Paràmetre que fa referència a una vista
     */
    public void posarDataReserva (View view) {

        // Les dues dates de reserva estan posades, pasem a l'activity d'escollir els serveis
        if (reserva.isDataIniciPosada() && reserva.isDataSortidaPosada() && reserva.isTotOK()) {
            Intent intent = new Intent(this, FerReservaSeleccionarServeis.class);
            intent.putExtra("id_espai", idEspai);
            intent.putExtra("entrada", reserva.getInici().toString());
            intent.putExtra("sortida", reserva.getSortida().toString());

            intent.putExtra("numServeis", serveis.size());
            for (int i = 0; i < serveis.size(); i ++) {
                intent.putExtra("servei" + i, serveis.get(i).getNom());
                intent.putExtra("preu" + i, serveis.get(i).getPreu());
            }
            intent.putExtra("preuBase", preuBase);
            startActivity(intent);
        }

        // Només tenim posada la data d'inici de la reserva
        else if (reserva.isDataIniciPosada() && !reserva.isDataSortidaPosada()) {
            Log.d("poner fecha inicio", "");
            if (horesMostrar.size() > 0) {
                // Crear el format del DateTime
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                // Agafar l'hora del desplegable
                LocalTime time = LocalTime.parse(spinner.getSelectedItem().toString() + ":00");
                // Agafar la data seleccionada per l'usuari y convertir-la a java.sql.Date
                java.util.Date dataUtil = reserva.getDataInici().getDate();
                // Convertir la data al format de sql.Date
                java.sql.Date data = new java.sql.Date(dataUtil.getTime());
                // Crear un DateTime amb aquesta data
                DateTime datetime = new DateTime(data);
                // Afegir l'hora al DateTime
                datetime = datetime.withTime(time);Log.d("Data inici", datetime+"");
                // Mirar que la data d'inici no sigui anterior a avui
                if (DateTimeComparator.getInstance().compare(DateTime.now(), datetime) < 0) {
                    reserva.setInici(datetime);
                    inici.add(reserva.getDataInici());
                    calendari.addDecorator(new DecoradorCalendari(1, inici));
                    reserva.setDataSortidaPosada(true);
                    //Log.d("pasa por aqui", ":D");

                    TextView tvEntrada = findViewById(R.id.tvTitol);
                    tvEntrada.setText(getString(R.string.etDiaSortidaReserva));
                }
                else {
                    String message = getString(R.string.dataIniciAnteriorAvui);
                    Toast.makeText(FerReserva.this, message, Toast.LENGTH_LONG).show();
                }
            }
            else {
                String message = getString(R.string.diaJaReservat);
                Toast.makeText(FerReserva.this, message, Toast.LENGTH_LONG).show();
            }
        }

        // Tenim posada la data de sortida de la reserva
        else if (reserva.isDataSortidaPosada()) {
            if (horesMostrar.size() > 0) {
                // Crear el format del DateTime
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                // Agafar l'hora del desplegable
                LocalTime time = LocalTime.parse(spinner.getSelectedItem().toString() + ":00");
                // Agafar la data seleccionada per l'usuari y convertir-la a java.sql.Date
                java.util.Date dataUtil = reserva.getDataSortida().getDate();
                // Convertir la data al format de sql.Date
                java.sql.Date data = new java.sql.Date(dataUtil.getTime());
                // Crear un DateTime amb aquesta data
                DateTime datetime = new DateTime(data);
                // Afegir l'hora al DateTime
                datetime = datetime.withTime(time);Log.d("Data sortida", datetime+"");
                Log.d("if", DateTimeComparator.getInstance().compare(reserva.getInici(), datetime) + "");
                if (DateTimeComparator.getInstance().compare(reserva.getInici(), datetime) < 0) {

                    int i = 0;
                    boolean error = false;
                    // Comprovar que les dues dates insertades no tenen reserves entremig
                    while (i < diesReservats.size() && !error) {
                        //for (int i = 0; i < diesReservats.size(); i ++) {
                        //else if(retVal < 0)
                        //myDateOne is before myDateTwo (anterior)
                        //else if(retVal > 0)
                        //myDateOne is after myDateTwo (posterior)
                        int entrada = DateTimeComparator.getInstance().compare(diesReservats.get(i).getDataInici(), reserva.getInici());
                        int sortida = DateTimeComparator.getInstance().compare(diesReservats.get(i).getDataSortida(), datetime);
                        if (entrada > 0 && sortida < 0) {
                            error = true;
                            String message = getString(R.string.reservesDiesEscollits);
                            Toast.makeText(FerReserva.this, message, Toast.LENGTH_LONG).show();
                        }
                        i++;
                    }
                    if (!error) {
                        reserva.setSortida(datetime);
                        inici.add(reserva.getDataSortida());
                        Log.d("pasa por aqui2", inici.size() + " " + inici.toString());
                        reserva.setTotOK(true);
                        calendari.addDecorator(new DecoradorCalendari(1, inici));

                        TextView tvEntrada = findViewById(R.id.tvTitol);
                        tvEntrada.setText(getString(R.string.btDiaReservaContinuar));
                    }
                } else {
                    String message = getString(R.string.dataSortidaAnteriorInici);
                    Toast.makeText(FerReserva.this, message, Toast.LENGTH_LONG).show();
                }
            }
            else {
                String message = getString(R.string.diaJaReservat);
                Toast.makeText(FerReserva.this, message, Toast.LENGTH_LONG).show();
            }
        }
        else {
            String message = getString(R.string.reservaDiaNoSeleccionar);
            Toast.makeText(FerReserva.this, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Mètode que serveix per eliminar la data seleccionada de reserva per modificar-la
     * @param view Paràmetre que fa referència a una vista
     */
    public void reset (View view) {
        inici.clear();
        calendari.removeDecorator(new DecoradorCalendari(1, inici));
        //reserva.setDataIniciPosada(false);
        reserva.setDataInici(reserva.getDataSortida());
        reserva.setDataSortidaPosada(false);
        reserva.setTotOK(false);

        TextView tvEntrada = findViewById(R.id.tvTitol);
        tvEntrada.setText(getString(R.string.btDiaIniciReserva));

    }

    private class BuscarDatesLliuresAsyncTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            String idEspai = args[0];

            // Building Parameters
            HashMap<String, String> params = new HashMap<String, String>();

            params.put("id_espai", idEspai);

            // getting JSON Object
            JSONObject json = jsonParser.makeHttpRequest(Constants.CONSULTA_DIES_RESERVA_URL,
                    "POST", params);

            // check for success tag
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

        protected void onPostExecute(String message) {
            // Segons el resultat del doInBackground, es mostra el resultat obtingut
            if (!message.isEmpty()) {
                // Només fem coses si hi han dies reservats
                List<String> reserves = new ArrayList<>();
                if (!message.equals(TAG_NO_RESERVES)) {
                    reserves = Arrays.asList(message.split("#"));
                }

                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < reserves.size(); i += 2) {

                    Log.d("CALENDARIO String", reserves.get(i));
                    //diesReservats.add(new DateTime(reserves.get(i)));
                    DateTime inici = formatter.parseDateTime(reserves.get(i));
                    DateTime sortida = formatter.parseDateTime(reserves.get(i + 1));
                    DataReservada data = new DataReservada(inici, sortida);
                    diesReservats.add(data);
                }
                Log.d("CALENDARIO2", diesReservats.toString());
            }
            else {
                String missatge = getString(R.string.carregarDiesReservesKO);
                Toast.makeText(FerReserva.this, missatge, Toast.LENGTH_LONG).show();
            }
        }
    }
}

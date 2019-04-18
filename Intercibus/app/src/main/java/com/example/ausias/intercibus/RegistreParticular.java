package com.example.ausias.intercibus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ausias.intercibus.classes.JSONParser;
import com.example.ausias.intercibus.classes.Particular;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;

/**
 * Aquesta classe serveix perquè un usuari particular es pugui registrar
 * @author Jessica Miranda
 * @version 1.0
 */
public class RegistreParticular extends AppCompatActivity {

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    //Variables per recollir dades usuaris
    private String username;
    private String pass1;
    private String pass2;
    private String nom;
    private String cognom1;
    private String cognom2;
    private String telefon;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre_particular);
    }

    /**
     * Mètode que recull les dades introduïdes per l'usuari que es vol registrar
     * @param view Paràmetre que fa referència a una vista
     */
    public void registrarParticular(View view){

        do{
            //Recollir informació introduïda per el usuari
            EditText etUsername = (EditText)findViewById(R.id.etUsername);
            EditText etPass1 = (EditText)findViewById(R.id.etPassword);
            EditText etPass2 = (EditText)findViewById(R.id.etPassword2);
            EditText etNom = (EditText)findViewById(R.id.etNom);
            EditText etCognom1 = (EditText)findViewById(R.id.etCognom1);
            EditText etCognom2 = (EditText)findViewById(R.id.etCognom2);
            EditText etTelefon = (EditText)findViewById(R.id.etTelefon);
            EditText etEmail = (EditText)findViewById(R.id.etMail);


            username = etUsername.getText().toString();
            pass1 = etPass1.getText().toString();
            pass2 = etPass2.getText().toString();
            nom = etNom.getText().toString();
            cognom1 = etCognom1.getText().toString();
            cognom2 = etCognom2.getText().toString();
            telefon = etTelefon.getText().toString();
            email = etEmail.getText().toString();
            if(!pass1.equals(pass2)) {
                String message = getString(R.string.PasswordNoIguals);
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                etPass1.setText("");
                etPass2.setText("");
            }
        }while(!pass1.equals(pass2));

        if(nom.equals(null)||cognom1.equals(null)||cognom2.equals(null)||username.equals(null)||pass1.equals(null)||telefon.equals(null)||email.equals(null)){
            String message = getString(R.string.omplirTotsCamps);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
        Particular particular = new Particular(nom,cognom1,cognom2,username,pass1,telefon,email);

        insertarParticular(particular);

    }


    /**
     * Mètode que crida a la asyngtask que s'encarrega d'insertar l'usuari a la base de dades
     * @param particular Paràmetre que fa referència a un objecte particular
     */
    public void insertarParticular(Particular particular){

        RegistrarUsuari lma = new RegistrarUsuari();
        lma.execute(particular);


    }

    private class RegistrarUsuari extends AsyncTask<Particular,Void,Boolean> {
        Boolean registrat = false;

        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Particular... args) {
            Particular particular = args[0];


            String username = particular.getUsername();
            String pass1 = particular.getPassword();
            String nom = particular.getNom();
            String cognom1 = particular.getCognom1();
            String cognom2 = particular.getCognom2();
            String telefon = particular.getTelefon();
            String email = particular.getEmail();
            //String nif = "null";

            // Building Parameters
            HashMap<String, String> params = new HashMap<String, String>();

            params.put("username", username);
            params.put("password", pass1);
            params.put("nom", nom);
            params.put("cognom_1", cognom1);
            params.put("cognom_2", cognom2);
            params.put("email", email);
            params.put("telefon", telefon);
            //params.put("nif", nif);
            params.put("alta", "1");
            params.put("tipo_user", "0");


            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(Constants.REGISTER_URL,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    //Toast.makeText(RegistreParticular.this, "Usuari creat correctament.", Toast.LENGTH_LONG).show();
                    return true;
                } else {
                    // failed to create product
                    //Toast.makeText(RegistreParticular.this, "Usuari no s'ha pogut crear.", Toast.LENGTH_LONG).show();
                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(Boolean b) {

            // Segons el resultat del doInBackground, es mostra el resultat obtingut
            if (b) {
                String message = getString(R.string.usuariOK);
                Toast.makeText(RegistreParticular.this, message, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegistreParticular.this, IniciarSessio.class);
                startActivity(intent);
            }
            else {
                String message = getString(R.string.usuariKO);
                Toast.makeText(RegistreParticular.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}

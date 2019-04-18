package com.example.ausias.intercibus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//import com.mysql.jdbc.PreparedStatement;
//import com.mysql.jdbc.Statement;

import com.example.ausias.intercibus.classes.Empresa;
import com.example.ausias.intercibus.classes.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Aquesta classe serveix perquè una empresa pugui registrar-se a la base de dades
 * @author Antonio de Dios Durán
 * @version 1.0
 */
public class RegistreEmpresa extends AppCompatActivity {

    // Declaració dels components per pantalla
    private EditText etNom;
    private EditText etNif;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etPassword2;
    private EditText etTelefon;
    private EditText etEmail;

    Empresa empresa;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre_empresa);
    }

    /**
     * Mètode que recull les dades introduïdes per l'empresa que es vol registrar
     * @param view Paràmetre que fa referència a una vista
     * @throws SQLException
     */
    public void onClick (View view) throws SQLException {
        // Enllaç dels components amb les variables
        etNom = (EditText) findViewById(R.id.etNom);
        etNif = (EditText) findViewById(R.id.etNif);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword2 = (EditText) findViewById(R.id.etPassword2);
        etTelefon = (EditText) findViewById(R.id.etTelefon);
        etEmail = (EditText) findViewById(R.id.etEmail);

        // Treure String de EditText
        String nom = etNom.getText().toString();
        String nif = etNif.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String password2 = etPassword2.getText().toString();
        String telefon = etTelefon.getText().toString();
        String email = etEmail.getText().toString();

        boolean dadesOK = false;

        // Comprovar que no hi han camps sense omplis
        if (nom.isEmpty() || nif.isEmpty() || username.isEmpty() || password.isEmpty() || password2.isEmpty() || telefon.isEmpty() || email.isEmpty()) {
            String message = getString(R.string.omplirTotsCamps);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
        // Comprovar password són iguals
        else if (password.equals(password2) && !password.isEmpty()) {
            dadesOK = true;
        }
        else {
            String message = getString(R.string.PasswordNoIguals);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }

        if (dadesOK) {
            // Crear AsyncTask per connectar a la BBDD
            RegistrarUsuari registraUsuari = new RegistrarUsuari();
            // Creem l'objecte empresa
            empresa = new Empresa(nif, nom, username, password, telefon, email);
            // Executem l'AsyncTask
            registraUsuari.execute(empresa);
        }

    }

    private class RegistrarUsuari extends AsyncTask<Empresa,Void,Boolean> {
        Boolean registrat = false;

        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Empresa... args) {
            Empresa empresa = args[0];


            String username = empresa.getUsername();
            String password = empresa.getPassword();
            String nom = empresa.getNom();
            //String cognom1 = null;
            //String cognom2 = null;
            String telefon = empresa.getTelefon();
            String email = empresa.getEmail();
            String nif = empresa.getNif();

            // Building Parameters
            HashMap<String, String> params = new HashMap<String, String>();

            params.put("username", username);
            params.put("password", password);
            params.put("nom", nom);
            //params.put("cognom_1", cognom1);
            //params.put("cognom_2", cognom2);
            params.put("telefon", telefon);
            params.put("email", email);
            params.put("nif", nif);
            params.put("alta", "1");
            params.put("tipo_user", "1");


            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(Constants.REGISTER_URL,
                    "POST", params);

            // check log cat fro response
            //Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    //Toast.makeText(RegistreEmpresa.this, "Usuari creat correctament.", Toast.LENGTH_LONG).show();
                    return true;
                } else {
                    // failed to create product
                    //Toast.makeText(RegistreEmpresa.this, "L'usuari no s'ha pogut crear.", Toast.LENGTH_LONG).show();
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
                Toast.makeText(RegistreEmpresa.this, message, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegistreEmpresa.this, IniciarSessio.class);
                startActivity(intent);
            }
            else {
                String message = getString(R.string.usuariKO);
                Toast.makeText(RegistreEmpresa.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}

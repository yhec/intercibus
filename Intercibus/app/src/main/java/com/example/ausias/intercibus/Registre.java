package com.example.ausias.intercibus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Aquesta classe serveix perquè l'usuari pugui escollir entre usuari particular o empresa
 * @author Jessica Miranda
 * @version 1.0
 */
public class Registre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);
    }

    /**
     * Mètode que crida a l'ctivity Registre Particular
     * @param view Paràmetre que fa referència a una vista
     */
    public void registrarParticular(View view){
        Intent intent = new Intent(this, RegistreParticular.class);
        startActivity(intent);
    }

    /**
     * Mètode que crida a l'ctivity Registre Empresa
     * @param view Paràmetre que fa referència a una vista
     */
    public void registrarEmpresa(View view){
        Intent intent = new Intent(this, RegistreEmpresa.class);
        startActivity(intent);
    }
}

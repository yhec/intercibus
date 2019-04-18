package com.example.ausias.intercibus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ausias.intercibus.basedades.BBDDHelper;

import java.util.List;

/**
 * Aquesta classe mostra la pàgina principal amb un menú desplegable, un missatge de benvinguda i un botó per seleccionar l'opció de cerca
 * @author Jessica Miranda
 * @version 1.0
 */
public class PaginaPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        drawerLayout = findViewById(R.id.drawerId);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView =(NavigationView)findViewById(R.id.naviView);
        navigationView.setNavigationItemSelectedListener(this);

        BBDDHelper bd = new BBDDHelper(this);
        List<String> usuari = bd.getUsuari();
        String username = usuari.get(0);
        TextView tvUsername = findViewById(R.id.tvNomUsuari);
        tvUsername.setText(username);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        int id=item.getItemId();
        switch (id){
            case R.id.paginaInici:
                intent = new Intent (this, PaginaPrincipal.class);
                startActivity(intent);
                break;
            case R.id.creaEspai:
                //String username = getIntent().getExtras().getString("username");
                intent = new Intent(this, CrearEspai.class);
                //intent.putExtra("username", username);
                startActivity(intent);
                break;
            case R.id.cercaPerso:
                intent = new Intent(this, PaginaRecercaServeis.class);
                startActivity(intent);
                break;
            case R.id.reservaPropia:
                intent = new Intent(this, ReservaPropia.class);
                startActivity(intent);
                break;
            case R.id.reservaSol:
                intent = new Intent(this, ReservaRebudes.class);
                startActivity(intent);
                break;
            case R.id.tancarSessio:
                //BBDDHelper bd = new BBDDHelper();
                this.deleteDatabase(BBDDHelper.DATABASE_NAME);//bd.esborrar();
                //BBDDHelper.close();
                intent = new Intent (this, Inici.class);
                startActivity(intent);
                break;

        }

        return false;
    }

    /**
     * Mètode que crida a l'activity Pagina Recerca Serveis
     * @param view Paràmetre que fa referència a una vista
     */
    public void cercaServei(View view){
        Intent intent = new Intent(PaginaPrincipal.this, PaginaRecercaServeis.class);
        startActivity(intent);
    }
}

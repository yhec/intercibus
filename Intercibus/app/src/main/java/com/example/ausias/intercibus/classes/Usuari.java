package com.example.ausias.intercibus.classes;

import java.util.Date;

/**
 * Classe abstracta de la que hereden la resta d'usuaris de l'aplicació
 * @author Jessica Miranda
 * @version 1.0
 */

public abstract class Usuari {

    String nom;
    String username;
    String password;
    String telefon;
    String email;


    /**
     * Constructor de Usuari
     * @param nom Paràmetre que fa referència al nom de l'usuari
     * @param username Paràmetre que fa referència al username de l'usuari
     * @param password Paràmetre que fa referència al password de l'usuari
     * @param telefon Paràmetre que fa referència al telèfon de l'usuari
     * @param email Paràmetre que fa referència al email de l'usuari
     */
    public Usuari (String nom, String username, String password, String telefon, String email) {
        this.nom = nom;
        this.username = username;
        this.password = password;
        this.telefon = telefon;
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

package com.example.ausias.intercibus.classes;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Aquesta classe serveix per identificar un espai
 * @author Antonio de Dios Durán
 * @version 1.0
 */

public class Espai {

    int id;
    String nom;
    String descripcio;
    int area;
    String direccio;
    String provincia;
    float preu;
    List<Extra> extras;
    String username;
    Bitmap bitmap;

    /**
     * Constructor per l'espai
     * @param nom Paràmetre que defineix el nom de l'espai
     * @param descripcio Paràmetre que defineix la descripció de l'espai
     * @param area Paràmetre que defineix l'àrea de l'espai
     * @param direccio Paràmetre que defineix la direcció de l'espai
     * @param provincia Paràmetre que defineix la provincia de l'espai
     * @param preu Paràmetre que defineix el preu de l'espai
     * @param extras Paràmetre que defineix els extres de l'espai
     */
    public Espai (String nom, String descripcio, int area, String direccio, String provincia, float preu, List<Extra> extras) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.area = area;
        this.direccio = direccio;
        this.provincia = provincia;
        this.extras = extras;
        this.preu = preu;
    }

    /**
     * Constructor per l'espai
     * @param nom Paràmetre que defineix el nom de l'espai
     * @param descripcio Paràmetre que defineix la descripció de l'espai
     * @param area Paràmetre que defineix l'àrea de l'espai
     * @param direccio Paràmetre que defineix la direcció de l'espai
     * @param provincia Paràmetre que defineix la provincia de l'espai
     * @param preu Paràmetre que defineix el preu de l'espai
     * @param extras Paràmetre que defineix els extres de l'espai
     * @param username Paràmetre que defineix el username de l'espai
     * @param bitmap Paràmetre que defineix la fotografia de l'espai
     */
    public Espai (String nom, String descripcio, int area, String direccio, String provincia, float preu, List<Extra> extras, String username, Bitmap bitmap) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.area = area;
        this.direccio = direccio;
        this.provincia = provincia;
        this.extras = extras;
        this.preu = preu;
        this.username = username;
        this.bitmap = bitmap;
    }


    /**
     * Constructor per l'espai
     * @param id Paràmetre que defineix el id de l'espai
     * @param nom Paràmetre que defineix el nom de l'espai
     * @param descripcio Paràmetre que defineix la descripció de l'espai
     * @param area Paràmetre que defineix l'àrea de l'espai
     * @param direccio Paràmetre que defineix la direcció de l'espai
     * @param provincia Paràmetre que defineix la provincia de l'espai
     * @param preu Paràmetre que defineix el preu de l'espai
     * @param username Paràmetre que defineix el username de l'espai
     */
    public Espai(int id, String nom, String descripcio, int area, String direccio, String provincia, float preu, String username) {

        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
        this.area = area;
        this.direccio = direccio;
        this.provincia = provincia;
        this.preu = preu;
        this.username = username;
    }

    /**
     * Constructor per l'espai
     * @param id Paràmetre que defineix el id de l'espai
     * @param nom Paràmetre que defineix el nom de l'espai
     * @param descripcio Paràmetre que defineix la descripció de l'espai
     * @param area Paràmetre que defineix l'àrea de l'espai
     * @param direccio Paràmetre que defineix la direcció de l'espai
     * @param provincia Paràmetre que defineix la provincia de l'espai
     * @param preu Paràmetre que defineix el preu de l'espai
     * @param username Paràmetre que defineix el username de l'espai
     * @param bitmap Paràmetre que defineix la fotografia de l'espai
     */
    public Espai(int id, String nom, String descripcio, int area, String direccio, String provincia, float preu, String username, Bitmap bitmap) {

        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
        this.area = area;
        this.direccio = direccio;
        this.provincia = provincia;
        this.preu = preu;
        this.username = username;
        this.bitmap = bitmap;
    }

    /**
     * Mètode que retorna el id que identifica a l'espai
     * @return El id que identifica a l'espai
     */
    public int getId() {
        return id;
    }

    /**
     * Mètode que retorna el nom de l'espai
     * @return El nom de l'espai
     */
    public String getNom() {
        return nom;
    }

    /**
     * Mètode que permet modificar el nom de l'espai
     * @param nom Paràmetre que identifica el nom de l'espai
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Mètode que retorna la descripció associada a un espai determinat
     * @return La descripció de l'espai
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     * Mètode que permet modificar la descripció d'un espai
     * @param descripcio Paràmetre que descriu com és un espai
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * Mètode que retorna l'àrea d'un espai determinat
     * @return Àrea d'un espai
     */
    public int getArea() {
        return area;
    }

    /**
     * Mètode que permet modificar l'àrea d'un espai determinat
     * @param area Paràmetre que defineix l'àrea d'un espai
     */
    public void setArea(int area) {
        this.area = area;
    }

    /**
     * Mètode que retorna la direcció d'un espai determinat
     * @return La direcció d'un espai
     */
    public String getDireccio() {
        return direccio;
    }

    /**
     * Mètode que permet modificar la direcció d'un espai
     * @param direccio Paràmetre que defineix la direcció d'un espai
     */
    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    /**
     * Mètode que retorna la provincia d'un espai
     * @return La provincia d'un espai
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Mètode que permet modificar el nom de la provincia d'un espai
     * @param provincia Paràmetre que fa referència al nom d'una provincia
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * Mètode que retorna una llista del extres que te cada espai
     * @return Llista dels extres de l'espai
     */
    public List<Extra> getExtres() {
        return extras;
    }

    /**
     * Mètode que retorna el preu de l'espai
     * @return El preu de un espai determinat
     */
    public float getPreu() {
        return preu;
    }

    /**
     * Mètode que permet modificar el preu d'un espai determinat
     * @param preu Paràmetre que fa referència al preu d'un espai
     */
    public void setPreu(float preu) {
        this.preu = preu;
    }

    /**
     * Mètode que retorna el username de l'espai
     * @return El username de l'espai
     */
    public String getUsername() {
        return username;
    }

    /**
     * Mètode que permet modificar el username de l'espai
     * @param username Paràmetre que fa referència al username de l'espai
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Mètode que retorna el bitmap de les fotografies de l'espai
     * @return El bitmap de les fotografies de l'espai
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * Mètode que permet modificar el bitmap de les fotografies de l'espai
     * @param bitmap Paràmetre que fa referència al bitmap de les fotografies
     */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Mètode que retorna una llista d'extres
     * @return Llista d'extres
     */
    public List<Extra> getExtras() {
        return extras;
    }

    /**
     * Mètode que permet modificar la llista d'extres
     * @param extras Paràmetre que fa referència a la llista d'extres que volem modificar
     */
    public void setExtras(List<Extra> extras) {
        this.extras = extras;
    }

    /**
     * Mètode que retorna tots els valors del atributs de l'espai
     * @return Els valors dels atributs de l'espai
     */


    public String toString () {
        return "Id: " + id + " Nom: " + nom + " Descripció: "
                + descripcio + " Àrea: " + area + " Direcció: " + direccio + " Provincia: " + provincia + " Preu: " + preu
                + " Serveis: " + extras + " Username: " + username;
    }
}

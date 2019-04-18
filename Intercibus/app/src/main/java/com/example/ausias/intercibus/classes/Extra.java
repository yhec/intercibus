package com.example.ausias.intercibus.classes;

/* Extres, contindrà tots els extres que pot tenir un Espai */

/**
 * Aquesta classe contindrà tots els extres que pot tenir un Espai
 * @author Antonio de Dios Durán
 * @version 1.0
 */
public class Extra {

    String nom;
    float preu;
    boolean marcat;

    /**
     * Constructor de la classe Extra
     * @param nom Paràmetre que fa referència al nom del extra
     * @param preu Paràmetre que fa referència al preu del extra
     */
    public Extra (String nom, float preu) {
        this.nom = nom;
        this.preu = preu;
        marcat = false;
    }

    /**
     * Mètode que retorna el nom de l'extra
     * @return El nom de l'extra
     */
    public String getNom() {
        return nom;
    }

    /**
     * Mètode que permet modificar el nom de l'extra
     * @param nom Paràmetre que fa referència al nom de l'extra
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Mètode que retorna el preu de l'extra
     * @return El preu de l'extra
     */
    public float getPreu() {
        return preu;
    }

    /**
     * Mètode que permet modificar el preu de l'extra
     * @param preu Paràmetre que fa referència al preu d'un extra
     */
    public void setPreu(float preu) {
        this.preu = preu;
    }

    /**
     * Mètode que indica si un extra es marca quan es fa la reserva
     * @return Un booleà que indica si un extra està marcat o no
     */
    public boolean isMarcat() {
        return marcat;
    }

    /**
     * Mètode que permet modificar si un extra està marcat o no
     * @param marcat Paràmetre que indica si un extra està seleccionat o no
     */
    public void setMarcat(boolean marcat) {
        this.marcat = marcat;
    }

    /**
     * Mètode que retorna els valors de tots els atributs de l'extra
     * @return Un string amb els valors de tots atributs
     */
    public String toString () {
        return nom + " " + preu + " €\n";
    }
}

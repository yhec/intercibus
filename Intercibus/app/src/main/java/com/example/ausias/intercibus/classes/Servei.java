package com.example.ausias.intercibus.classes;

/**
 * Aquesta classe serveix per crear els serveis que desprès oferiran els diferents espais
 * @author Antonio de Dios Durán
 * @version 1.0
 */

public class Servei {
    private String nomServei;

    /**
     * Constructor del servei
     * @param nomServei Paràmetre que fa referència al nom del servei
     */
    public Servei(String nomServei){

         this.nomServei=nomServei;
    }

    /**
     * Mètode que retorna el nom del servei
     * @return El nom del servei
     */
    public String getNomServei() {

        return nomServei;
    }


}

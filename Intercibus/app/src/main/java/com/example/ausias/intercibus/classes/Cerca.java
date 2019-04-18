package com.example.ausias.intercibus.classes;

import java.util.List;

/**
 * Aquesta classe serveix per crear un objecte cerca que conté la llista de serveis que hi ha en una determinada provincia
 * @author Jessica Miranda
 * @version 1.0
 */

public class Cerca {
    private List<String> serveis;
    private String provincia;

    /**
     * Constructor per la classe cerca
     * @param serveis Paràmetre que defineix la llista de serveis
     * @param provincia Paràmetre que defineix la provincia
     */
    public Cerca(List<String> serveis, String provincia) {
        this.serveis = serveis;
        this.provincia = provincia;
    }

    /**
     * Mètode que retorna la llista de serveis
     * @return Llista de serveis
     */
    public List<String> getServeis() {
        return serveis;
    }

    /**
     * Mètode que retorna la provincia
     * @return Nom de la provincia
     */
    public String getProvincia() {
        return provincia;
    }
}

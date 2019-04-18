package com.example.ausias.intercibus.classes;

import org.joda.time.DateTime;

/**
 * Aquesta classe serveix per emmagatzemar una data reservada que consta de una data d'inici i de sortida
 * @author Antonio de Dios Durán
 */

public class DataReservada {
    DateTime dataInici;
    DateTime dataSortida;

    /**
     * Constructor per data reservada
     * @param dataInici Paràmetre que defineix la data d'inici de la reserva
     * @param dataSortida Paràmetre que defineix la data de sortida de la reserva
     */
    public DataReservada (DateTime dataInici, DateTime dataSortida) {
        this.dataInici = dataInici;
        this.dataSortida = dataSortida;
    }

    /**
     * Mètode que retorna la data d'inici
     * @return La data d'inici de la reserva
     */
    public DateTime getDataInici() {
        return dataInici;
    }

    /**
     * Mètode que modifica la data d'inici
     * @param dataInici Paràmetre que defineix la data d'inici de la reserva
     */
    public void setDataInici(DateTime dataInici) {
        this.dataInici = dataInici;
    }

    /**
     * Mètode que retorna la data de sortida
     * @return La data de sortida de la reserva
     */
    public DateTime getDataSortida() {
        return dataSortida;
    }

    /**
     * Mètode que modifica la data de sortida
     * @param dataSortida Paràmetre que defineix la data de sortida de la reserva
     */
    public void setDataSortida(DateTime dataSortida) {
        this.dataSortida = dataSortida;
    }

    /**
     * Mètode que retorna tota la informació del objecte cerca
     * @return Tots els valors dels atributs definits del objecte cerca
     */
    public String toString () {
        return "Data Inici: " + dataInici + " Data Sortida: " + dataSortida;
    }
}

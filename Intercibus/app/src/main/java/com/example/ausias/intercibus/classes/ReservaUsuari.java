package com.example.ausias.intercibus.classes;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.joda.time.DateTime;

/**
 * Aquesta classe serveix per comprovar que la reserva que vol realitzar el usuari és correcta
 * @author Antonio de Dios Durán
 * @version 1.0
 */
public class ReservaUsuari {

    CalendarDay dataInici;
    CalendarDay dataSortida;
    DateTime inici;
    DateTime sortida;
    boolean dataIniciPosada;
    boolean dataSortidaPosada;
    boolean totOK;

    /**
     * Constructor per la reserva de l'usuari
     */
    public ReservaUsuari () {
        dataIniciPosada = false;
        dataSortidaPosada = false;
        totOK = false;
    }

    /**
     * Mètode que retorna la dara d'inici del calendar
     * @return La data d'inici
     */
    public CalendarDay getDataInici() {
        return dataInici;
    }

    /**
     * Mètode que permet modificar la data d'inici del calendari
     * @param dataInici Paràmetre que fa referència a la data d'inici modificada
     */
    public void setDataInici(CalendarDay dataInici) {
        this.dataInici = dataInici;
    }

    /**
     * Mètode que retorna la data de sortida del calendari
     * @return La data de sortida de la reserva
     */
    public CalendarDay getDataSortida() {
        return dataSortida;
    }

    /**
     * Mètode que permet modificar la data de sortida del calendari
     * @param dataSortida Paràmetre que fa referència a la data de sortida modificada
     */
    public void setDataSortida(CalendarDay dataSortida) {
        this.dataSortida = dataSortida;
    }

    /**
     * Mètode que retorna la data d'inici de la base de dades
     * @return La data d'inici
     */
    public DateTime getInici() {
        return inici;
    }

    /**
     * Mètode que permet modificar la data d'inici de la base de dades
     * @param inici Paràmetre que fa referència a la data d'inici
     */
    public void setInici(DateTime inici) {
        this.inici = inici;
    }

    /**
     * Mètode que retorna la data de sortida de la base de dades
     * @return La data de sortida
     */
    public DateTime getSortida() {
        return sortida;
    }

    /**
     * Mètode que permet modificar la data de sortida de la base de dades
     * @param sortida Paràmetre que fa referència a la data de sortida
     */
    public void setSortida(DateTime sortida) {
        this.sortida = sortida;
    }

    /**
     * Mètode que indica si la data d'inici ja està a la base de dades
     * @return Un booleà indicant si la data d'inici ja existeix
     */
    public boolean isDataIniciPosada() {
        return dataIniciPosada;
    }

    /**
     * Mètode que permet modificar si la data d'inici existeix o no
     * @param dataIniciPosada Paràmetre que fa referència a la data d'inici
     */
    public void setDataIniciPosada(boolean dataIniciPosada) {
        this.dataIniciPosada = dataIniciPosada;
    }

    /**
     * Mètode que indica si la data de sortida ja està a la base de dades
     * @return Un booleà indicant si la data de sortida ja existeix
     */
    public boolean isDataSortidaPosada() {
        return dataSortidaPosada;
    }

    /**
     * Mètode que permet modificar si la data de sortida existeix o no
     * @param dataSortidaPosada Paràmetre que fa referència a la data de sortida
     */
    public void setDataSortidaPosada(boolean dataSortidaPosada) {
        this.dataSortidaPosada = dataSortidaPosada;
    }

    /**
     * Mètode que indica si la reserva que ha realitzat el usuari es correcta
     * @return Un booleà que indica que la reserva és correcta o no
     */
    public boolean isTotOK() {
        return totOK;
    }

    /**
     * Mètode que permet modificar si la reserva de l'usuari es correcta o no
     * @param totOK Paràmetre que indica si la reserva és correcta o no
     */
    public void setTotOK(boolean totOK) {
        this.totOK = totOK;
    }

    /**
     * Mètode que retorna els valors de tots els atributs
     * @return Un String amb els valors de tots els atributs de la classe
     */
    public String toString () {
        return "DateTime entrada: " + inici + " DateTime sortida: " + sortida;
    }
}

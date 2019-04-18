package com.example.ausias.intercibus.classes;

import android.graphics.Bitmap;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

/**
 * Aquesta classe serveix per crear una reserva d'un espai
 * @author Antonio de Dios Durán
 * @version 1.0
 */
public class Reserva {
    int idEspai;
    DateTime inici;
    DateTime sortida;
    float preuTotal;
    String username;
    // Serveis contractats
    List<Extra> serveis;
    String nomEspai;
    Bitmap foto;
    int idReserva;
    float preu;
    String entradaReserva;
    String sortidaReserva;

    /**
     * Constructor per la reserva
     * @param idEspai Paràmetre que fa referència al id de l'espai
     * @param inici Paràmetre que fa referència a la data d'inici de la reserva
     * @param sortida Paràmetre que fa referència a la data de sortida de la reserva
     * @param preuTotal Paràmetre que fa referència al preu total de la reserva
     * @param username Paràmetre que fa referència al username de l'usuari
     * @param serveis Paràmetre que fa referència als serveis reservats de l'espai
     */
    public Reserva (int idEspai, DateTime inici, DateTime sortida, float preuTotal, String username, List<Extra> serveis) {
        this.inici = inici;
        this.sortida = sortida;
        this.idEspai = idEspai;
        this.preuTotal = preuTotal;
        this.username = username;
        this.serveis = serveis;
    }

    /**
     * Constructor per la reserva
     * @param nomEspai Paràmetre que fa referència al nom de l'espai reservat
     * @param foto Paràmetre que fa referència a la foto de l'espai reservat
     * @param idReserva Paràmetre que fa referència al id de la reserva
     * @param preu Paràmetre que fa referència al preu de l'espai reservat
     * @param entradaReserva Paràmetre que fa referència a la data d'inici de la reserva
     * @param sortidaReserva Paràmetre que fa referència a la data de sortida de la reserva
     */
    public Reserva(String nomEspai, Bitmap foto, int idReserva, float preu, String entradaReserva, String sortidaReserva){
        this.nomEspai=nomEspai;
        this.foto=foto;
        this.idReserva=idReserva;
        this.preu=preu;
        this.entradaReserva=entradaReserva;
        this.sortidaReserva=sortidaReserva;

    }

    /**
     * Constructor per la reserva
     * @param nomEspai Paràmetre que fa referència al nom de l'espai reservat
     * @param foto Paràmetre que fa referència a la foto de l'espai reservat
     * @param preuTotal Paràmetre que fa referència al preu de l'espai reservat
     * @param entradaReserva Paràmetre que fa referència a la data d'inici de la reserva
     * @param sortidaReserva Paràmetre que fa referència a la data de sortida de la reserva
     * @param serveis Paràmetre que fa referència als serveis reservats de l'espai
     */
    public Reserva(String nomEspai, Bitmap foto, float preuTotal, String entradaReserva, String sortidaReserva, List<Extra> serveis) {
        this.nomEspai = nomEspai;
        this.foto = foto;
        this.preuTotal = preuTotal;
        this.entradaReserva = entradaReserva;
        this.sortidaReserva = sortidaReserva;
        this.serveis = serveis;
    }

    /**
     * Mètode que retorna la data i l'hora d'entrada de la reserva
     * @return La data i l'hora d'entrada
     */
    public DateTime getInici() {
        return inici;
    }

    /**
     * Mètode que permet modificar la data i l'hora d'entrada
     * @param inici Paràmetre que fa referència a la data i l'hora d'entrada modificada
     */
    public void setInici(DateTime inici) {
        this.inici = inici;
    }

    /**
     * Mètode que retorna la data i l'hora de sortida de la reserva
     * @return La data i l'hora de sortida
     */
    public DateTime getSortida() {
        return sortida;
    }

    /**
     * Mètode que permet modificar la data i l'hora de sortida
     * @param sortida Paràmetre que fa referència a la data i l'hora de sortida modificada
     */
    public void setSortida(DateTime sortida) {
        this.sortida = sortida;
    }

    /**
     * Mètode que retorna el id de l'espai de la reserva
     * @return El id de l'espai reservat
     */
    public int getIdEspai() {
        return idEspai;
    }

    /**
     * Mètode que retorna el username de l'usuari que ha realitzat la reserva
     * @return El id del usuari
     */
    public String getUsername() {
        return username;
    }

    /**
     * Mètode que permet modificar el username de l'usuari que ha realitzat la reserva
     * @param username Paràmetre que fa referència al username de l'usuari modificat
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Mètode que retorna el preu total de la reserva
     * @return El preu total de la reserva
     */
    public float getPreuTotal() {
        return preuTotal;
    }

    /**
     * Mètode que permet modificar el preu total de la reserva
     * @param preuTotal Paràmetre que fa referència al preu total modificat
     */
    public void setPreuTotal(float preuTotal) {
        this.preuTotal = preuTotal;
    }


    /**
     * Mètode que permet modificar la llista de serveis demanats a la reserva
     * @param serveis Paràmetre que refèrencia a la llista de serveis modificats
     */
    public void setServeis(List<Extra> serveis) {
        this.serveis = serveis;
    }

    /**
     * Mètode que transforma el datetime que recull del calendari a un format datetime de l'SQL per guardar-lo en la base de dades
     * @param datetime Paràmetre que fa referència al datetime del calendari
     * @return Un String amb el datetime transformat
     */
    public String toSQLString (DateTime datetime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.print(datetime);
    }

    /**
     * Mètode que retorna el nom de l'espai on s'ha realitzat la reserva
     * @return El nom de l'espai
     */
    public String getNomEspai() {
        return nomEspai;
    }

    /**
     * Mètode que retorna la fotografia de l'espai en bits
     * @return La fotografia de l'espai
     */
    public Bitmap getFoto() {
        return foto;
    }

    /**
     * Mètode que retorna el id de la reserva realitzada
     * @return El id de la reserva
     */
    public int getIdReserva() {
        return idReserva;
    }

    /**
     * Mètode que retorna el preu de la reserva realitzada
     * @return El preu de la reserva
     */
    public float getPreu() {
        return preu;
    }

    /**
     * Mètode que retorna la data i l'hora d'entrada de la reserva
     * @return La data i l'hora de la reserva
     */
    public String getEntradaReserva() {
        return entradaReserva;
    }

    /**
     * Mètode que retorna una llista d'extres d'una reserva
     * @return Una llista d'extres
     */
    public List<Extra> getServeis() {
        return serveis;
    }

    /**
     * Mètode que retorna la data i l'hora de sortida de la reserva
     * @return La data i l'hora de la sortida
     */
    public String getSortidaReserva() {
        return sortidaReserva;
    }
}

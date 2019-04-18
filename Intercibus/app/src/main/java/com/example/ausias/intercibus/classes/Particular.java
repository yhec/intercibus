package com.example.ausias.intercibus.classes;

/* Classe per identificar a un Particular */

/**
 * Aquesta classe serveix per identificar a un usuari particular
 * @author Jessica Miranda
 * @version 1.0
 */
public class Particular extends Usuari {

	String cognom1;
	String cognom2;

	/**
	 * Constructor per el usuari particular
	 * @param nom Paràmetre que fa referència al nom del usuari particular
	 * @param cognom1 Paràmetre que fa referència al nom del usuari particular
	 * @param cognom2 Paràmetre que fa referència al primer cognom del usuari particular
	 * @param username Paràmetre que fa referència al segon cognom del usuari particular
	 * @param password Paràmetre que fa referència al password del usuari particular
	 * @param telefon Paràmetre que fa referència al telèfon del usuari particular
	 * @param email Paràmetre que fa referència al email del usuari particular
	 */
	public Particular(String nom, String cognom1, String cognom2, String username, String password, String telefon, String email) {

		super(nom, username, password, telefon, email);
		this.cognom1 = cognom1;
		this.cognom2 = cognom2;
	}

	/**
	 * Mètode que retorna el primer cognom de l'usuari particular
	 * @return Primer cognom de l'usuari particular
	 */
	public String getCognom1() {
		return cognom1;
	}

	/**
	 * Mètode que retorna el segon cognom de l'usuari particular
	 * @return Segon cognom de l'usuari particular
	 */
	public String getCognom2() {
		return cognom2;
	}


}

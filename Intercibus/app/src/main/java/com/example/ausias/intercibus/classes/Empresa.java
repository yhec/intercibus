package com.example.ausias.intercibus.classes;

/**
 * Aquesta classe serveix per identificar una Empresa
 * @author Jessica Miranda
 * @version 1.0
 */
public class Empresa extends Usuari {

	String nif;

	/**
	 * Constructor per l'empresa
	 * @param nif Paràmetre que defineix el nif de l'empresa
	 * @param nom Paràmetre que defineix el nom de l'empresa
	 * @param username Paràmetre que defineix el username de l'empresa
	 * @param password Paràmetre que defineix el password de l'empresa
	 * @param telefon Paràmetre que defineix el telèfon de l'empresa
	 * @param email Paràmetre que defineix email de l'empresa
	 */
	public Empresa(String nif, String nom, String username, String password, String telefon, String email) {
		super (nom, username, password, telefon, email);
		this.nif = nif;
	}

	/**
	 * Mètode que retorna el valor del nif d'una empresa
	 * @return El valor del nif de l'empresa
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Mètode per canviar el valor del nif d'una empresa
	 * @param nif Paràmetre que defineix el nif d'una empresa
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}
}

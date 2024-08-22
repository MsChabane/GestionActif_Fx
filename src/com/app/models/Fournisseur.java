package com.app.models;


public class Fournisseur {
	private int id; 
	public Fournisseur(int id, String nom, String prenom, String entreprise, String numRGC) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.entreprise = entreprise;
		this.numRGC = numRGC;
	}
	private String nom ,prenom,entreprise,numRGC;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEntreprise() {
		return entreprise;
	}
	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
	}
	public String getNumRGC() {
		return numRGC;
	}
	public void setNumRGC(String numRGC) {
		this.numRGC = numRGC;
	}
	@Override
	public String toString() {
		return entreprise+" ( "+ nom +" "+prenom+" )";
	}
}

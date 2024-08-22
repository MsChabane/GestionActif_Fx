package com.app.models;

public class Types {
	private int id;
	private String intitule;
	private int nbr;
	public Types(int id, String intitule, int nbr) {
		super();
		this.id = id;
		this.intitule = intitule;
		this.nbr = nbr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public int getNbr() {
		return nbr;
	}
	public void setNbr(int nbr) {
		this.nbr = nbr;
	}
	
}

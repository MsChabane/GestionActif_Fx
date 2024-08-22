package com.app.models;

public class Categorie {
	private String intitule;
	private int id;
	public Categorie(int  id, String intitule) {
		super();
		this.id = id;
		this.intitule = intitule;
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

	@Override
	public String toString() {
		return  intitule ;
	}
	
}

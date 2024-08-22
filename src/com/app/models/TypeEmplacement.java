package com.app.models;

public class TypeEmplacement {
	@Override
	public String toString() {
		return   intitule ;
	}
	private int id;
	private String intitule,mnemo;
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
	public TypeEmplacement(int id, String intitule,String mnemo) {
		this.id = id;
		this.intitule = intitule;
		this.mnemo=mnemo;
	}
	public TypeEmplacement(int id, String intitule) {
		super();
		this.id = id;
		this.intitule = intitule;
	}
	public String getMnemo() {
		return mnemo;
	}
	public void setMnemo(String mnemo) {
		this.mnemo = mnemo;
	}
}

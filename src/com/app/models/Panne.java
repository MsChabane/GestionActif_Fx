package com.app.models;

import java.util.Date;

public class Panne {
	private int id;
	private Date datepanne;
	private String intitule;
	private Types typePanne ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDatepanne() {
		return datepanne;
	}
	public void setDatepanne(Date datepanne) {
		this.datepanne = datepanne;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public Types getTypePanne() {
		return typePanne;
	}
	public void setTypePanne(Types typePanne) {
		this.typePanne = typePanne;
	}
	public Panne(int id, Date datepanne, String intitule, Types typePanne) {
		super();
		this.id = id;
		this.datepanne = datepanne;
		this.intitule = intitule;
		this.typePanne = typePanne;
	}
	
}

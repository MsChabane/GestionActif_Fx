package com.app.models;

import java.util.Date;

public class Emprunt {
	private String id;
	private Date datePret,dateRemis;
	private boolean estRetourner;
	private Actif actif;
	private EmplacementExterne emplacementExterne;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getDatePret() {
		return datePret;
	}
	public void setDatePret(Date datePret) {
		this.datePret = datePret;
	}
	public Date getDateRemis() {
		return dateRemis;
	}
	public void setDateRemis(Date dateRemis) {
		this.dateRemis = dateRemis;
	}
	public boolean isEstRetourner() {
		return estRetourner;
	}
	public void setEstRetourner(boolean estRetourner) {
		this.estRetourner = estRetourner;
	}
	public Actif getActif() {
		return actif;
	}
	public void setActif(Actif actif) {
		this.actif = actif;
	}
	public EmplacementExterne getEmplacementExterne() {
		return emplacementExterne;
	}
	public void setEmplacementExterne(EmplacementExterne emplacementExterne) {
		this.emplacementExterne = emplacementExterne;
	}
	public Emprunt(String id, Date datePret, Date dateRemis, boolean estRetourner, Actif actif,
			EmplacementExterne emplacementExterne) {
		super();
		this.id = id;
		this.datePret = datePret;
		this.dateRemis = dateRemis;
		this.estRetourner = estRetourner;
		this.actif = actif;
		this.emplacementExterne = emplacementExterne;
	}
	
	
}

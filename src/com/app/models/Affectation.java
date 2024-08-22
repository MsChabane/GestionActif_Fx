package com.app.models;

import java.util.Date;

public class Affectation {
	@Override
	public String toString() {
		return "Affectation [actif=" + actif.getId() + ", emplacementInterne=" + emplacementInterne .getId()+ ", dateAffec=" + dateAffec.toString()
				+ ", status=" + status + "]";
	}
	private Actif actif;
	private EmplacementInterne emplacementInterne;
	private Date dateAffec;
	private boolean status;
	public Actif getActif() {
		return actif;
	}
	public void setActif(Actif actif) {
		this.actif = actif;
	}
	public EmplacementInterne getEmplacementInterne() {
		return emplacementInterne;
	}
	public void setEmplacementInterne(EmplacementInterne emplacementInterne) {
		this.emplacementInterne = emplacementInterne;
	}
	public Date getDateAffec() {
		return dateAffec;
	}
	public void setDateAffec(Date dateAffec) {
		this.dateAffec = dateAffec;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Affectation(Actif actif, EmplacementInterne emplacementInterne, Date dateAffec, boolean status) {
		super();
		this.actif = actif;
		this.emplacementInterne = emplacementInterne;
		this.dateAffec = dateAffec;
		this.status = status;
	}
	
	
}

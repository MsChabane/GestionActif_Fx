package com.app.models;

import java.util.Date;

public class Maintenance {
	private String id,status;
	private Date dateDeb,dateFin;
	private Actif actif;
	private Types typeMaintenance ;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDateDeb() {
		return dateDeb;
	}
	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public Actif getActif() {
		return actif;
	}
	public void setActif(Actif actif) {
		this.actif = actif;
	}
	public Types getTypeMaintenance() {
		return typeMaintenance;
	}
	public void setTypeMaintenance(Types typeMaintenance) {
		this.typeMaintenance = typeMaintenance;
	}
	public Maintenance(String id, String status, Date dateDeb, Date dateFin, Actif actif, Types typeMaintenance) {
		super();
		this.id = id;
		this.status = status;
		this.dateDeb = dateDeb;
		this.dateFin = dateFin;
		this.actif = actif;
		this.typeMaintenance = typeMaintenance;
	}
}

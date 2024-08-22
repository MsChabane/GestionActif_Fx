package com.app.models;

import java.util.Date;

public class Operation {
	private Maintenance maintenance;
	private String id, intitule;
	private Date date;
	private Types typeOperation;
	private Operateur operateur;
	private Panne panne;
	public Maintenance getMaintenance() {
		return maintenance;
	}
	public void setMaintenance(Maintenance maintenance) {
		this.maintenance = maintenance;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Types getTypeOperation() {
		return typeOperation;
	}
	public void setTypeOperation(Types typeOperation) {
		this.typeOperation = typeOperation;
	}
	public Operateur getOperateur() {
		return operateur;
	}
	public void setOperateur(Operateur operateur) {
		this.operateur = operateur;
	}
	public Panne getPanne() {
		return panne;
	}
	public void setPanne(Panne panne) {
		this.panne = panne;
	}
	public Operation(Maintenance maintenance, String id, String intitule, Date date, Types typeOperation,
			Operateur operateur, Panne panne) {
		super();
		this.maintenance = maintenance;
		this.id = id;
		this.intitule = intitule;
		this.date = date;
		this.typeOperation = typeOperation;
		this.operateur = operateur;
		this.panne = panne;
	}
	
}

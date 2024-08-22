package com.app.models;

import java.util.Date;

public class Actif {
	private String id,desg,evaluation,status;
	private double valeur ;
	private  Date dateInvtr;
	private Categorie categorie;
	private Fournisseur fournisseur;
	private boolean estConsomable;
	public boolean isEstConsomable() {
		return estConsomable;
	}
	public void setEstConsomable(boolean estConsomable) {
		this.estConsomable = estConsomable;
	}
	public Actif(String id, String desg, String evaluation, String status, double valeur, Date dateInvtr, Categorie categorie,
			Fournisseur fournisseur,boolean estConsimable) {
		this.id = id;
		this.desg = desg;
		this.evaluation = evaluation;
		this.status = status;
		this.valeur = valeur;
		this.dateInvtr = dateInvtr;
		this.categorie = categorie;
		this.fournisseur = fournisseur;
		this.estConsomable=estConsimable;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public Fournisseur getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return   id + " " + desg + " " + dateInvtr ;
	}
	public String getDesg() {
		return desg;
	}
	public void setDesg(String desg) {
		this.desg = desg;
	}
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	public Actif(String id, String desg, Date dateInvtr) {
		this.id = id;
		this.desg = desg;
		this.dateInvtr = dateInvtr;
	}
	public Actif(String id, String desg) {
		this.id = id;
		this.desg = desg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getValeur() {
		return valeur;
	}
	public void setValeur(double valeur) {
		this.valeur = valeur;
	}
	public Date getDateInvtr() {
		return dateInvtr;
	}
	public void setDateInvtr(Date dateInvtr) {
		this.dateInvtr = dateInvtr;
	}
}

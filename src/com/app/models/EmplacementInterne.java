package com.app.models;

public class EmplacementInterne {
	private String id,intitule;
	@Override
	public String toString() {
		return id + " " + intitule;
	}

	private TypeEmplacement typeEmplacement;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TypeEmplacement getTypeEmplacement() {
		return typeEmplacement;
	}

	public void setTypeEmplacement(TypeEmplacement typeEmplacement) {
		this.typeEmplacement = typeEmplacement;
	}

	public EmplacementInterne(String id, String intitule, TypeEmplacement typeEmplacement) {
		super();
		this.id = id;
		this.intitule = intitule;
		this.typeEmplacement = typeEmplacement;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	

}

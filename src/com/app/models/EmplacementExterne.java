package com.app.models;

public class EmplacementExterne {
	private int id;
	private String intitle,location;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIntitle() {
		return intitle;
	}
	public void setIntitle(String intitle) {
		this.intitle = intitle;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public EmplacementExterne(int id, String intitle, String location) {
		this.id = id;
		this.intitle = intitle;
		this.location = location;
	}
	@Override
	public String toString() {
		
		return intitle;
	}
	
}

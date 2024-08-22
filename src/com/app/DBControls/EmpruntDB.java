package com.app.DBControls;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.app.models.Emprunt;

public class EmpruntDB {
	public static void add(Emprunt emprunt) throws SQLException {
		createId(emprunt);
		PreparedStatement stmt = DB.connect().prepareStatement("insert into emprunt (id_em,datePret_em,dateRemi_em,actif,emplacementExterne)values(?,?,?,?,?)");
		stmt.setString(1, emprunt.getId());
		stmt.setDate(2, new Date(emprunt.getDatePret().getTime()));
		stmt.setDate(3, new Date(emprunt.getDateRemis().getTime()));
		stmt.setString(4, emprunt.getActif().getId());
		stmt.setInt(5, emprunt.getEmplacementExterne().getId());
		stmt.executeUpdate();
	} 
	private static void createId(Emprunt emprunt) {
		emprunt.setId(new SimpleDateFormat("ddMMyyyy").format(emprunt.getDatePret()).concat(emprunt.getActif().getId()));	
	}
	public static void remis(Emprunt emprunt) throws SQLException {
		PreparedStatement stmt = DB.connect().prepareStatement("update emprunt  set dateRemi_em =?,estRetourner_em=true where id_em = ?");
		stmt.setDate(1, new Date(emprunt.getDateRemis().getTime()));
		stmt.setString(2, emprunt.getId());
		stmt.executeUpdate();
	}
	public static ResultSet getData() throws SQLException {
		return DB.connect().createStatement().executeQuery("SELECT id_em ,datePret_em ,dateRemi_em,estRetourner_em,actif,desg_ac,dateintr_ac,id_empEx,intitule_empEx,location "
				+ "from emprunt inner join actif on actif = id_ac inner Join emplacement_externe on emplacementExterne= id_empEx;");
	}
	public static void modify(Emprunt emprunt) throws SQLException {
		PreparedStatement stmt = DB.connect().prepareStatement("update emprunt  set dateRemi_em =? where id_em = ?");
		stmt.setDate(1, new Date(emprunt.getDateRemis().getTime()));
		stmt.setString(2, emprunt.getId());
		stmt.executeUpdate();
	}
	public static void delete(Emprunt emprunt) throws SQLException {
		PreparedStatement stmt = DB.connect().prepareStatement("delete from emprunt where id_em = ?");
		stmt.setString(1, emprunt.getId());
		stmt.executeUpdate();
	}
}

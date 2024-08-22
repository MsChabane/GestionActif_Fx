package com.app.DBControls;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.models.EmplacementExterne;


public class EmplacementExterneDB {
	
	
	
	public static void add(EmplacementExterne emplacement) throws SQLException {
		
		PreparedStatement stmt = DB.connect().prepareStatement("insert into emplacement_externe(intitule_empEx,location)value(?,?)");
		stmt.setString(1, emplacement.getIntitle());
		stmt.setString(2, emplacement.getLocation());
		stmt.executeUpdate();
	}
	public static void modifY(EmplacementExterne emplacement) throws SQLException {
		PreparedStatement stmt = DB.connect().prepareStatement("update  emplacement_externe set intitule_empEx = ? ,location = ?  where id_empEx=?");
		stmt.setString(1, emplacement.getIntitle());
		stmt.setString(2, emplacement.getLocation());
		stmt.setInt(3, emplacement.getId());
		stmt.executeUpdate();
	}
	public static List<EmplacementExterne>getData() throws SQLException{
		List<EmplacementExterne>emps = new ArrayList<EmplacementExterne>();
		ResultSet res= DB.connect().createStatement().executeQuery("select id_empEx,intitule_empEx,location "
				+ "from emplacement_externe   ");
		while(res.next()) {
			emps.add(new EmplacementExterne(res.getInt(1),res.getString(2), res.getString(3)));
		}
		return emps;
	}
}

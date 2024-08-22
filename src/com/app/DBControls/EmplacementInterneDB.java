package com.app.DBControls;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.models.EmplacementInterne;
import com.app.models.TypeEmplacement;

public class EmplacementInterneDB {
	
	public static void createId(EmplacementInterne emplacementInterne) throws SQLException {
		String id =emplacementInterne.getTypeEmplacement().getMnemo();
		//id=id.length()==4?id:
		PreparedStatement stmt = DB.connect().prepareStatement("select max(substr(id_emp,3))+1 from emplacement_interne where id_emp like ? ");
		stmt.setString(1, id+"%");
		ResultSet res = stmt.executeQuery();
		id=id.concat(res.next() && res.getString(1)!=null? String.format("%03d", Integer.parseInt(res.getString(1))):"000");
		emplacementInterne.setId(id);
	}
	
	public static void add(EmplacementInterne emplacement) throws SQLException {
		createId(emplacement);
		PreparedStatement stmt = DB.connect().prepareStatement("insert into emplacement_interne (id_emp,intitule_emp,type_emplacement)value(?,?,?)");
		stmt.setString(1, emplacement.getId());
		stmt.setString(2, emplacement.getIntitule());
		stmt.setInt(3, emplacement.getTypeEmplacement().getId());
		stmt.executeUpdate();
	}
	public static void modifY(EmplacementInterne emplacement) throws SQLException {
		PreparedStatement stmt = DB.connect().prepareStatement("update  emplacement_interne set intitule_emp = ?  where id_emp=?");
		stmt.setString(1, emplacement.getIntitule());
		stmt.setString(2, emplacement.getId());
		stmt.executeUpdate();
	}
	public static List<EmplacementInterne>getData() throws SQLException{
		List<EmplacementInterne>emps = new ArrayList<EmplacementInterne>();
		ResultSet res= DB.connect().createStatement().executeQuery("select id_emp,intitule_emp,type_emplacement,intitule_ty "
				+ "from emplacement_interne inner join typeemplacement on id_ty=type_emplacement ");
		while(res.next()) {
			emps.add(new EmplacementInterne(res.getString(1),res.getString(2),new TypeEmplacement(res.getInt(3), res.getString(4))));
		}
		return emps;
	}
}

package com.app.DBControls;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.models.Actif;
import com.app.models.Affectation;

public class AffectationDB {
	
	public static void add(Affectation affectation) throws SQLException {
		PreparedStatement stmt = DB.connect().prepareStatement("insert into affectation (actif,date_af,emplacement_interne)value(?,?,?)");
		stmt.setString(1, affectation.getActif().getId());
		stmt.setDate(2, new Date(affectation.getDateAffec().getTime()));
		stmt.setString(3, affectation.getEmplacementInterne().getId());
		stmt.executeUpdate();
	}
	public static ResultSet  getData( ) throws SQLException {
		return DB.connect().createStatement().executeQuery("select actif,desg_ac,date_af,emplacement_interne,"
				+ "intitule_emp,id_ty,intitule_ty from affectation inner JOIN actif on actif = id_ac inner join  "
				+ "emplacement_interne on emplacement_interne= id_emp inner join typeemplacement on type_emplacement=id_ty where status_af = true;");
	}
	public static Date getMaxDate(Actif actif) throws SQLException {
		String queryaff=" select max(date_af) from affectation where actif = ? ";
		String queryEmp=" select max(dateRemi_em) from emprunt where actif = ? ";
		Date dateAff=executeStmt(queryaff, actif.getId());
		Date dateEmp=executeStmt(queryEmp, actif.getId());
		Date dateInvrt= new Date(actif.getDateInvtr().getTime());
		ArrayList<Date>dates = new ArrayList<Date>();
		dates.add(dateAff);
		dates.add(dateEmp);
		dates.add(dateInvrt);
		return dates.stream().filter(e->e!=null).reduce(dateInvrt, (acc, date) ->acc.getTime()>date.getTime()?acc:date );
		//return dateAff!=null ? dateAff.getTime()>dateInvrt.getTime()?dateAff: dateInvrt :dateInvrt;
	
	}
	private static Date executeStmt(String query, String id) throws SQLException {
		PreparedStatement prp = DB.connect().prepareStatement(query);
		prp.setString(1,id);
		ResultSet  res = prp.executeQuery();
		return  (res.next())? res.getDate(1):null;
	} 
	public static Boolean isExist(Affectation affectation) throws SQLException {
		PreparedStatement stmt = DB.connect().prepareStatement("select actif ,date_af ,emplacement_interne ,status_af from affectation where actif =? and date_af = ? and emplacement_interne = ? ");
		stmt.setString(1, affectation.getActif().getId());
		stmt.setDate(2,new Date(affectation.getDateAffec().getTime()));
		stmt.setString(3,affectation.getEmplacementInterne().getId() );
		ResultSet res = stmt.executeQuery();
		return res.next()? Boolean.valueOf(res.getBoolean(4)):null;
	}
	public static boolean changeStatus(Affectation affectation) throws SQLException {
		PreparedStatement stmt = DB.connect().prepareStatement("update affectation set status_af =not status_af where actif =? and date_af = ? and emplacement_interne = ? ");
		stmt.setString(1, affectation.getActif().getId());
		stmt.setDate(2,new Date(affectation.getDateAffec().getTime()));
		stmt.setString(3,affectation.getEmplacementInterne().getId() );
		ResultSet res = stmt.executeQuery();
		return res.next()? res.getBoolean(1):null;
	}
	public static void dropLast(Affectation affectation) throws SQLException {
		PreparedStatement stmt = DB.connect().prepareStatement("update affectation set status_af =false where actif =? ");
		stmt.setString(1, affectation.getActif().getId());
		 stmt.executeUpdate();	
	}
	
	
}

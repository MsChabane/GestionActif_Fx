package com.app.DBControls;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


import com.app.models.Actif;


public class ActifDB {
	private static void createId(Actif actif) throws SQLException {
		String id = new SimpleDateFormat("ddMMyyyy").format(actif.getDateInvtr());
		PreparedStatement stmt = DB.connect().prepareStatement("select max(substr(id_ac,10))+1 from actif where id_ac like ?");
		stmt.setString(1, id+"%");
		ResultSet res= stmt.executeQuery();
		id=id.concat(actif.isEstConsomable()?"C":"N").concat(
				(res.next() && res.getString(1)!=null)?String.format("%03d", Integer.parseInt(res.getString(1))):"000");
		actif.setId(id);
	}
	public static void add(Actif actif) throws SQLException {
		createId(actif);
		PreparedStatement stmt = DB.connect().prepareStatement("insert into actif (id_ac,dateIntr_ac,desg_ac,valeur_ac,categorie,fournisseur,estConsomable)value(?,?,?,?,?,?,?)");
		stmt.setString(1, actif.getId());
		stmt.setDate(2, new Date(actif.getDateInvtr().getTime()));
		stmt.setString(3, actif.getDesg());
		stmt.setDouble(4, actif.getValeur());
		stmt.setInt(5, actif.getCategorie().getId());
		stmt.setInt(6, actif.getFournisseur().getId());
		stmt.setBoolean(7, actif.isEstConsomable());
		stmt.executeUpdate();
	}
	public static void modify(Actif actif ) throws SQLException {
		PreparedStatement stmt = DB.connect().prepareStatement("update actif set desg_ac=?,valeur_ac=?,categorie=?,fournisseur= ? where id_ac =?");
		stmt.setString(1, actif.getDesg());
		stmt.setDouble(2, actif.getValeur());
		stmt.setInt(3, actif.getCategorie().getId());
		stmt.setInt(4, actif.getFournisseur().getId());
		stmt.setString(5, actif.getId());
		stmt.executeUpdate();
	};
	
	public static ResultSet getData() throws SQLException {
		//List<Actif>actifs = new ArrayList<Actif>();
		ResultSet res = DB.connect().prepareCall("select  id_ac,desg_ac,evaluation,status_ac,valeur_ac,dateIntr_ac,id_fr,nom_fr,prenom_fr,entreprise_fr,id_ctg,intitule_ctg ,estConsomable from actif "
				+ "inner join fournisseur on fournisseur=id_fr inner join categorie on categorie = id_ctg where estSupp_ac=false;").executeQuery();
		/*while(res.next()) {
			actifs.add(
					new Actif(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getDouble(5), res.getDate(6),
							new Categorie(res.getInt(11), res.getString(12)),
							new Fournisseur(res.getInt(7), res.getString(8), res.getString(9), res.getString(10),null ),
							 res.getBoolean(13))
					);
		}*/
		return res;
	}
	public static void delete(Actif actif) throws SQLException {
		PreparedStatement stmt = DB.connect().prepareStatement("update actif set estSupp_ac = true where id_ac = ?");
		stmt.setString(1, actif.getId());
		stmt.executeUpdate();
	}
	public static void changeStatus(Actif actif) throws SQLException {
		PreparedStatement stmt = DB.connect().prepareStatement("update actif set status_ac = ? where id_ac = ? ");
		stmt.setString(1, actif.getStatus());
		stmt.setString(2,actif.getId());
		stmt.executeUpdate();
	}
	public static void changeEvaluation(Actif actif) throws SQLException {
		PreparedStatement stmt = DB.connect().prepareStatement("update actif set evaluation = ? where id_ac = ? ");
		stmt.setString(1, actif.getEvaluation());
		stmt.setString(2,actif.getId());
		stmt.executeUpdate();
	}
	public static ResultSet getDataByStatus(String status) throws SQLException {
		
		PreparedStatement stmt = DB.connect().prepareStatement("select  id_ac,desg_ac,dateIntr_ac from actif where status_ac = ? and estConsomable=false and estSupp_ac=false ; "
				);
		stmt.setString(1, status);
		ResultSet res = stmt.executeQuery();
		/*while(res.next()) {
			actifs.add(
					new Actif(res.getString(1), res.getString(2), res.getDate(3))
			);
		}*/
		return res;
	}	
}

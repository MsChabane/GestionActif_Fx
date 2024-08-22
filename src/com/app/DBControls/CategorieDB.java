package com.app.DBControls;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.models.Categorie;


public class CategorieDB {
	public static void add(Categorie categorie) throws SQLException {
		PreparedStatement prp =DB.connect().prepareStatement("insert into categorie (intitule_ctg) value (?)");
		prp.setString(1, categorie.getIntitule());
		prp.executeUpdate();
	};
	public static void modify(Categorie categorie) throws SQLException {
		PreparedStatement prp =DB.connect().prepareStatement("update categorie set intitule_ctg = ? where id_ctg= ? ");
		prp.setString(1, categorie.getIntitule());
		prp.setInt(2, categorie.getId());
		prp.executeUpdate();
	};
	public static List<Categorie> getData() throws SQLException {
		List<Categorie>cats = new ArrayList<Categorie>();
		ResultSet res = DB.connect().createStatement().executeQuery("select * from categorie");
		while(res.next()) {
			cats.add(new Categorie(res.getInt(1),res.getString(2)));
		}
		return cats;
		};
	public static void delete(Categorie categorie) throws SQLException {
		PreparedStatement prp =DB.connect().prepareStatement("delete from categorie where id_ctg= ? ");
		prp.setInt(1, categorie.getId());
		prp.executeUpdate();
	};
}

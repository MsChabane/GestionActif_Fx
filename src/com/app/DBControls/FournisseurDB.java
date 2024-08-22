package com.app.DBControls;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.models.Fournisseur;

public class FournisseurDB {
	public static void add(Fournisseur fournisseur) throws SQLException {
		PreparedStatement prp =DB.connect().prepareStatement("insert into fournisseur (nom_fr,prenom_fr,entreprise_fr,numRGC) value (?,?,?,?)");
		prp.setString(1, fournisseur.getNom());
		prp.setString(2, fournisseur.getPrenom());
		prp.setString(1, fournisseur.getEntreprise());
		prp.setString(1, fournisseur.getNumRGC());
		prp.executeUpdate();
	};
	public static void modify() {};
	public static List<Fournisseur> getData() throws SQLException {
		List<Fournisseur>frs=new ArrayList<Fournisseur>();
		ResultSet res = DB.connect().createStatement().executeQuery("select id_fr,nom_fr,prenom_fr,entreprise_fr,numRGC from fournisseur ");
		while(res.next()) {
			frs.add(new Fournisseur(res.getInt(1), res.getString(2), res.getString(3),res.getString(4),res.getString(5)));
		}
		return frs;
	};
	public static void delete() {};
}

package com.app.DBControls;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeEmplacementDB {
	public static ResultSet getData() throws SQLException {
		return DB.connect().prepareStatement("select * from typeemplacement ").executeQuery();
	}
}

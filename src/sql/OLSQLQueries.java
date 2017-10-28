package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OLSQLQueries {

	private Connection con;

	/* Creates a new MySQL querying object. Specific for only this Project */
	public OLSQLQueries(String host, String database, String user, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = null;
			con = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database, user, password);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			System.out.println("MySQL driver Exception");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/* Closes the Connection. */
	public void close() throws SQLException {
		con.close();
	}

	public ResultSet getShops() throws SQLException {
		PreparedStatement ps = con.prepareStatement(
				"SELECT longitude, latitude, storeid, MAX(categoryid) FROM shoplocation INNER JOIN shopinformation ON shoplocation.identifier = shopinformation.storeid INNER JOIN shopcategories ON name = spanishDesc GROUP BY storeid ORDER BY storeid; ");
		return ps.executeQuery();
	}

}
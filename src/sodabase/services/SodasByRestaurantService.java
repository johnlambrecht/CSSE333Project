package sodabase.services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import sodabase.ui.SodaByRestaurant;

public class SodasByRestaurantService {

	private DatabaseConnectionService dbService = null;

	public SodasByRestaurantService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean addSodaByRestaurant(String rest, String soda, double price) {
		CallableStatement cs = null;
		try {
			cs = dbService.getConnection().prepareCall("{call AddSells(?,?,?)}");
			
			if(rest!=null && soda!=null){
				cs.setString(1, soda);
				cs.setString(2, rest);
				cs.setDouble(3, price);
			}else {return false;}
			
			return cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Add Soda by Restaurant not implemented.");
		return false;
	}

	public ArrayList<SodaByRestaurant> getSodasByRestaurants(String rest, String soda, String price,
			boolean useGreaterThanEqual) {
		// TODO: Task 3 and Task 4
		// try {
		// Statement stmt = this.dbService.getConnection().createStatement();
		// String query = "SELECT Restaurant, Soda, Manufacturer,
		// RestaurantContact, Price \nFROM SodasByRestaurant\n";
		// if(price != null && !price.isEmpty()){
		// query += "WHERE Price = " + price;
		// }
		// ResultSet rs = stmt.executeQuery(query);
		// return parseResults(rs);
		// }
		// catch (SQLException ex) {
		// JOptionPane.showMessageDialog(null, "Failed to retrieve sodas by
		// restaurant.");
		// ex.printStackTrace();
		// return new ArrayList<SodaByRestaurant>();
		// }
		PreparedStatement stmt = null;
		Connection con = this.dbService.getConnection();

		try {
			String query = "SELECT Restaurant, Soda, Manufacturer, RestaurantContact, Price \nFROM SodasByRestaurant\n ";
			
			if (price != null && !price.isEmpty()) {
				query = buildParameterizedSqlStatementString(rest, soda, Double.parseDouble(price), useGreaterThanEqual);

				stmt = con.prepareStatement(query);
				if(rest !=null && soda !=null){
				stmt.setString(1, rest);
				stmt.setString(2, soda);
				stmt.setDouble(3, Double.parseDouble(price));
				}if(rest!=null){
					stmt.setString(1, rest);
					stmt.setDouble(2, Double.parseDouble(price));
				}else
					stmt.setDouble(1, Double.parseDouble(price));
			}else {
				stmt = con.prepareStatement(query);
			}

			ResultSet rs = stmt.executeQuery();
			return parseResults(rs);

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve sodas by restaurant.");
			ex.printStackTrace();
			return new ArrayList<SodaByRestaurant>();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
		}

	}

	/**
	 * Creates a string containing ? in the correct places in the SQL statement
	 * based on the filter information provided.
	 * 
	 * @param rest
	 *            - The restaurant to match
	 * @param soda
	 *            - The soda to match
	 * @param price
	 *            - The price to compare to
	 * @param useGreaterThanEqual
	 *            - If true, the prices returned should be greater than or equal
	 *            to what's given, otherwise less than or equal.
	 * @return A string representing the SQL statement to be executed
	 */
	private String buildParameterizedSqlStatementString(String rest, String soda, Double price,
			boolean useGreaterThanEqual) {
		String sqlStatement = "SELECT Restaurant, Soda, Manufacturer, RestaurantContact, Price \nFROM SodasByRestaurant\n";
		ArrayList<String> wheresToAdd = new ArrayList<String>();
		PreparedStatement stmt = null;
		Connection con = this.dbService.getConnection();
		
		if (rest != null) {
			wheresToAdd.add("Restaurant = ?");
		}
		if (soda != null) {
			wheresToAdd.add("Soda = ?");
		}
		if (price != null) {
			if (useGreaterThanEqual) {
				wheresToAdd.add("Price >= ?");
			} else {
				wheresToAdd.add("Price <= ?");
			}
		}
		boolean isFirst = true;
		while (wheresToAdd.size() > 0) {
			if (isFirst) {
				sqlStatement = sqlStatement + " WHERE " + wheresToAdd.remove(0);
				isFirst = false;
			} else {
				sqlStatement = sqlStatement + " AND " + wheresToAdd.remove(0);
			}
		}
		return sqlStatement;
	}

	private ArrayList<SodaByRestaurant> parseResults(ResultSet rs) {
		try {
			ArrayList<SodaByRestaurant> sodasByRestaurants = new ArrayList<SodaByRestaurant>();
			int restNameIndex = rs.findColumn("Restaurant");
			int sodaNameIndex = rs.findColumn("Soda");
			int manfIndex = rs.findColumn("Manufacturer");
			int restContactIndex = rs.findColumn("RestaurantContact");
			int priceIndex = rs.findColumn("Price");
			while (rs.next()) {
				sodasByRestaurants.add(new SodaByRestaurant(rs.getString(restNameIndex), rs.getString(sodaNameIndex),
						rs.getString(manfIndex), rs.getString(restContactIndex), rs.getDouble(priceIndex)));
			}
			System.out.println(sodasByRestaurants.size());
			return sodasByRestaurants;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving sodas by restaurants. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<SodaByRestaurant>();
		}

	}

}

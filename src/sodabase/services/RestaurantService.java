package sodabase.services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JOptionPane;

public class RestaurantService {

	private DatabaseConnectionService dbService = null;

	public RestaurantService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean addResturant(String restName, String addr, String contact) {
		// TODO: Task 5
		CallableStatement cs = null;
		try {
			cs = dbService.getConnection().prepareCall("{call AddRestaurant(?,?,?)}");
			
			if(restName!=null && addr!=null && contact !=null){
				cs.setString(1, restName);
				cs.setString(2, addr);
				cs.setString(3, contact);
			
			}else if(restName!=null&&addr!=null){
				cs.setString(1, restName);
				cs.setString(2, addr);
		
			}else if(restName!=null){
				cs.setString(1, restName);
			}
			
			return cs.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Add Restaurant not implemented.");
		return false;
	}

	public ArrayList<String> getRestaurants() {
		// DONE: Task 2
		ArrayList<String> rests = new ArrayList<String>();
		String query = "select Name From Rest";
		try {
			Statement stmt = this.dbService.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String restName = rs.getString("Name");
				rests.add(restName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rests;
	}
}

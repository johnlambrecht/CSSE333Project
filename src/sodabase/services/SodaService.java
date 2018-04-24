package sodabase.services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SodaService {

	private DatabaseConnectionService dbService = null;

	public SodaService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean addSoda(String sodaName, String manf) {
		CallableStatement cs = null;
		try {
			cs = dbService.getConnection().prepareCall("{call AddSoda(?,?)}");
			
			if(sodaName!=null && manf!=null){
				cs.setString(1, sodaName);
				cs.setString(2, manf);
				
			}else if(sodaName!=null){
				cs.setString(1, sodaName);
			}else {
				return false;
			}
			
			return cs.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Add Soda not implemented.");
		return false;
	}

	public ArrayList<String> getSodas() {
		// DONE: Task 2
		ArrayList<String> sodas = new ArrayList<String>();
		String query = "select Name From Soda";
		try {
			Statement stmt = this.dbService.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String sodaName = rs.getString("Name");
				sodas.add(sodaName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sodas;
	}
}

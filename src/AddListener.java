import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

import CarService.DatabaseConnectionService;

public class AddListener implements ActionListener{
	DatabaseConnectionService dbService;
	String dbName;
	
	public AddListener(DatabaseConnectionService dbService, String dbName) {
		this.dbService = dbService;
		this.dbName = dbName;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(dbName.equals("Car")) {
			CallableStatement cs = null;
			
			try {
				cs = this.dbService.getConnection().prepareCall("{ ? = call newCar(?,?,?,?,?,?,?)}" );
				cs.registerOutParameter(1, Types.INTEGER);
				cs.setString(2, "000001");
				cs.setString(3, "tempa");
				cs.setString(4, "tempaa");
				cs.setFloat(5, 15);
				cs.setInt(6, 15);
				cs.setString(7, "BMW");
				cs.setString(8, "Y");
				cs.execute();
				int returnValue = cs.getInt(1);
				if(returnValue == 1) {
					JOptionPane.showMessageDialog(null, "ERROR: Must enter a valid VIN");
				}else if(returnValue == 2) {
					JOptionPane.showMessageDialog(null, "ERROR: Must enter a valid Name");
				}else if(returnValue == 3) {
					JOptionPane.showMessageDialog(null, "ERROR: The VIN already exists");
				}else if(returnValue == 4) {
					JOptionPane.showMessageDialog(null, "ERROR: The manf does not exist");
				}
			} catch (SQLException e) {
				e.printStackTrace();
//				JOptionPane.showMessageDialog(null, "Add Restaurant not implemented.");
			}
		}
	}

}

package CarService;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

public class CarService {

	DatabaseConnectionService dbService;
	
	public CarService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public int add() {
		
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
					return 0;
				}else if(returnValue == 2) {
					JOptionPane.showMessageDialog(null, "ERROR: Must enter a valid Name");
					return 0;
				}else if(returnValue == 3) {
					JOptionPane.showMessageDialog(null, "ERROR: The VIN already exists");
					return 0;
				}else if(returnValue == 4) {
					JOptionPane.showMessageDialog(null, "ERROR: The manf does not exist");
					return 0;
				}
			} catch (SQLException e) {
				e.printStackTrace();
//				JOptionPane.showMessageDialog(null, "Add Restaurant not implemented.");
			}
		return 1;
	}
}

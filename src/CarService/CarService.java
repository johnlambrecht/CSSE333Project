package CarService;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CarService {

	DatabaseConnectionService dbService;
	
	public CarService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public void populateFrame(JFrame frame) {
		System.out.println("working with frame");
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		frame.setTitle("Add Car");
		JLabel lVin = new JLabel();
		lVin.setText("VIN");
		JTextField jVIN = new JTextField();
		String VIN = "";
		
		JLabel lColor = new JLabel();
		JTextField jColor = new JTextField();
		String color = "";
		
		JLabel lModel = new JLabel();
		JTextField jModel = new JTextField();
		String model = "";
		
		JLabel lMileage = new JLabel();
		JTextField jMileage = new JTextField();
		String mileage = "";
		
		JLabel lMSRP = new JLabel();
		JTextField jMSRP = new JTextField();
		String msrp = "";
		
		JLabel lManf = new JLabel();
		JTextField jmanf = new JTextField();
		String manf = "";
		
		JLabel lAvail = new JLabel();
		JTextField jAvail = new JTextField();
		String avail = "";
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
										.addComponent(lVin)
										.addComponent(jVIN));
		frame.setVisible(true);
	}
	public int add(String VIN, String color, String model, float mileage, int msrp, String manf, String avail) {
		
			CallableStatement cs = null;
			
			try {
				cs = this.dbService.getConnection().prepareCall("{ ? = call newCar(?,?,?,?,?,?,?)}" );
				cs.registerOutParameter(1, Types.INTEGER);
				cs.setString(2, VIN);
				cs.setString(3, color);
				cs.setString(4, model);
				cs.setFloat(5, mileage);
				cs.setInt(6, msrp);
				cs.setString(7, manf);
				cs.setString(8, avail);
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

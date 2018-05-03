package CarService;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MembershipService {

	DatabaseConnectionService dbService;
	
	public MembershipService(DatabaseConnectionService dbService2) {
		this.dbService = dbService2;
	}
	public void populateFrame() {
		JFrame frame = new JFrame();
		//System.out.println("working with frame");
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(2,1);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		frame.setTitle("Add Membership");
		
		
		JTextField jCust = new JTextField("CustomerID");
		panel.add(jCust);
		
		JTextField jManf = new JTextField("Manufacturer");
		panel.add(jManf);
		
		JTextField jName = new JTextField("Name");
		panel.add(jName);
		
		JTextField jType = new JTextField("Type");
		panel.add(jType);
		
		JButton doneButton = new JButton("DONE");
		
	
		
		class DoneListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				int cust = 0;
				if(!jCust.getText().equals("CustomerID")){
				 cust = Integer.parseInt(jCust.getText());
				}
				String manf = jManf.getText();
				
				
				
				String name = null;
				if(!jName.getText().equals("Name")){
				name = jName.getText();
				}
				
				String type = null;
				if(!jType.getText().equals("Type")){
				type = jType.getText();
				}
				
				add(name, type, cust,manf);
			}
			
		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);
	
}

	public int add(String name, String type, int customerID, String manf) {
		
		CallableStatement cs = null;
		
		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call newCar(?,?,?,?)}" );
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setString(3, type);
			cs.setInt(4, customerID);
			cs.setFloat(5, 15);
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
//			JOptionPane.showMessageDialog(null, "Add Restaurant not implemented.");
		}
	return 1;
}
}

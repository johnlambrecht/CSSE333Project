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

public class ServicesService {

	DatabaseConnectionService dbService;
	
	public ServicesService(DatabaseConnectionService dbService2) {
		this.dbService = dbService2;
	}
	public void populateFrame() {
		JFrame frame = new JFrame();
		//System.out.println("working with frame");
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(2,1);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		frame.setTitle("Add Service");
		
		
		JTextField jVIN = new JTextField("VIN");
		panel.add(jVIN);
		
		JTextField jName = new JTextField("Name");
		panel.add(jName);
		
		JTextField jAddress = new JTextField("Address");
		panel.add(jAddress);
		
		JTextField jDate = new JTextField("Date");
		panel.add(jDate);
		
		JTextField jType = new JTextField("Type");
		panel.add(jType);
		
		JButton doneButton = new JButton("DONE");
		
	
		
		class DoneListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String vin = jVIN.getText();
				
				
				String name = null;
				if(!jName.getText().equals("Name")){
				name = jName.getText();
				}
				
				
				String address = null;
				if(!jAddress.getText().equals("Address")){
				address = jAddress.getText();
				}
				
				String date = null;
				if(!jDate.getText().equals("Date")){
				date = jDate.getText();
				}
				
				String type = null;
				if(!jType.getText().equals("Type")){
				type = jType.getText();
				}
				
				
				
				add(vin,name,address,date,type);
			}
			
		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);
	
}

	public int add(String VIN, String name, String address, String date, String type) {
		
		CallableStatement cs = null;
		
		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call addService(?,?,?,?,?)}" );
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, VIN);
			cs.setString(3, name);
			cs.setString(4, address);
			cs.setString(5,date);
			cs.setString(6, type);
			cs.execute();
			int returnValue = cs.getInt(1);
			if(returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Must Enter a valid VIN");
				return 0;
			}else if(returnValue == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: Must Enter a valid Name");
				return 0;
			}else if(returnValue == 3) {
				JOptionPane.showMessageDialog(null, "ERROR: Must Enter a valid Address");
				return 0;
			}else if(returnValue == 4) {
				JOptionPane.showMessageDialog(null, "ERROR: The VIN is not valid");
				return 0;
			}else if(returnValue == 5) {
				JOptionPane.showMessageDialog(null, "ERROR: The Name or the Address is not valid");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Add Restaurant not implemented.");
		}
	return 1;
}
}

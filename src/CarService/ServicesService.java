package CarService;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
		GridLayout layout = new GridLayout(4,2);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		frame.setTitle("Add Service");
		
		
		JTextField jVIN = new JTextField("VIN");
		panel.add(jVIN);
		
		JTextField jName = new JTextField("Name");
		panel.add(jName);
	
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
				
				
				String type = null;
				if(!jType.getText().equals("Type")){
				type = jType.getText();
				}
				
				
				
				add(vin,name,type);
				frame.setVisible(false);
			}
			
		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);
	
}

	public int add(String VIN, String name, String type) {
		
		CallableStatement cs = null;
		
		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call addService(?,?,?)}" );
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, VIN);
			cs.setString(3, name);
			cs.setString(4, type);
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
	
	public void populateEditFrame(JFrame frame) {
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(2,1);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		frame.setTitle("Edit Service");
		
		JTextField jID = new JTextField("ID");
		panel.add(jID);
		
		JTextField jVIN = new JTextField("VIN");
		panel.add(jVIN);
		
		JTextField jName = new JTextField("Name");
		panel.add(jName);
		
		JTextField jType = new JTextField("Type");
		panel.add(jType);
		
		JButton doneButton = new JButton("DONE");
		
	
		
		class DoneListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				int id = -1;
				if(!jID.getText().equals("ID")){
					id = Integer.parseInt(jID.getText());
				}
				String vin = jVIN.getText();
				
				
				String name = null;
				if(!jName.getText().equals("Name")){
				name = jName.getText();
				}
				
				
				String type = null;
				if(!jType.getText().equals("Type")){
				type = jType.getText();
				}
				
				
				
				edit(id, vin,name,type);
				frame.setVisible(false);
			}
			
		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);
	
}

	public int edit(int id, String VIN, String name, String type) {
		
		CallableStatement cs = null;
		
		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call editService(?,?,?,?)}" );
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, id);
			cs.setString(3, VIN);
			cs.setString(4, name);
			cs.setString(5, type);
			cs.execute();
			int returnValue = cs.getInt(1);
			if(returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Must Enter a valid VIN");
				return 0;
			}else if(returnValue == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: Must Enter a valid Service Center Name");
				return 0;
			}else if(returnValue == 3) {
				JOptionPane.showMessageDialog(null, "ERROR: The VIN is not valid");
				return 0;
			}else if(returnValue == 4) {
				JOptionPane.showMessageDialog(null, "ERROR: The Name or the Address is not valid");
				return 0;
			}else if(returnValue == 5) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter an ID");
				return 0;
			}else if(returnValue == 6) {
				JOptionPane.showMessageDialog(null, "ERROR: Service does not exist");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Add Restaurant not implemented.");
		}
	return 1;
	}
	
}

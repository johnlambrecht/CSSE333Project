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

public class CustomerService {

	DatabaseConnectionService dbService;
	
	public CustomerService(DatabaseConnectionService dbService2) {
		this.dbService = dbService2;
	}
	public void populateFrame() {
		JFrame frame = new JFrame();
		//System.out.println("working with frame");
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(4,2);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		frame.setTitle("Add Customer");
		
		
		JTextField jPhoneNum = new JTextField("Phone Number");
		panel.add(jPhoneNum);
		
		JTextField jFName = new JTextField("First Name");
		panel.add(jFName);
		
		JTextField jMinit = new JTextField("Middle Initial");
		panel.add(jMinit);
		
		JTextField jLName = new JTextField("Last Name");
		panel.add(jLName);
		
		JTextField jAddress = new JTextField("Address");
		panel.add(jAddress);
		
		
		JButton doneButton = new JButton("DONE");
		
	
		
		class DoneListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				String phoneNum = null;
				if(!jPhoneNum.getText().equals("Phone Number")){
				 phoneNum = jPhoneNum.getText();
				}
				
				String lName = null;
				if(!jLName.getText().equals("Last Name")){
					lName = jLName.getText();
				}
				
				String minit = null;
				if(!jMinit.getText().equals("Middle Initial")){
					minit = jMinit.getText();
				}
				
				String fName = null;
				if(!jFName.getText().equals("First Name")){
					fName = jFName.getText();
				}
				
				String address = null;
				if(!jAddress.getText().equals("Address")){
					address = jAddress.getText();
				}
				
				
				add(phoneNum, fName,minit,lName, address);
				frame.setVisible(false);
			}
			
		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);
	
}

	public int add(String phoneNum, String fname, String minit, String lname, String address) {
		
		CallableStatement cs = null;
		
		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call addCustomer(?,?,?,?,?)}" );
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, phoneNum);
			cs.setString(3, fname);
			cs.setString(4, lname);
			cs.setString(5, minit);
			cs.setString(6, address);
			cs.execute();
			int returnValue = cs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Add Restaurant not implemented.");
		}
	return 1;
	}	
	public int delete(String VIN, JFrame frame) {
		CallableStatement cs = null;

		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call deleteCustomer(?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, VIN);
			cs.execute();
			int returnValue = cs.getInt(1);
			if (returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Please enter a valid Customer ID");
				return 0;
			} else if (returnValue == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: This customer does not exist");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		return 1;
	}
	
	public void populateDeleteFrame(JFrame frame) {
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(2,2);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		JLabel jVIN = new JLabel("CustomerID");
		panel.add(jVIN);
		JTextField tfVIN = new JTextField();
		panel.add(tfVIN);
		JButton doneButton = new JButton("DONE");

		class DoneListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String VIN = tfVIN.getText();
				delete(VIN, frame);
			}

		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);
	}
	public void populateEditFrame(JFrame frame) {
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(2, 1);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		frame.setTitle("Edit Customer");

		JTextField jCID = new JTextField("Customer ID");
		panel.add(jCID);

		JTextField jPhoneNum = new JTextField("Phone Number");
		panel.add(jPhoneNum);
		
		JTextField jFName = new JTextField("First Name");
		panel.add(jFName);
		
		JTextField jMinit = new JTextField("Middle Initial");
		panel.add(jMinit);
		
		JTextField jLName = new JTextField("Last Name");
		panel.add(jLName);
		
		JTextField jAddress = new JTextField("Address");
		panel.add(jAddress);

		JButton doneButton = new JButton("DONE");

		class DoneListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int CID = 0;
				if (!jCID.getText().equals("Customer ID")) {
					CID = Integer.parseInt(jCID.getText());
				}

				String phoneNum = null;
				if(!jPhoneNum.getText().equals("Phone Number")){
				 phoneNum = jPhoneNum.getText();
				}
				
				String lName = null;
				if(!jLName.getText().equals("Last Name")){
					lName = jLName.getText();
				}
				
				String minit = null;
				if(!jMinit.getText().equals("Middle Initial")){
					minit = jMinit.getText();
				}
				
				String fName = null;
				if(!jFName.getText().equals("First Name")){
					fName = jFName.getText();
				}
				
				String address = null;
				if(!jAddress.getText().equals("Address")){
					address = jAddress.getText();
				}
				
				
				edit(CID, phoneNum, fName,minit,lName, address);
				frame.setVisible(false);
			}
			
		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);
	
}

	public int edit(int customerID, String phoneNum, String fname, String minit, String lname, String address) {
		
		CallableStatement cs = null;
		
		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call editCustomer(?,?,?,?,?,?)}" );
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, customerID);
			cs.setString(3, phoneNum);
			cs.setString(4, fname);
			cs.setString(5, lname);
			cs.setString(6, minit);
			cs.setString(7, address);
			cs.execute();
			int returnValue = cs.getInt(1);
			if(returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Please enter a valid customer ID");
				return 0;
			}else if(returnValue == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: The customer does not exist");
				return 0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Add Restaurant not implemented.");
		}
	return 1;
	}	
}

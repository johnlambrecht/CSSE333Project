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

public class PersonService {

	DatabaseConnectionService dbService;

	public PersonService(DatabaseConnectionService dbService2) {
		this.dbService = dbService2;
	}

	// public void populateFrame() {
	// JFrame frame = new JFrame();
	// //System.out.println("working with frame");
	// JPanel panel = new JPanel();
	// GridLayout layout = new GridLayout(2,1);
	// panel.setLayout(layout);
	// frame.setSize(1000, 500);
	// frame.setTitle("Add Person");
	//
	//
	// JTextField jCust = new JTextField("CustomerID");
	// panel.add(jCust);
	//
	//
	// JButton doneButton = new JButton("DONE");
	//
	//
	//
	// class DoneListener implements ActionListener{
	//
	// @Override
	// public void actionPerformed(ActionEvent arg0) {
	//
	// int custID = 0;
	// if(!jCust.getText().equals("CustomerID")){
	// custID = Integer.parseInt(jCust.getText());
	// }
	//
	// add(custID);
	// frame.setVisible(false);
	// }
	//
	// }
	// DoneListener doneListener = new DoneListener();
	// doneButton.addActionListener(doneListener);
	// panel.add(doneButton);
	// frame.add(panel);
	// frame.setVisible(true);
	//
	// }
	//
	// public int add(int customerID) {
	//
	// CallableStatement cs = null;
	//
	// try {
	// cs = this.dbService.getConnection().prepareCall("{ ? = call
	// addCustomer(?)}" );
	// cs.registerOutParameter(1, Types.INTEGER);
	// cs.setInt(2, customerID);
	// cs.execute();
	// int returnValue = cs.getInt(1);
	// if(returnValue == 1) {
	// JOptionPane.showMessageDialog(null, "ERROR");
	// return 0;
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	//// JOptionPane.showMessageDialog(null, "Add Restaurant not implemented.");
	// }
	// return 1;
	// }
	public void populateEditFrame(JFrame frame) {// System.out.println("working
		// with frame");
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(2, 1);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		frame.setTitle("Edit Membership");

		JTextField jPhoneNum = new JTextField("Phone Number");
		panel.add(jPhoneNum);

		JTextField jFName = new JTextField("First Name");
		panel.add(jFName);

		JTextField jLName = new JTextField("Last Name");
		panel.add(jLName);

		JTextField jMinit = new JTextField("Middle Initial");
		panel.add(jMinit);

		JTextField jAddress = new JTextField("Address");
		panel.add(jAddress);

		JButton doneButton = new JButton("DONE");

		class DoneListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int cust = 0;
				if (!jPhoneNum.getText().equals("Phone Number")) {
					cust = Integer.parseInt(jPhoneNum.getText());
				}
				String fName = jFName.getText();
				String lName = jLName.getText();

				String address = null;
				if (!jAddress.getText().equals("Address")) {
					address = jAddress.getText();
				}

				String minit = null;
				if (!jMinit.getText().equals("Middle Initial")) {
					minit = jMinit.getText();
				}

				int phoneNumber = 0;
				if(!jPhoneNum.getText().equals("Phone Number")){
					phoneNumber = Integer.parseInt(jPhoneNum.getText());
				}

				edit(fName, lName, phoneNumber,minit,address);
				frame.setVisible(false);
			}

		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);

	}

	public int edit(String FName, String LName, int phoneNumber, String Minit, String address) {

		CallableStatement cs = null;

		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call editPerson(?,?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			if (phoneNumber != 0) {
				cs.setString(2, null);
			} else {
				cs.setInt(2, phoneNumber);
			}
			cs.setString(3, FName);
			cs.setString(4, LName);
			cs.setString(5, Minit);
			cs.setString(6, address);
			cs.execute();
			int returnValue = cs.getInt(1);
			if (returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter a first name");
				return 0;
			} else if (returnValue == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter a last name");
				return 0;
			} else if (returnValue == 3) {
				JOptionPane.showMessageDialog(null, "ERROR: Person does not exist");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// JOptionPane.showMessageDialog(null, "Add Restaurant not
			// implemented.");
		}
		return 1;
	}
}

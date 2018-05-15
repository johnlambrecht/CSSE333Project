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

public class SalesPersonService {
	DatabaseConnectionService dbService;

	public SalesPersonService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public void populateFrame(JFrame frame) {
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(2, 1);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		frame.setTitle("Add Salesperson");

		JTextField jEID = new JTextField("Employee ID");
		panel.add(jEID);

		JTextField jWorksFor = new JTextField("Dealership");
		panel.add(jWorksFor);

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
				int eID = 0;
				if (!jEID.getText().equals("Employee ID")) {
					eID = Integer.parseInt(jEID.getText());
				}

				String worksFor = null;
				if (!jWorksFor.getText().equals("Dealership")) {
					worksFor = jWorksFor.getText();
				}

				int phoneNum = 0;
				if (!jPhoneNum.getText().equals("Phone Number")) {
					phoneNum = Integer.parseInt(jPhoneNum.getText());
				}

				String lName = null;
				if (!jLName.getText().equals("Last Name")) {
					lName = jLName.getText();
				}

				String minit = null;
				if (!jMinit.getText().equals("Middle Initial")) {
					minit = jMinit.getText();
				}

				String fName = null;
				if (!jFName.getText().equals("First Name")) {
					fName = jFName.getText();
				}

				String address = null;
				if (!jAddress.getText().equals("Address")) {
					address = jAddress.getText();
				}

				add(eID, worksFor, phoneNum, fName, minit, lName, address);
				frame.setVisible(false);
			}

		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);

	}

	public int add(int eID, String dealership, int phoneNum, String fName, String minit, String lName, String address) {

		CallableStatement cs = null;

		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call addSalesPerson(?,?,?,?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, eID);
			cs.setString(3, dealership);
			cs.setInt(4, phoneNum);
			cs.setString(5, fName);
			cs.setString(6, lName);
			cs.setString(7, minit);
			cs.setString(8, address);
			cs.execute();
			int returnValue = cs.getInt(1);
			if (returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter an EmployeeID");
				return 0;
			} else if (returnValue == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter a dealership");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// JOptionPane.showMessageDialog(null, "Add Restaurant not
			// implemented.");
		}
		return 1;
	}

	public void populateEditFrame(JFrame frame) {
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(2, 1);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		frame.setTitle("Edit Membership");

		JTextField jEID = new JTextField("Employee ID");
		panel.add(jEID);

		JTextField jWorksFor = new JTextField("Service Center");
		panel.add(jWorksFor);

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

				int EID = 0;
				if (!jEID.getText().equals("Employee ID")) {
					EID = Integer.parseInt(jEID.getText());
				}

				String worksFor = null;
				if (!jWorksFor.getText().equals("Service Center")) {
					worksFor = jWorksFor.getText();
				}

				int phoneNum = 0;
				if (!jPhoneNum.getText().equals("Phone Number")) {
					phoneNum = Integer.parseInt(jPhoneNum.getText());
				}

				String lName = null;
				if (!jLName.getText().equals("Last Name")) {
					lName = jLName.getText();
				}

				String minit = null;
				if (!jMinit.getText().equals("Middle Initial")) {
					minit = jMinit.getText();
				}

				String fName = null;
				if (!jFName.getText().equals("First Name")) {
					fName = jFName.getText();
				}

				String address = null;
				if (!jAddress.getText().equals("Address")) {
					address = jAddress.getText();
				}

				edit(EID, worksFor, phoneNum, fName, lName, minit, address);
				frame.setVisible(false);
			}

		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);

	}

	public int edit(int eID, String worksFor, int phoneNum, String fName, String lName, String minit, String address) {

		CallableStatement cs = null;

		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call editSalesPerson(?,?,?,?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, eID);
			cs.setString(3, worksFor);
			cs.setInt(4, phoneNum);
			cs.setString(5, fName);
			cs.setString(6, lName);
			cs.setString(7, lName);
			cs.setString(8, address);

			cs.execute();
			int returnValue = cs.getInt(1);
			if (returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter an Employee ID");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;

	}


	public int delete(int EID, JFrame frame) {
		CallableStatement cs = null;

		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call deleteSalesPerson(?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, EID);
			cs.execute();
			int returnValue = cs.getInt(1);
			if (returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Please enter a valid Employee ID");
				return 0;
			} else if (returnValue == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: This employee does not exist");
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
		GridLayout layout = new GridLayout(2, 2);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		JLabel lEID = new JLabel("Employee ID");
		panel.add(lEID);
		JTextField jEID = new JTextField();
		panel.add(jEID);
		JButton doneButton = new JButton("DONE");

		class DoneListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int EID = Integer.parseInt(jEID.getText());
				delete(EID, frame);
			}

		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);

	}

}

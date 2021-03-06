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

public class MembershipService {

	DatabaseConnectionService dbService;

	public MembershipService(DatabaseConnectionService dbService2) {
		this.dbService = dbService2;
	}

	public void populateFrame() {
		JFrame frame = new JFrame();
		// System.out.println("working with frame");
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(2, 1);
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

		class DoneListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int cust = 0;
				if (!jCust.getText().equals("CustomerID")) {
					cust = Integer.parseInt(jCust.getText());
				}
				String manf = jManf.getText();

				String name = null;
				if (!jName.getText().equals("Name")) {
					name = jName.getText();
				}

				String type = null;
				if (!jType.getText().equals("Type")) {
					type = jType.getText();
				}

				add(name, type, cust, manf);
				frame.setVisible(false);
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
			cs = this.dbService.getConnection().prepareCall("{ ? = call addMember(?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setString(3, type);
			cs.setInt(4, customerID);
			cs.setString(5, manf);
			cs.execute();
			int returnValue = cs.getInt(1);
			if (returnValue == 1) {
				JOptionPane.showMessageDialog(null, "Must enter a valid CustomerID");
				return 0;
			}
			if (returnValue == 2) {
				JOptionPane.showMessageDialog(null,
						"Must enter a valid Manufacturer");
				return 0;
			}
			if (returnValue == 3) {
				JOptionPane.showMessageDialog(null,
						"The CustomerID does not exist");
				return 0;
			}
			if (returnValue == 4) {
				JOptionPane.showMessageDialog(null,
						"The manufacturer " + manf + " does not exist");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// JOptionPane.showMessageDialog(null, "Add Restaurant not
			// implemented.");
		}
		return 1;
	}

	public int delete(String ID, JFrame frame) {
		CallableStatement cs = null;

		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call deleteMembership(?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, ID);
			cs.execute();
			int returnValue = cs.getInt(1);
			if (returnValue == 1) {
				JOptionPane.showMessageDialog(null,
						"ERROR: This membership has already been deleted from the database");
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
		JLabel jVIN = new JLabel("ID");
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

	public void populateEditFrame(JFrame frame) {// System.out.println("working
													// with frame");
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(2, 1);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		frame.setTitle("Edit Membership");
		
		JTextField jID = new JTextField("ID");
		panel.add(jID);

		JTextField jCust = new JTextField("CustomerID");
		panel.add(jCust);

		JTextField jManf = new JTextField("Manufacturer");
		panel.add(jManf);

		JTextField jName = new JTextField("Name");
		panel.add(jName);

		JTextField jType = new JTextField("Type");
		panel.add(jType);

		JButton doneButton = new JButton("DONE");

		class DoneListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int id = 0;
				if (!jID.getText().equals("ID")) {
					id = Integer.parseInt(jID.getText());
				}
				
				int cust = 0;
				if (!jCust.getText().equals("CustomerID")) {
					cust = Integer.parseInt(jCust.getText());
				}
				String manf = jManf.getText();

				String name = null;
				if (!jName.getText().equals("Name")) {
					name = jName.getText();
				}

				String type = null;
				if (!jType.getText().equals("Type")) {
					type = jType.getText();
				}

				edit(id,name, type, cust, manf);
				frame.setVisible(false);
			}

		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);

	}

	public int edit(int id,String name, String type, int customerID, String manf) {

		CallableStatement cs = null;

		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call editMembership(?,?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, id);
			cs.setString(3, name);
			cs.setString(4, type);
			cs.setInt(5, customerID);
			cs.setString(6, manf);
			cs.execute();
			int returnValue = cs.getInt(1);
			if (returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter an ID");
				return 0;
			}else if (returnValue == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: Membership does not exist");
				return 0;
			}else if (returnValue == 3) {
				JOptionPane.showMessageDialog(null, "ERROR: Customer does not exist");
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

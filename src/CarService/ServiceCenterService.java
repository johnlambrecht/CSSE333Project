package CarService;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServiceCenterService {

	DatabaseConnectionService dbService;

	public ServiceCenterService(DatabaseConnectionService dbService) {
		this.dbService = dbService;

	}

	public void populateAddFrame() {
		JFrame frame = new JFrame();
		// System.out.println("working with frame");
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(7, 1);
		panel.setLayout(layout);
		frame.setSize(1000, 500);

		// JLabel lColor = new JLabel();
		// lColor.setText("Color");
		JTextField jName = new JTextField("Name");
		// panel.add(lColor);
		panel.add(jName);

		// JLabel lModel = new JLabel();
		JTextField jAddress = new JTextField("Address");
		// panel.add(lModel);
		panel.add(jAddress);

		JButton doneButton = new JButton("DONE");

		class DoneListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String name = jName.getText();
				String address = jAddress.getText();

				add(name, address, frame);

			}

		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);
	}

	public int add(String name, String address, JFrame frame) {

		CallableStatement cs = null;

		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call addServiceCenter(?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setString(3, address);

			cs.execute();
			int returnValue = cs.getInt(1);
			if (returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter a Name");
				return 0;
			} else if (returnValue == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter an Address");
				return 0;
			} else if (returnValue == 3) {
				JOptionPane.showMessageDialog(null, "ERROR: The Service Center already exists");
				return 0;
			} else if (returnValue == 4) {
				JOptionPane.showMessageDialog(null, "ERROR: There is already a Service Center at this address");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		frame.setVisible(false);

		return 1;
	}

	public int delete(String VIN, JFrame frame) {
		CallableStatement cs = null;

		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call deleteCar(?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, VIN);
			cs.execute();
			int returnValue = cs.getInt(1);
			if (returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: This car has already been sold");
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
		frame.setTitle("Add Car");
		JLabel jVIN = new JLabel(" VIN");
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
		GridLayout layout = new GridLayout(7, 1);
		panel.setLayout(layout);
		frame.setSize(1000, 500);

		JTextField jName = new JTextField("Name");
		panel.add(jName);

		JTextField jAddress = new JTextField("Address");
		panel.add(jAddress);

		JButton doneButton = new JButton("DONE");

		class DoneListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String name = null;
				if (!jName.getText().equals("Name")) {
					name = jName.getText();
				}
				String address = null;
				if (!jAddress.getText().equals("Address")) {
					address = jAddress.getText();
				}

				edit(name, address, frame);

			}

		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);
	}

	public int edit(String name, String address, JFrame frame) {

		CallableStatement cs = null;

		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call editServiceCenter(?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setString(3, address);

			cs.execute();
			int returnValue = cs.getInt(1);
			if (returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter a Name");
				return 0;
			} else if (returnValue == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter an Address");
				return 0;
			} else if (returnValue == 3) {
				JOptionPane.showMessageDialog(null, "ERROR: The Service Center does not exist");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		frame.setVisible(false);

		return 1;

	}
}

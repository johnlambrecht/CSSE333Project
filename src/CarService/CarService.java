package CarService;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class CarService {

	DatabaseConnectionService dbService;

	public CarService(DatabaseConnectionService dbService) {
		this.dbService = dbService;

	}

	public void populateFrame() {
		JFrame frame = new JFrame();
		System.out.println("working with frame");
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(7, 1);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		frame.setTitle("Add Car");
		// JLabel lVin = new JLabel();
		// lVin.setText("VIN");
		JTextField jVIN = new JTextField("VIN");
		// panel.add(lVin);
		panel.add(jVIN);

		// JLabel lColor = new JLabel();
		// lColor.setText("Color");
		JTextField jColor = new JTextField("Color");
		// panel.add(lColor);
		panel.add(jColor);

		// JLabel lModel = new JLabel();
		JTextField jModel = new JTextField("Model");
		// panel.add(lModel);
		panel.add(jModel);

		// JLabel lMileage = new JLabel();
		JTextField jMileage = new JTextField("Mileage");
		// panel.add(lMileage);
		panel.add(jMileage);

		// JLabel lMSRP = new JLabel();
		JTextField jMSRP = new JTextField("MSRP");
		// panel.add(lMSRP);
		panel.add(jMSRP);

		// JLabel lManf = new JLabel();
		JTextField jmanf = new JTextField("Manufacturer");
		// panel.add(lManf);
		panel.add(jmanf);

		// JLabel lAvail = new JLabel();
		JTextField jAvail = new JTextField("Availability");
		// panel.add(lAvail);
		panel.add(jAvail);

		JButton doneButton = new JButton("DONE");

		class DoneListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String VIN = jVIN.getText();
				String color = null;
				if (!jColor.getText().equals("Color")) {
					color = jColor.getText();
				}
				String model = null;
				
				if (!jModel.getText().equals("Model")) {
					model = jModel.getText();
				}

				float mileage = 0;
				if (!jMileage.getText().equals("Mileage")) {
					mileage = Float.parseFloat(jMileage.getText());
				}
				int msrp = 0;
				if (!jMSRP.getText().equals("MSRP")) {
					msrp = Integer.parseInt(jMSRP.getText());
				}
				String manf = jmanf.getText();
				String avail = "Y";
				if (!jAvail.getText().equals("Availability"))
					;
				avail = jAvail.getText();

				add(VIN, color, model, mileage, msrp, manf, avail, frame);
				frame.setVisible(false);
			}

		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);
	}

	public int add(String VIN, String color, String model, float mileage, int msrp, String manf, String avail,
			JFrame frame) {

		CallableStatement cs = null;

		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call addCar(?,?,?,?,?,?,?)}");
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
			if (returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter a valid VIN");
				return 0;
			} else if (returnValue == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter a valid Name");
				return 0;
			} else if (returnValue == 3) {
				JOptionPane.showMessageDialog(null, "ERROR: The VIN already exists");
				return 0;
			} else if (returnValue == 4) {
				JOptionPane.showMessageDialog(null, "ERROR: The manf does not exist");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		frame.setVisible(false);

		return 1;
	}
	
	public int delete() {
		
		return 1;
	}
	
	public void populateDeleteFrame(JFrame frame) {
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(2,1);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		frame.setTitle("Add Car");
		JTextField jVIN = new JTextField("      VIN");
		panel.add(jVIN);
	}
}

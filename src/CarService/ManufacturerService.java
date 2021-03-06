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

public class ManufacturerService {

	DatabaseConnectionService dbService;
	
	public ManufacturerService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
		public void populateFrame() {
			JFrame frame = new JFrame();

			JPanel panel = new JPanel();
			GridLayout layout = new GridLayout(7,1);
			panel.setLayout(layout);
			frame.setSize(1000, 500);
			frame.setTitle("Add Manufacturer");
			
			
			JTextField jName = new JTextField("Name");
			panel.add(jName);

			
			JButton doneButton = new JButton("DONE");
			
		
			
			class DoneListener implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String name = jName.getText();
					
					
					add(name);
					frame.setVisible(false);
				}
				
			}
			DoneListener doneListener = new DoneListener();
			doneButton.addActionListener(doneListener);
			panel.add(doneButton);
			frame.add(panel);
			frame.setVisible(true);
		
	}

	public int add(String name) {
		
		CallableStatement cs = null;
		
		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call addManufacturer(?)}" );
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.execute();
			int returnValue = cs.getInt(1);
			if(returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter a valid Name");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Add Restaurant not implemented.");
		}
	return 1;
	}
	public int delete(String VIN, JFrame frame) {
		CallableStatement cs = null;

		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call deleteManufacturer(?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, VIN);
			cs.execute();
			int returnValue = cs.getInt(1);
			if (returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: This manufacturer has already been deleted from the database");
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
		JLabel jVIN = new JLabel("Name");
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
	public void populateEditFrame(JFrame editFrame) {
		JOptionPane.showMessageDialog(editFrame, "Manufacturers cannot be edited","Error",JOptionPane.ERROR_MESSAGE);
		
	}
}

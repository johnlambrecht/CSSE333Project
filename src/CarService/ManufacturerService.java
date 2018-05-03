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
}

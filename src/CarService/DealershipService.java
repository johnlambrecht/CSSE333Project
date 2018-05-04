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

public class DealershipService {

	DatabaseConnectionService dbService;
	
	public DealershipService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
		public void populateFrame() {
			JFrame frame = new JFrame();
			//System.out.println("working with frame");
			JPanel panel = new JPanel();
			GridLayout layout = new GridLayout(2,1);
			panel.setLayout(layout);
			frame.setSize(1000, 500);
			frame.setTitle("Add Dealership");
			
			
			
			JTextField jName = new JTextField("Name");
			panel.add(jName);
			
			JTextField jAddress = new JTextField("Address");
			panel.add(jAddress);
			
			JButton doneButton = new JButton("DONE");
			
		
			
			class DoneListener implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String name = jName.getText();
					String address = jAddress.getText();
					
					
					add(name,address);
					frame.setVisible(false);
				}
				
			}
			DoneListener doneListener = new DoneListener();
			doneButton.addActionListener(doneListener);
			panel.add(doneButton);
			frame.add(panel);
			frame.setVisible(true);
		
	}
	

	public int add(String name, String address) {
		
		CallableStatement cs = null;
		
		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call addDealership(?,?)}" );
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setString(3, address);
			cs.execute();
			int returnValue = cs.getInt(1);
			if(returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter a valid Name");
				return 0;
			}else if(returnValue == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter a valid Address");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Add Restaurant not implemented.");
		}
	return 1;
}
}

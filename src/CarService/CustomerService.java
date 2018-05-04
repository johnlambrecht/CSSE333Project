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

public class CustomerService {

	DatabaseConnectionService dbService;
	
	public CustomerService(DatabaseConnectionService dbService2) {
		this.dbService = dbService2;
	}
	public void populateFrame() {
		JFrame frame = new JFrame();
		//System.out.println("working with frame");
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(2,1);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		frame.setTitle("Add Customer");
		
		
		JTextField jCust = new JTextField("CustomerID");
		panel.add(jCust);
		
		
		JButton doneButton = new JButton("DONE");
		
	
		
		class DoneListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				int custID = 0;
				if(!jCust.getText().equals("CustomerID")){
				 custID = Integer.parseInt(jCust.getText());
				}
				
				add(custID);
				frame.setVisible(false);
			}
			
		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);
	
}

	public int add(int customerID) {
		
		CallableStatement cs = null;
		
		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call addCustomer(?)}" );
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, customerID);
			cs.execute();
			int returnValue = cs.getInt(1);
			if(returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Add Restaurant not implemented.");
		}
	return 1;
}
}

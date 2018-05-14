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

import CarService.DatabaseConnectionService;

public class SalesPersonService {
	DatabaseConnectionService dbService;

	public SalesPersonService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public void populateFrame(JFrame frame) {
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(2,1);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		frame.setTitle("Add Salesperson");
		
		
		
		JTextField jEID = new JTextField("Employee ID");
		panel.add(jEID);
		
		JTextField jWorksFor = new JTextField("Dealership");
		panel.add(jWorksFor);
		
		JButton doneButton = new JButton("DONE");
		
	
		
		class DoneListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0){
				int eID = 0;
				if(!jEID.getText().equals("Employee ID")){
				eID = Integer.parseInt(jEID.getText());
				}
				
				String worksFor = null;
				if(!jWorksFor.getText().equals("Dealership")){
				worksFor = jWorksFor.getText();
				}
				
				
				add(eID,worksFor);
				frame.setVisible(false);
			}
			
		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);
	
}


public int add(int eID, String dealership) {
	
	CallableStatement cs = null;
	
	try {
		cs = this.dbService.getConnection().prepareCall("{ ? = call addSalesPerson(?,?)}" );
		cs.registerOutParameter(1, Types.INTEGER);
		cs.setInt(2, eID);
		cs.setString(3, dealership);
		cs.execute();
		int returnValue = cs.getInt(1);
		if(returnValue == 1) {
			JOptionPane.showMessageDialog(null, "ERROR: Must enter an EmployeeID");
			return 0;
		}else if(returnValue == 2) {
			JOptionPane.showMessageDialog(null, "ERROR: Must enter a dealership");
			return 0;
		}
	} catch (SQLException e) {
		e.printStackTrace();
//		JOptionPane.showMessageDialog(null, "Add Restaurant not implemented.");
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

				

				edit(EID, worksFor);
				frame.setVisible(false);
			}

		}
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);

	}

	public int edit(int eID, String worksFor) {

		CallableStatement cs = null;

		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call editSalesPerson(?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, eID);
			cs.setString(3, worksFor);
			cs.execute();
			int returnValue = cs.getInt(1);
			if (returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter an Employee ID");
				return 0;
			} else if (returnValue == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter a Dealership");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;

	}

}

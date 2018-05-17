package CarService;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.MonthDay;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TransactsService {

	DatabaseConnectionService dbService;
	
	public TransactsService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
		public void populateFrame() {
			JFrame frame = new JFrame();
	
			JPanel panel = new JPanel();
			GridLayout layout = new GridLayout(7,1);
			panel.setLayout(layout);
			frame.setSize(1000, 500);
			frame.setTitle("Add Transaction");
		
			JTextField jSalesPerson = new JTextField("EmployeeID");
			panel.add(jSalesPerson);
			
			
			
			JTextField jSellP = new JTextField("Sell Price");
			panel.add(jSellP);
			
			
			JTextField jCar = new JTextField("Car");
			panel.add(jCar);
			
			
			JTextField jCustomer = new JTextField("Customer");
			panel.add(jCustomer);
			
			
			
			JButton doneButton = new JButton("DONE");
			
		
			
			class DoneListener implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					int sellP = 0;
					if(!jSellP.getText().equals("Sell Price")){
						sellP = Integer.parseInt(jSellP.getText());
					}
					
					int salesPerson = 0;
					if(!jSalesPerson.getText().equals("EmployeeID")){
						salesPerson = Integer.parseInt(jSalesPerson.getText());
					}
							
					String car = jCar.getText();
					
					int customer = 0;
					if(!jCustomer.getText().equals("Customer")){
					customer = Integer.parseInt(jCustomer.getText());
				}
					
					
					add(salesPerson,sellP,car,customer);
					frame.setVisible(false);
				}
				
			}
			DoneListener doneListener = new DoneListener();
			doneButton.addActionListener(doneListener);
			panel.add(doneButton);
			frame.add(panel);
			frame.setVisible(true);
		
	}
	
	public int add(int salesPerson,int sellP,String car,int customer) {
		
		CallableStatement cs = null;
		
		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call addTransacts(?,?,?,?)}" );
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, salesPerson);
			cs.setInt(3, sellP);
			cs.setString(4, car);
			cs.setInt(5,customer);
			cs.execute();
			int returnValue = cs.getInt(1);
			if(returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter a valid SalesPerson");
				return 0;
			}else if(returnValue == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter a valid Car");
				return 0;
			}else if(returnValue == 3) {
				JOptionPane.showMessageDialog(null, "ERROR: Must enter a valid Customer");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Add Restaurant not implemented.");
		}
	return 1;
	}
}

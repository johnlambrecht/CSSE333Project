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
		
			JTextField jSalesPerson = new JTextField("SalesPerson");
			panel.add(jSalesPerson);
			
			
			
			JTextField jSellP = new JTextField("Sell Price");
			panel.add(jSellP);
			
			
			JTextField jDate = new JTextField("Date");
			panel.add(jDate);
			
			
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
					
					String salesPerson = jSalesPerson.getText();
					String date = null;
					if(!jDate.getText().equals("Date")){
					date = jDate.getText();
					}
					String car = jCar.getText();
					String customer = jCustomer.getText();
					
					add(salesPerson,sellP,date,car,customer);
					frame.setVisible(false);
				}
				
			}
			DoneListener doneListener = new DoneListener();
			doneButton.addActionListener(doneListener);
			panel.add(doneButton);
			frame.add(panel);
			frame.setVisible(true);
		
	}
	
	public int add(String salesPerson,int sellP,String date,String car,String customer) {
		
		CallableStatement cs = null;
		
		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call addTransacts(?,?,?,?,?)}" );
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, salesPerson);
			cs.setInt(3, sellP);
			cs.setString(4, date);
			cs.setString(5, car);
			cs.setString(6,customer);
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
	
	public int delete(String VIN, JFrame frame) {
		CallableStatement cs = null;

		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call deleteTransacts(?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, VIN);
			cs.execute();
			int returnValue = cs.getInt(1);
			if (returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: This person has already been deleted from the database");
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

}

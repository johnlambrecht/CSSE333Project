

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import CarService.DatabaseConnectionService;

public class SearchListener implements ActionListener{
	
	private DatabaseConnectionService dbService;
	private JPanel dataPanel;

	public SearchListener(DatabaseConnectionService dbService, JPanel dataPanel) {
		this.dbService = dbService;
		this.dataPanel = dataPanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		createFrame();
	}

	private void createFrame() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(7, 1);
		panel.setLayout(layout);
		frame.setSize(1000, 500);
		frame.setTitle("Search Car");
		JTextField jVIN = new JTextField("VIN");
		panel.add(jVIN);

		JTextField jColor = new JTextField("Color");
		panel.add(jColor);

		JTextField jModel = new JTextField("Model");
		panel.add(jModel);

		JTextField jMileage = new JTextField("Mileage");
		panel.add(jMileage);

		JTextField jMSRP = new JTextField("MSRP");
		panel.add(jMSRP);

		JTextField jmanf = new JTextField("Manufacturer");
		panel.add(jmanf);

		JTextField jAvail = new JTextField("Availability");
		panel.add(jAvail);
		
		Boolean greaterThan = false;
		ArrayList<Boolean> temp = new ArrayList<Boolean>();
		temp.add(greaterThan);
		JButton greaterThanButton = new JButton();
		greaterThanButton.setText("Less than or equal to");
		class GreaterThanListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton source = (JButton) e.getSource();
				if(source.getText().equals("Less than or equal to")) {
					source.setText("Greater than or equal to");
					return;
					}
				source.setText("Less than or equal to");
				return;
			}
			
		}

		JButton doneButton = new JButton("DONE");

		class DoneListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String VIN = null;
				if (!jVIN.getText().equals("VIN")) {
					VIN = jVIN.getText();
				}
				
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
				String manf = null;
				if (!jmanf.getText().equals("Manufacturer")) {
					manf = jmanf.getText();
				}
				String avail = null;
				if (!jAvail.getText().equals("Availability")) {
					avail = jAvail.getText();
				}
				
				boolean greaterThan = false;
				if(!greaterThanButton.getText().equals("Less than or equal to")) {
					greaterThan = true;
				}
				search(VIN, color, model, mileage, msrp, manf, avail, frame, greaterThan);
				frame.setVisible(false);
			}

			

		}
		GreaterThanListener greaterThanListener = new GreaterThanListener();
		greaterThanButton.addActionListener(greaterThanListener);
		panel.add(greaterThanButton);
		DoneListener doneListener = new DoneListener();
		doneButton.addActionListener(doneListener);
		panel.add(doneButton);
		frame.add(panel);
		frame.setVisible(true);
		
	}
	
	private void search(String vIN, String color, String model, float mileage, int msrp, String manf,
			String avail, JFrame frame, boolean greaterThan) {
		PreparedStatement psquery = null;
		try {
			String query = "";
			query = buildParameterizedSqlStatementString(vIN, color, model, mileage, msrp, manf, avail, frame, greaterThan);
			
			psquery = this.dbService.getConnection().prepareStatement(query);
			if(vIN != null && !vIN.equals("")) {
				System.out.println("1-1");
				psquery.setString(1, vIN);
			}
			if(color != null && !color.equals("")) {
				System.out.println("1-2");
				psquery.setString(1, color);
			}
			if(model != null && !model.equals("")) {
				System.out.println("1-3");
				psquery.setString(1, model);
			}
			if(mileage != 0) {
				System.out.println("1-4");
//				Double dPrice = Double.parseDouble(price);
				psquery.setFloat(1, mileage);
			}
			if(msrp != 0) {
				System.out.println("1-5");
				psquery.setInt(1, msrp);
			}
			if(manf != null && !manf.equals("")) {
				System.out.println("1-6");
				psquery.setString(1, manf);
			}
			if(avail != null && !avail.equals("")) {
				System.out.println("1-7");
				psquery.setString(1, avail);
			}
			ResultSet rs = psquery.executeQuery();
			parseResults(rs);
			return;
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve car query");
			ex.printStackTrace();
			return;
		}
		
	}
	
	private String buildParameterizedSqlStatementString(String vIN, String color, String model, float mileage, int msrp, String manf,
			String avail, JFrame frame, boolean greaterThan) {
		String sqlStatement = "SELECT VIN, color, model, mileage, msrp, manf, availability \nFROM Car\n";
		ArrayList<String> wheresToAdd = new ArrayList<String>();

		if (vIN != null) {
			System.out.println("1-21");
			wheresToAdd.add("VIN = ?");
		}
		if (color != null) {
			System.out.println("1-22");
			wheresToAdd.add("color = ?");
		}
		if (model != null) {
			System.out.println("1-23");
			wheresToAdd.add("model = ?");
		}
		if (mileage != 0) {
			System.out.println("1-24");
			wheresToAdd.add("mileage <= ?");
		}
		if (msrp != 0) {
			System.out.println("1-25");
			if (greaterThan) {
				wheresToAdd.add("MSRP >= ?");
			} else {
				wheresToAdd.add("MSRP <= ?");
			}
		}
		if (manf != null) {
			System.out.println("1-26");
			wheresToAdd.add("manf = ?");
		}
		if (avail != null) {
			System.out.println("1-27");
			wheresToAdd.add("availability = ?");
		}
		boolean isFirst = true;
		while (wheresToAdd.size() > 0) {
			if (isFirst) {
				sqlStatement = sqlStatement + " WHERE " + wheresToAdd.remove(0);
				isFirst = false;
			} else {
				sqlStatement = sqlStatement + " AND " + wheresToAdd.remove(0);
			}
		}
		return sqlStatement;
	}
	
	private void parseResults(ResultSet rs) {
		// get column names
				int len = 0;
				try {
					len = rs.getMetaData().getColumnCount();
				} catch (SQLException exception) {
					// TODO Auto-generated catch-block stub.
					exception.printStackTrace();
				}
				Vector cols= new Vector(len);
				for(int i=1; i<=len; i++)
					try {
						cols.add(rs.getMetaData().getColumnName(i));
					} catch (SQLException exception) {
						// TODO Auto-generated catch-block stub.
						exception.printStackTrace();
					}


				// Add Data
				Vector data = new Vector();
				try {
					while(rs.next())
					{
					    Vector row = new Vector(len);
					    for(int i=1; i<=len; i++)
					    {
					        row.add(rs.getString(i));
					    }
					    data.add(row);
					}
				} catch (SQLException exception) {
					// TODO Auto-generated catch-block stub.
					exception.printStackTrace();
				}

				// Now create the table
				JTable table = new JTable(data, cols);
				table.setFillsViewportHeight(true);
				JScrollPane scrollTable = new JScrollPane(table);
				scrollTable.setSize(1000, 750);
				
				dataPanel.removeAll();
				dataPanel.add(scrollTable, BorderLayout.CENTER);
				dataPanel.revalidate();
				dataPanel.repaint();
				return;

	}

}

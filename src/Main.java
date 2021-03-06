
//import sodabase.ui.ApplicationRunner;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import CarService.DatabaseConnectionService;

public class Main {

	// public static void main(String[] args) {
	// ApplicationRunner appRunner = new ApplicationRunner();
	// appRunner.runApplication(args);
	// }

	static String DBNAME;
	static boolean showing = true;
	
	public static void main(String[] args) throws SQLException {
		// boolean for show/hide
		
		//Establish a connection with the database
		DBNAME = "CarInventroyManagement";
		DatabaseConnectionService dbService = new DatabaseConnectionService("golem.csse.rose-hulman.edu", DBNAME);
		dbService.connect("carim", "carim123");
		// the main frame
		JFrame frame = new JFrame();
		frame.setSize(1700, 750);
		frame.setTitle("Car Inventory Management");


		// a panel for our buttons
		JPanel buttonPanel = new JPanel();
		GridLayout buttonLayout = new GridLayout(20, 0);
		buttonPanel.setLayout(buttonLayout);
		
		// a panel for the data
		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		dataPanel.setLayout(new BorderLayout());
		
		
		//test
		// creates the drop down box with an example set of strings
		ArrayList<String> dbNames = new ArrayList<String>();
		Statement stmt = null;
	    String query = "USE " + DBNAME  +
                " SELECT name FROM sys.tables";
	    try {
	        stmt = dbService.getConnection().createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	dbNames.add(rs.getString("name"));
	        	
	        }
	    } catch (SQLException e ) {
	    	e.printStackTrace();
	    } 
		JComboBox dbList = new JComboBox(dbNames.toArray());
		buttonPanel.add(dbList);

		// creates each individual button. will need to add listeners to make
		// the buttons work
		JButton addButton = new JButton("Add");
		JButton editButton = new JButton("Edit");
		JButton deleteButton = new JButton("Delete");
		JButton searchButton = new JButton("Search Car");
		JButton showButton = new JButton("Show/Hide Deleted Records");
		

		
		//adding the button listeners to their buttons

		
		EditListener editListener = new EditListener(dbService,dbNames, dbList);
		DeleteListener deleteListener = new DeleteListener(dbService, dbNames,dbList);
		//AddListener addListener = new AddListener(dbService, dbNames.get(dbList.getSelectedIndex()));
		AddListener addListener = new AddListener(dbService, dbNames,dbList);
		SearchListener searchListener = new SearchListener(dbService, dataPanel);
		
		
		addButton.addActionListener(addListener);
		editButton.addActionListener(editListener);
		deleteButton.addActionListener(deleteListener);
		searchButton.addActionListener(searchListener);
		
		DropDownListener ddList = new DropDownListener(dbService, dbList, dataPanel);
		dbList.addActionListener(ddList);
		
		System.out.println(ddList.getTableName());
		
		showHideListener shListener = new showHideListener(dbService, dbList, dataPanel, ddList);
		
		showButton.addActionListener(shListener);
		
		// puts the buttons on the buttonPanel
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(editButton);
		buttonPanel.add(showButton);
		buttonPanel.add(searchButton);
		
		

		
		
		
		
		
		    
		
			
			PreparedStatement psmt = dbService.getConnection().prepareStatement("SELECT * FROM " + dbNames.get(dbList.getSelectedIndex()));
			ResultSet rs = psmt.executeQuery();
			
			// get column names
			int len = rs.getMetaData().getColumnCount();
			Vector cols= new Vector(len);
			for(int i=1; i<=len; i++) // Note starting at 1
			    cols.add(rs.getMetaData().getColumnName(i));


			// Add Data
			Vector data = new Vector();
			while(rs.next())
			{
			    Vector row = new Vector(len);
			    for(int i=1; i<=len; i++)
			    {
			        row.add(rs.getString(i));
			    }
			    data.add(row);
			}

			// Now create the table
			JTable table = new JTable(data, cols);
			table.setFillsViewportHeight(true);
			JScrollPane scrollTable = new JScrollPane(table);
			scrollTable.setSize(1000, 750);
			dataPanel.add(scrollTable, BorderLayout.CENTER);
			
			
			// puts all the buttons on the left of the frame
			frame.add(buttonPanel, BorderLayout.EAST);
			frame.add(dataPanel, BorderLayout.CENTER);

			frame.setVisible(true);
	}


		
	
	

}


//import sodabase.ui.ApplicationRunner;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import CarService.DatabaseConnectionService;

public class Main {

	// public static void main(String[] args) {
	// ApplicationRunner appRunner = new ApplicationRunner();
	// appRunner.runApplication(args);
	// }

	static String DBNAME;
	
	public static void main(String[] args) {
		//Establish a connection with the database
		DBNAME = "CarInventroyManagement";
		DatabaseConnectionService dbService = new DatabaseConnectionService("golem.csse.rose-hulman.edu", DBNAME);
		dbService.connect("carim", "carim123");
		// the main frame
		JFrame frame = new JFrame();
		frame.setSize(1700, 750);
		frame.setTitle("Car Inventory Management");

		// these are the frames for the button pop ups
		JFrame addFrame = new JFrame();
		addFrame.setTitle("Add");
		addFrame.setSize(1000, 500);

		JFrame editFrame = new JFrame();
		editFrame.setTitle("Edit");
		editFrame.setSize(1000, 500);

		JFrame deleteFrame = new JFrame();
		deleteFrame.setTitle("Delete");
		deleteFrame.setSize(1000, 500);

		// a panel for our buttons
		JPanel buttonPanel = new JPanel();

		// a panel for the data
		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// creates the drop down box with an example set of strings
		ArrayList<String> dbNames = new ArrayList<String>();
		Statement stmt = null;
	    String query = "USE " + DBNAME +
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

		// button listeners that set their respective frames visible
		class AddListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addFrame.setVisible(true);
			}
		}

		class EditListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				editFrame.setVisible(true);
			}
		}

		class DeleteListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				deleteFrame.setVisible(true);
			}
		}

		//adding the button listeners to their buttons
		AddListener addListener = new AddListener();
		addButton.addActionListener(addListener);
		
		EditListener editListener = new EditListener();
		editButton.addActionListener(editListener);
		
		DeleteListener deleteListener = new DeleteListener();
		deleteButton.addActionListener(deleteListener);
		
		// puts the buttons on the buttonPanel
		buttonPanel.add(addButton, BorderLayout.NORTH);
		buttonPanel.add(deleteButton, BorderLayout.CENTER);
		buttonPanel.add(editButton, BorderLayout.SOUTH);

		// puts all the buttons on the left of the frame
		frame.add(buttonPanel, BorderLayout.EAST);
		frame.add(dataPanel, BorderLayout.CENTER);

		frame.setVisible(true);

	}

}

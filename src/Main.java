import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	public static void main(String[] args) {
		
		// the main frame
		JFrame frame = new JFrame();
		frame.setSize(1700, 750);
		
		//a panel for our buttons
		JPanel buttonPanel = new JPanel();
		
		//a panel for the data
		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		// creates the drop down box with an example set of strings
		String[] dbNames = { "Car", "Person" };
		JComboBox dbList = new JComboBox(dbNames);
		buttonPanel.add(dbList);
		

		// creates each individual button. will need to add listeners to make
		// the buttons work
		JButton addButton = new JButton("Add");
		JButton editButton = new JButton("Edit");
		JButton deleteButton = new JButton("Delete");

		//puts the buttons on the buttonPanel 
		buttonPanel.add(addButton, BorderLayout.NORTH);
		buttonPanel.add(deleteButton, BorderLayout.CENTER);
		buttonPanel.add(editButton, BorderLayout.SOUTH);
		
		//puts all the buttons on the left of the frame
		frame.add(buttonPanel, BorderLayout.EAST);
		frame.add(dataPanel,BorderLayout.CENTER);

		frame.setVisible(true);

	}

}

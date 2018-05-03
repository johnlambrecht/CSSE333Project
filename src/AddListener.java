import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import CarService.CarService;
import CarService.DatabaseConnectionService;
import CarService.DealershipService;
import CarService.ManufacturerService;
import CarService.MembershipService;
import CarService.TransactsService;

public class AddListener implements ActionListener{
	DatabaseConnectionService dbService;
	ArrayList<String> dbNames;
	JComboBox dbList;

	
	public AddListener(DatabaseConnectionService dbService, ArrayList<String>dbNames, JComboBox dbList) {
		this.dbService = dbService;
		this.dbNames = dbNames;
		this.dbList = dbList;
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JFrame addFrame = new JFrame();
		addFrame.setSize(1000, 500);
		//JPanel panel = new JPanel();
		switch(this.dbNames.get(dbList.getSelectedIndex())) {
		case "Car":
			System.out.println("In add car");
			CarService car = new CarService(dbService);
			car.populateFrame();
			break;
		case "Manufacturer":
			//addFrame.setTitle("Add Manufacturer");
			ManufacturerService manf = new ManufacturerService(dbService);
			manf.populateFrame();
			
			break;
		case "Dealership":
			//addFrame.setTitle("Add Dealership");
			DealershipService deal = new DealershipService(dbService);
			deal.populateFrame();
			break;
		case "Transacts":
			//addFrame.setTitle("Add Transacts");
			TransactsService trans = new TransactsService(dbService);
			trans.populateFrame();
			break;
		case "Membership":
			//addFrame.setTitle("Add Membership");
			MembershipService memb = new MembershipService(dbService);
			memb.populateFrame();
			break;
		}
	}

}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import CarService.CarService;
import CarService.DatabaseConnectionService;
import CarService.DealershipService;
import CarService.ManufacturerService;
import CarService.MembershipService;
import CarService.TransactsService;

public class AddListener implements ActionListener{
	DatabaseConnectionService dbService;
	String dbName;
	
	public AddListener(DatabaseConnectionService dbService, String dbName) {
		this.dbService = dbService;
		this.dbName = dbName;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JFrame addFrame = new JFrame();
		addFrame.setTitle("Add");
		addFrame.setSize(1000, 500);
		addFrame.setVisible(true);
		switch(this.dbName) {
		case "Car":
			
			CarService car = new CarService(dbService);
			break;
		case "Manufacturer":
			ManufacturerService manf = new ManufacturerService(dbService);
			break;
		case "Dealership":
			DealershipService deal = new DealershipService(dbService);
			break;
		case "Transacts":
			TransactsService trans = new TransactsService(dbService);
			break;
		case "Membership":
			MembershipService memb = new MembershipService(dbService);
			break;
		}
	}

}

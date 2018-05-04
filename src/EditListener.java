import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import CarService.CarService;
import CarService.CustomerService;
import CarService.DatabaseConnectionService;
import CarService.DealershipService;
import CarService.ManufacturerService;
import CarService.MembershipService;
import CarService.ServiceCenterService;
import CarService.ServicesService;
import CarService.TransactsService;

public class EditListener implements ActionListener{
	DatabaseConnectionService dbService;
	String dbName;
	
	public EditListener(DatabaseConnectionService dbService, String dbName) {
		this.dbService = dbService;
		this.dbName = dbName;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
				JFrame addFrame = new JFrame();
				addFrame.setTitle("Add");
				addFrame.setSize(1000, 500);
				addFrame.setVisible(true);
				switch(this.dbName) {
				case "Car":
					CarService car = new CarService(dbService);
					car.populateFrame();
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
				case "Customer":
					CustomerService cust = new CustomerService(dbService);
					cust.populateFrame();
					break;
				case "Service":
					ServicesService ser =  new ServicesService(dbService);
					ser.populateFrame();
					break;
				case "Service Center":
					ServiceCenterService serCen = new ServiceCenterService(dbService);
					break;
				}
	}

}

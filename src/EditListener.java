import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import CarService.CarService;
import CarService.CustomerService;
import CarService.DatabaseConnectionService;
import CarService.DealershipService;
import CarService.ManufacturerService;
import CarService.MembershipService;
import CarService.PersonService;
import CarService.ServiceCenterService;
import CarService.ServicesService;
import CarService.TransactsService;

public class EditListener implements ActionListener{
	DatabaseConnectionService dbService;
	ArrayList<String> dbNames;
	JComboBox dbList;
	
	
	public EditListener(DatabaseConnectionService dbService, ArrayList<String>dbNames, JComboBox dbList) {
		this.dbService = dbService;
		this.dbNames = dbNames;
		this.dbList = dbList;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
				JFrame editFrame = new JFrame();
				editFrame.setTitle("Edit");
				editFrame.setSize(1000, 500);
				editFrame.setVisible(true);
				switch(this.dbNames.get(dbList.getSelectedIndex())) {
				case "Car":
					CarService car = new CarService(dbService);
					car.populateEditFrame(editFrame);
					break;
//				case "Manufacturer":
//					ManufacturerService manf = new ManufacturerService(dbService);
//					manf.populateEditFrame(editFrame);
//					break;
				case "Dealership":
					DealershipService deal = new DealershipService(dbService);
					deal.populateEditFrame(editFrame);
					break;
				case "Transacts":
					TransactsService trans = new TransactsService(dbService);
					break;
				case "Membership":
					MembershipService memb = new MembershipService(dbService);
					memb.populateEditFrame(editFrame);
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
				case "Person":
					PersonService perserv = new PersonService(dbService);
					perserv.populateEditFrame(editFrame);
					break;
				}
	}

}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import CarService.CarService;
import CarService.CustomerService;
import CarService.DatabaseConnectionService;
import CarService.DealershipService;
import CarService.ManufacturerService;
import CarService.MembershipService;
import CarService.SalesPersonService;
import CarService.ServicesService;
import CarService.TransactsService;

public class DeleteListener implements ActionListener {
	DatabaseConnectionService dbService;
	ArrayList<String> dbNames;
	JComboBox dbList;

	public DeleteListener(DatabaseConnectionService dbService, ArrayList<String>dbNames, JComboBox dbList) {
		this.dbService = dbService;
		this.dbNames = dbNames;
		this.dbList = dbList;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFrame frame = new JFrame();
		frame.setSize(1000, 500);
		switch(this.dbNames.get(dbList.getSelectedIndex())) {
		case "Car":
			frame.setTitle("Delete Car");
			CarService car = new CarService(dbService);
			car.populateDeleteFrame(frame);
			break;
		case "Manufacturer":
			frame.setTitle("Delete Manufacturer");
			ManufacturerService manf = new ManufacturerService(dbService);
			manf.populateDeleteFrame(frame);
			break;
		case "Dealership":
			frame.setTitle("Delete Dealership");
			DealershipService deal = new DealershipService(dbService);
			deal.populateDeleteFrame(frame);
			break;
		case "Transacts":
			JOptionPane.showMessageDialog(null, "Cannot delete a transaction");
			break;
		case "Membership":
			frame.setTitle("Delete Membership");
			MembershipService memb = new MembershipService(dbService);
			memb.populateDeleteFrame(frame);
			break;
		case "Services":
			JOptionPane.showMessageDialog(null, "Cannot delete a service");
			break;
		case "Service Center":
			ServicesService ser =  new ServicesService(dbService);
			ser.populateDeleteFrame(frame);
			break;
		case "Customer":
			CustomerService cust = new CustomerService(dbService);
			cust.populateDeleteFrame(frame);
			break;
		case "SalesPerson":
			SalesPersonService salesP = new SalesPersonService(dbService);
			salesP.populateDeleteFrame(frame);
			break;
		}

	}

}

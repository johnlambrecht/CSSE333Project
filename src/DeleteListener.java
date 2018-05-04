import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import CarService.CarService;
import CarService.DatabaseConnectionService;
import CarService.DealershipService;
import CarService.ManufacturerService;
import CarService.MembershipService;
import CarService.TransactsService;

public class DeleteListener implements ActionListener {
	DatabaseConnectionService dbService;
	String dbName;

	public DeleteListener(DatabaseConnectionService dbService, String dbName) {
		this.dbService = dbService;
		this.dbName = dbName;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFrame frame = new JFrame();
		frame.setSize(1000, 500);
		switch(this.dbName) {
		case "Car":
			System.out.println("In add car");
			CarService car = new CarService(dbService);
			car.populateDeleteFrame(frame);
			break;
		case "Manufacturer":
			frame.setTitle("Add Manufacturer");
			ManufacturerService manf = new ManufacturerService(dbService);
			
			break;
		case "Dealership":
			frame.setTitle("Add Dealership");
			DealershipService deal = new DealershipService(dbService);
			deal.populateFrame();
			break;
		case "Transacts":
			frame.setTitle("Add Transacts");
			TransactsService trans = new TransactsService(dbService);
			break;
		case "Membership":
			frame.setTitle("Add Membership");
			MembershipService memb = new MembershipService(dbService);
			break;
		}

	}

}

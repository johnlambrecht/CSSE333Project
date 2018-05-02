import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddCarListener implements ActionListener{

	public AddCarListener(JFrame addFrame){
		JPanel addPanel = new JPanel();
		JTextField carVin = new JTextField("             VIN             ");
		JTextField carColor = new JTextField("            COLOR            ");
		JTextField carModel = new JTextField("            MODEL            ");
		JTextField carMileage = new JTextField("            MILEAGE            ");
		JTextField carMSRP = new JTextField("            MSRP             ");
		JTextField carManf = new JTextField("          MANUFACTURER          ");
		JButton done = new JButton("Done");
	
		addPanel.add(carVin);
		addPanel.add(carColor);
		addPanel.add(carModel);
		addPanel.add(carMileage);
		addPanel.add(carMSRP);
		addPanel.add(carManf);
		addPanel.add(done, BorderLayout.SOUTH);
		
		class DoneListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String cVin = carVin.getText();
				String cColor = carColor.getText();
				String cModel = carModel.getText();
				String cMileage = carMileage.getText();
				String cMSRP = carMSRP.getText();
				String cManf = carManf.getText();
				
			}
			
		}
		
		DoneListener doneListen = new DoneListener();
		done.addActionListener(doneListen);
		
		
		
	
		
		
		
		
		addFrame.add(addPanel);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

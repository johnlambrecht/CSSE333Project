import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ButtonListener implements ActionListener {
	private char buttonLetter;

	public ButtonListener(char letter) {
		this.buttonLetter = letter;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (this.buttonLetter == 'a') {
			JFrame addFrame = new JFrame();
			addFrame.setTitle("Add");
			addFrame.setSize(1000, 500);
			addFrame.setVisible(true);
			
		} else if (this.buttonLetter == 'e') {
			JFrame editFrame = new JFrame();
			editFrame.setTitle("Edit");
			editFrame.setSize(1000, 500);
			editFrame.setVisible(true);
			
		} else if (this.buttonLetter == 'd') {
			JFrame deleteFrame = new JFrame();
			deleteFrame.setTitle("Delete");
			deleteFrame.setSize(1000, 500);
			deleteFrame.setVisible(true);
		}

	}

}

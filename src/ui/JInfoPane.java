package ui;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class JInfoPane extends JScrollPane {

	public JInfoPane() {
		super();
		JTextArea infoText = new JTextArea();
		infoText.setEditable(false);
		super.setViewportView(infoText);
	}

}

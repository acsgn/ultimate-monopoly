package ui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.DropMode;
import javax.swing.SwingConstants;

public class JControls extends JPanel {
	private JTextField txt1;

	/**
	 * Create the panel.
	 */
	public JControls() {
		setLayout(null);
		
		txt1 = new JTextField();
		txt1.setHorizontalAlignment(SwingConstants.CENTER);
		txt1.setText("textDisplay 1");
		txt1.setBounds(12, 13, 276, 168);
		add(txt1);
		
		JTextArea txtrTextdisplay = new JTextArea();
		txtrTextdisplay.setFont(new Font("Arial", Font.PLAIN, 14));
		txtrTextdisplay.setEditable(false);
		txtrTextdisplay.setText("textDisplay 2");
		txtrTextdisplay.setBounds(12, 195, 276, 183);
		
		add(txtrTextdisplay);
		
		JButton btnStandardButton = new JButton("Swing Button");
		btnStandardButton.setBounds(12, 838, 276, 54);
		add(btnStandardButton);

	}
}

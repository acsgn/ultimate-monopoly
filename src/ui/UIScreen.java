package ui;


import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DropMode;
import javax.swing.SwingConstants;

import ObserverPattern.Observer;
import ObserverPattern.Subject;

public class UIScreen extends JFrame implements Subject{
	private JTextField txt1;
	private JButton payRent;
	private String message;
	private JLabel label;
	
	/// obeservers
	private ArrayList<Observer> observers; 
	/**
	 * Create the panel.
	 */
	public UIScreen(Observer o) {
		setLayout(null);
		
		/// Adding the observer
			observers = new ArrayList<>();
			this.registerObserver(o);	
		///
		txt1 = new JTextField();
		txt1.setHorizontalAlignment(SwingConstants.CENTER);
		txt1.setText("textDisplay 1");
		txt1.setBounds(12, 13, 276, 168);
		//add(txt1);
		
		JTextArea txtrTextdisplay = new JTextArea();
		txtrTextdisplay.setFont(new Font("Arial", Font.PLAIN, 14));
		txtrTextdisplay.setEditable(false);
		txtrTextdisplay.setText("textDisplay 2");
		txtrTextdisplay.setBounds(12, 195, 276, 183);
		//add(txtrTextdisplay);
		
		payRent = new JButton();
		payRent.setText("Pay Rent");
		payRent.setBounds(12, 195, 276, 183);
		add(payRent);
		
		label = new JLabel("Hello");
		label.setBounds(12, 13, 276, 168);
		add(label);
		
		
		JButton btnStandardButton = new JButton("Swing Button");
		btnStandardButton.setBounds(12, 838, 276, 54);
		add(btnStandardButton);
		
		
		// Action Listeners
		payRent.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				message = "Pay Rent";
				notifyObservers();
			}
		});
	}
	@Override
	public void registerObserver(Observer o) {
		// TODO Auto-generated method stub
		observers.add(o);
	}
	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		for(Observer o: observers){
			o.update(message);
		}
	}
	
	public void receiveMessage(String message){
		label.setText(message);
	}
}

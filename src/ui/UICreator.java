package ui;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ObserverPattern.Observer;
import ObserverPattern.Subject;

public class UICreator extends JFrame implements Subject {
	private static final long serialVersionUID = 1L;
	private static final String ultimateMonopoly = "resources/ultimate_monopoly.jpg";

	private ArrayList<Observer> observers;
	private String message;

	private int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

	/**
	 * Create the frame.
	 */
	public UICreator(Observer o) {
		setTitle("Ultimate Monopoly by Waterfall Haters!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setBounds((screenWidth - 636) / 2, (screenHeight - 450) / 2, 636, 487);
		getContentPane().setLayout(null);

		/// Adding the observer
		observers = new ArrayList<>();
		this.registerObserver(o);
		///

		JLabel image = new JLabel();
		image.setIcon(new ImageIcon(ultimateMonopoly));
		image.setBounds(0, 0, 630, 252);
		getContentPane().add(image);

		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton serverButton = new JRadioButton("Server");
		serverButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		buttonGroup.add(serverButton);
		serverButton.setBounds(30, 272, 100, 75);
		getContentPane().add(serverButton);

		JRadioButton clientButton = new JRadioButton("Client");
		clientButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		buttonGroup.add(clientButton);
		clientButton.setSelected(true);
		clientButton.setBounds(30, 357, 100, 75);
		getContentPane().add(clientButton);

		JLabel playerCountLabel = new JLabel("Number of Players:");
		playerCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerCountLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		playerCountLabel.setBounds(165, 272, 200, 20);
		getContentPane().add(playerCountLabel);

		JSlider slider = new JSlider();
		slider.setFont(new Font("Tahoma", Font.PLAIN, 24));
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setValue(4);
		slider.setMaximum(10);
		slider.setMinimum(2);
		slider.setBounds(165, 292, 200, 55);

		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(2, new JLabel("2"));
		labelTable.put(3, new JLabel("3"));
		labelTable.put(4, new JLabel("4"));
		labelTable.put(5, new JLabel("5"));
		labelTable.put(6, new JLabel("6"));
		labelTable.put(7, new JLabel("7"));
		labelTable.put(8, new JLabel("8"));
		labelTable.put(9, new JLabel("9"));
		labelTable.put(10, new JLabel("10"));
		slider.setLabelTable(labelTable);
		slider.setEnabled(false);

		JPanel IPPanel = new JPanel();
		IPPanel.setBounds(165, 357, 200, 75);
		IPPanel.setLayout(null);

		JLabel IPLabel = new JLabel("Server IP:");
		IPLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		IPLabel.setHorizontalAlignment(SwingConstants.CENTER);
		IPLabel.setBounds(35, 10, 130, 20);
		IPPanel.add(IPLabel);

		JTextField IPTextField = new JTextField();
		IPTextField.setHorizontalAlignment(SwingConstants.CENTER);
		IPTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		IPTextField.setBounds(0, 35, 200, 30);
		IPPanel.add(IPTextField);

		getContentPane().add(IPPanel);

		serverButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				slider.setEnabled(true);
				IPTextField.setEnabled(false);
			}
		});

		clientButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				slider.setEnabled(false);
				IPTextField.setEnabled(true);
			}
		});

		getContentPane().add(slider);

		JButton startGameButton = new JButton("Start Game");
		startGameButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		startGameButton.setBounds(400, 312, 200, 80);
		getContentPane().add(startGameButton);

		startGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (buttonGroup.isSelected(serverButton.getModel())) {
					message = "SERVER/" + slider.getValue();
				} else {
					message = "CLIENT/" + IPTextField.getText();
				}
				System.out.println(message);
				// notifyObservers();
			}
		});

	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			System.out.println(message);
			o.update(message);
		}
	}
}

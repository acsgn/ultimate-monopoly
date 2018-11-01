package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class mainUI {

	private JFrame frmUltimateMonopolyBy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainUI window = new mainUI();
					window.frmUltimateMonopolyBy.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUltimateMonopolyBy = new JFrame();
		frmUltimateMonopolyBy.setTitle("Ultimate Monopoly by Waterfall Haters!");
		frmUltimateMonopolyBy.setResizable(false);
		frmUltimateMonopolyBy.setBounds(0, 0, 1306, 1035);
		frmUltimateMonopolyBy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUltimateMonopolyBy.getContentPane().setLayout(null);
		
		JLabel board = new JLabel();
		board.setBounds(0, 0, 1000, 1000);
		board.setIcon(new ImageIcon("resources/board.jpg"));
		frmUltimateMonopolyBy.getContentPane().add(board);
		
		JControls cont = new JControls();
		cont.setBounds(1000, 0, 300,1000);
		frmUltimateMonopolyBy.getContentPane().add(cont);
	}

}

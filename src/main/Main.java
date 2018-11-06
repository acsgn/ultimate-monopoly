package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import game.FrontController;
import game.MonopolyGame;
import ui.UIController;
import ui.UIScreen;
import ObserverPattern.Observer;

import javax.swing.ImageIcon;

public class Main {

	private JFrame frmUltimateMonopolyBy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MonopolyGame game = new MonopolyGame();
		FrontController domainController = new FrontController(game);
		UIScreen screen = new UIScreen(domainController);
		screen.setVisible(true);
		screen.setBounds(100, 100, 700, 700);
		UIController uiController = new UIController(screen);
		game.setObserver(uiController);
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new mainUI(observer);
					window.frmUltimateMonopolyBy.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
	}

	/**
	 * Create the application.
	 */
	/*public Main(Observer observer) {
		initialize(observer);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	/*private void initialize(Observer observer) {
		frmUltimateMonopolyBy = new JFrame();
		frmUltimateMonopolyBy.setTitle("Ultimate Monopoly by Waterfall Haters!");
		frmUltimateMonopolyBy.setResizable(false);
		frmUltimateMonopolyBy.setBounds(0, 0, 1306, 1035);
		frmUltimateMonopolyBy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUltimateMonopolyBy.getContentPane().setLayout(null);
		
		JLabel board = new JLabel();
		board.setBounds(0, 0, 1000, 1000);
		board.setIcon(new ImageIcon("resources/board.png"));
		//frmUltimateMonopolyBy.getContentPane().add(board);
		JControls cont = new JControls(observer);
		cont.setBounds(1000, 0, 300,1000);
		UIController control = new UIController();
		frmUltimateMonopolyBy.getContentPane().add(cont);
	}*/

}
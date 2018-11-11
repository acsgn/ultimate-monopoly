package ui;

import javax.swing.JFrame;

public class Animator implements Runnable {

	private static long sleepTime = 25;

	private JFrame panel;

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
			}
			panel.repaint();				
		}
	}

	public Animator(JFrame panel) {
		this.panel = panel;
	}

}

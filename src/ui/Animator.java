package ui;

import javax.swing.JFrame;

public class Animator implements Runnable {

	private static long sleepTime = 25;

	private JFrame frame;

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
			}
			frame.repaint();
		}
	}

	public Animator(JFrame frame) {
		this.frame = frame;
	}

}

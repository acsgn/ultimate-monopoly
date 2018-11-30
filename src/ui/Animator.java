package ui;

import javax.swing.JLabel;

public class Animator implements Runnable {

	private static long sleepTime = 25;

	private volatile boolean animatorStopped = true;
	private boolean animatorDestruct = false;
	private JLabel board;

	@Override
	public void run() {
		while (true) {
			if (animatorDestruct)
				break;
			try {
				synchronized (this) {
					if (animatorStopped)
						wait();
					else {
						Thread.sleep(sleepTime);
						board.repaint();
					}
				}
			} catch (InterruptedException e) {
			}
		}
	}

	public Animator(JLabel board) {
		this.board = board;
	}

	public void startAnimator() {
		synchronized (this) {
			animatorStopped = false;
			notify();
		}
	}

	public void stopAnimator() {
		animatorStopped = true;
	}

	public void destruct() {
		animatorDestruct = true;
	}
}

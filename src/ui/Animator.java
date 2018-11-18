package ui;

import javax.swing.JLabel;

public class Animator implements Runnable {

	private static long sleepTime = 25;

	private boolean animatorStopped = true;
	private boolean animatorDestruct = false;
	private JLabel board;

	@Override
	public void run() {
		while (true) {
			try {
				synchronized (this) {
					if (animatorDestruct)
						break;
					if (animatorStopped)
						wait();
				}
				if (!animatorStopped) {
					Thread.sleep(sleepTime);
				}
			} catch (InterruptedException e) {
			}
			board.repaint();
		}
	}

	public Animator(JLabel board) {
		this.board = board;
	}

	public void startAnimator() {
		animatorStopped = false;
	}

	public void stopAnimator() {
		animatorStopped = true;
	}

	public void destruct() {
		animatorDestruct = true;
	}
}

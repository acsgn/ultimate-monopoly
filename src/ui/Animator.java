package ui;

import javax.swing.JFrame;

public class Animator implements Runnable {

	private static long sleepTime = 25;

	private boolean animatorStopped = true;
	private boolean animatorDestruct = false;
	private JFrame frame;

	@Override
	public void run() {
		while (true) {
			try {
				synchronized (this) {
					if (animatorDestruct) {
						break;
					}
					if (animatorStopped != true) {
						Thread.sleep(sleepTime);
						frame.repaint();
					}
				}
			} catch (InterruptedException e) {
			}
		}
	}

	public Animator(JFrame frame) {
		this.frame = frame;
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

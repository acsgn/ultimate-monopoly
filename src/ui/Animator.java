package ui;

import java.awt.Point;

import javax.swing.JComponent;

public class Animator implements Runnable {

	private static long sleepTime = 25;

	private Path path;
	private JComponent piece;

	@Override
	public void run() {
		while (true) {
			synchronized (this) {
			}
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
			}
			while (path.hasMoreSteps()) {
				Point nextPoint = path.nextPosition();
				piece.setLocation(nextPoint);
			}
		}
	}

	public Animator() {
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public void setPiece(JComponent piece) {
		this.piece = piece;
	}

}

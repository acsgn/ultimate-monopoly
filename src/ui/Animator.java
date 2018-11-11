package ui;

import java.awt.Point;

import javax.swing.JPanel;

public class Animator implements Runnable {

	private static long sleepTime = 500;

	private Path path;
	private JPanel piece;

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
			}
			if (path != null && piece != null)
				if (path.hasMoreSteps()) {
					Point nextPoint = path.nextPosition();
					piece.setLocation(nextPoint);
					piece.repaint();
				}
		}
	}

	public Animator() {
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public void setPiece(JPanel piece) {
		this.piece = piece;
	}

}

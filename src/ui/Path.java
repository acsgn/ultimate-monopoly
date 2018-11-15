package ui;

import java.awt.Point;
import java.util.ArrayList;

public class Path {
	private final static int unscaledDelta = 15;

	private ArrayList<Point> path;
	private int delta;

	public Path(double scaleFactor) {
		delta = (int) (unscaledDelta * scaleFactor);
		path = new ArrayList<Point>();
	}

	/**
	 * Should be a straight line to work
	 */
	public void addLine(int X1, int Y1, int X2, int Y2) {
		if (Math.abs(X1 - X2) <= delta) {
			if (Y1 < Y2) {
				Y1 += delta;
				while (Y1 < Y2) {
					path.add(new Point(X1, Y1));
					Y1 += delta;
				}
			} else {
				Y1 -= delta;
				while (Y1 > Y2) {
					path.add(new Point(X1, Y1));
					Y1 -= delta;
				}
			}
			path.add(new Point(X1, Y2));
		} else if (Math.abs(Y1 - Y2) <= delta) {
			if (X1 < X2) {
				X1 += delta;
				while (X1 < X2) {
					path.add(new Point(X1, Y1));
					X1 += delta;
				}
			} else {
				X1 -= delta;
				while (X1 > X2) {
					path.add(new Point(X1, Y1));
					X1 -= delta;
				}
			}
			path.add(new Point(X2, Y1));
		}
	}

	public boolean hasMoreSteps() {
		return !path.isEmpty();
	}

	public Point nextPosition() {
		return path.remove(0);
	}

	public void mergePaths(Path otherPath) {
		path.addAll(otherPath.path);
	}

}

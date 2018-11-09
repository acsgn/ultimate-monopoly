package ui;

import java.awt.Point;

public class PathFinder {

	private static final int OUTER_TRACK = 1;
	private static final int MIDDLE_TRACK = 2;
	private static final int INNER_TRACK = 3;

	private int middleToOuterFirstTransit = 5;
	private int middleToOuterSecondTransit = 25;
	private int middleToInnerFirstTransit = 15;
	private int middleToInnerSecondTransit = 35;

	private int outerToMiddleFirstTransit = 7;
	private int outerToMiddleSecondTransit = 35;

	private int innerToMiddleFirstTransit = 9;
	private int innerToMiddleSecondTransit = 21;

	private int outerTrackCornerDifference = 14;
	private int middleTrackCornerDifference = 10;
	private int innerTrackCornerDifference = 6;

	private double scaleFactor;

	private int outerTrackSquareStep;
	private int middleTrackSquareStep;
	private int innerTrackSquareStep;

	private int transitMiddleOuterStep;
	private int transitMiddleInnerStep;

	private Point outerTrackDownRightCorner;
	private Point outerTrackUpLeftCorner;

	private Point middleTrackDownRightCorner;
	private Point middleTrackUpLeftCorner;

	private Point innerTrackDownRightCorner;
	private Point innerTrackUpLeftCorner;

	public PathFinder(double scaleFactor) {

		this.scaleFactor = scaleFactor;

		outerTrackSquareStep = getScaled(170);
		middleTrackSquareStep = getScaled(167);
		innerTrackSquareStep = getScaled(163);

		transitMiddleOuterStep = getScaled(700);
		transitMiddleInnerStep = getScaled(683);

		outerTrackUpLeftCorner = new Point(getScaled(204), getScaled(204));
		outerTrackUpLeftCorner = new Point(getScaled(204), getScaled(204));

		middleTrackUpLeftCorner = new Point(getScaled(565), getScaled(565));
		middleTrackUpLeftCorner = new Point(getScaled(2433), getScaled(2433));

		innerTrackUpLeftCorner = new Point(getScaled(920), getScaled(920));
		innerTrackUpLeftCorner = new Point(getScaled(2796), getScaled(2796));
	}

	public Path findPath(int track, int location, int newTrack, int newLocation) {
		if (track == newTrack) {
			return findPathOnSameTrack(location, newLocation, track);
		} else if (Math.abs(track - newTrack) == 1) {
			int[] transitLocations = findTransitStations(track, location, newTrack);
			Path path1 = findPathOnSameTrack(location, transitLocations[0], track);
			//Path path2 = findTransitPath();
			Path path3 = findPathOnSameTrack(transitLocations[1], newLocation, newTrack);
		}
		return null;
	}

	private int[] findTransitStations(int track, int location, int newTrack) {
		int[] result = new int[2];
		if (track == INNER_TRACK) {
			if (location > innerToMiddleFirstTransit && location < innerToMiddleSecondTransit) {
				result[0] = innerToMiddleSecondTransit;
				result[1] = middleToInnerSecondTransit;
			} else {
				result[0] = innerToMiddleFirstTransit;
				result[1] = middleToInnerFirstTransit;
			}
		} else if (track == OUTER_TRACK) {
			if (location > outerToMiddleFirstTransit && location < outerToMiddleSecondTransit) {
				result[0] = outerToMiddleSecondTransit;
				result[1] = middleToOuterSecondTransit;
			} else {
				result[0] = outerToMiddleFirstTransit;
				result[1] = middleToOuterFirstTransit;
			}
		} else {
			if (newTrack == INNER_TRACK) {
				if (location > middleToInnerFirstTransit && location < middleToInnerSecondTransit) {
					result[0] = middleToInnerSecondTransit;
					result[1] = innerToMiddleSecondTransit;
				} else {
					result[0] = middleToInnerFirstTransit;
					result[1] = innerToMiddleFirstTransit;
				}
			} else if (newTrack == OUTER_TRACK) {
				if (location > middleToOuterFirstTransit && location < middleToOuterSecondTransit) {
					result[0] = middleToOuterSecondTransit;
					result[1] = outerToMiddleSecondTransit;
				} else {
					result[0] = middleToOuterFirstTransit;
					result[1] = outerToMiddleFirstTransit;
				}
			}
		}
		return result;
	}

	private Path findPathOnSameTrack(int location, int newLocation, int track) {
		if (track == OUTER_TRACK) {
			return pathOnSameTrack(location, newLocation, outerTrackUpLeftCorner, outerTrackDownRightCorner,
					outerTrackCornerDifference, outerTrackSquareStep);
		} else if (track == MIDDLE_TRACK) {
			return pathOnSameTrack(location, newLocation, middleTrackUpLeftCorner, middleTrackDownRightCorner,
					middleTrackCornerDifference, middleTrackSquareStep);
		} else if (track == INNER_TRACK) {
			return pathOnSameTrack(location, newLocation, innerTrackUpLeftCorner, innerTrackDownRightCorner,
					innerTrackCornerDifference, innerTrackSquareStep);
		}
		return null;
	}

	private Path pathOnSameTrack(int location, int newLocation, Point upLeftCorner, Point downRightCorner,
			int cornerDifference, int trackStep) {
		Path path = new Path(scaleFactor);
		while (location != newLocation) {
			if (location < cornerDifference)
				path.addLine(downRightCorner.x - location * trackStep, downRightCorner.y,
						downRightCorner.x - (location + 1) * trackStep, downRightCorner.y);
			else if (location < 2 * cornerDifference)
				path.addLine(upLeftCorner.x, upLeftCorner.y - (2 * cornerDifference - location) * trackStep,
						upLeftCorner.x, upLeftCorner.y - (2 * cornerDifference - 1 - location) * trackStep);
			else if (location < 3 * cornerDifference)
				path.addLine(upLeftCorner.x + (location - 2 * cornerDifference) * trackStep, upLeftCorner.y,
						upLeftCorner.x + (location + 1 - 2 * cornerDifference) * trackStep, upLeftCorner.y);
			else if (location < 4 * cornerDifference)
				path.addLine(downRightCorner.x, downRightCorner.y + (4 * cornerDifference - location) * trackStep,
						downRightCorner.x, downRightCorner.y + (4 * cornerDifference - 1 - location) * trackStep);
			location = location == 4 * cornerDifference - 1 ? 0 : location + 1;
		}
		return path;
	}

	private int getScaled(int i) {
		return (int) (i / scaleFactor);
	}

}

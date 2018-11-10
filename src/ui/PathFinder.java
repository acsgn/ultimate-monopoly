package ui;

public class PathFinder {

	private static final int OUTER_TRACK = 1;
	private static final int MIDDLE_TRACK = 2;
	private static final int INNER_TRACK = 3;

	private static final int outerTrackUpLeftCorner = 204;
	private static final int outerTrackDownRightCorner = 2795;
	private static final int outerTrackStepDifference = 170;
	private static final int outerTrackCornerDifference = 14;

	private static final int middleTrackUpLeftCorner = 566;
	private static final int middleTrackDownRightCorner = 2432;
	private static final int middleTrackStepDifference = 167;
	private static final int middleTrackCornerDifference = 10;

	private static final int innerTrackUpLeftCorner = 922;
	private static final int innerTrackDownRightCorner = 2074;
	private static final int innerTrackStepDifference = 160;
	private static final int innerTrackCornerDifference = 6;

	private static final int middleToOuterFirstTransitLocation = 5;
	private static final int middleToOuterSecondTransitLocation = 25;
	private static final int middleToInnerFirstTransitLocation = 15;
	private static final int middleToInnerSecondTransitLocation = 35;

	private static final int outerToMiddleFirstTransitLocation = 7;
	private static final int outerToMiddleSecondTransitLocation = 35;

	private static final int innerToMiddleFirstTransitLocation = 9;
	private static final int innerToMiddleSecondTransitLocation = 21;

	private static final int transitMiddleOuterStep = 362;
	private static final int transitMiddleInnerStep = 357;

	private double scaleFactor;

	private Track outerTrack;
	private Track middleTrack;
	private Track innerTrack;

	private TransitStation middleToOuterFirst;
	private TransitStation middleToOuterSecond;
	private TransitStation middleToInnerFirst;
	private TransitStation middleToInnerSecond;
	private TransitStation outerToMiddleFirst;
	private TransitStation outerToMiddleSecond;
	private TransitStation innerToMiddleFirst;
	private TransitStation innerToMiddleSecond;

	public PathFinder(double scaleFactor) {

		this.scaleFactor = scaleFactor;

		outerTrack = new Track(OUTER_TRACK, getScaled(outerTrackUpLeftCorner), getScaled(outerTrackDownRightCorner),
				getScaled(outerTrackStepDifference), outerTrackCornerDifference);

		middleTrack = new Track(MIDDLE_TRACK, getScaled(middleTrackUpLeftCorner), getScaled(middleTrackDownRightCorner),
				getScaled(middleTrackStepDifference), middleTrackCornerDifference);

		innerTrack = new Track(INNER_TRACK, getScaled(innerTrackUpLeftCorner), getScaled(innerTrackDownRightCorner),
				getScaled(innerTrackStepDifference), innerTrackCornerDifference);

		middleToOuterFirst = new TransitStation(middleTrack, middleToOuterFirstTransitLocation, outerTrack,
				outerToMiddleFirstTransitLocation, getScaled(transitMiddleOuterStep));
		middleToOuterSecond = new TransitStation(middleTrack, middleToOuterSecondTransitLocation, outerTrack,
				outerToMiddleSecondTransitLocation, getScaled(transitMiddleOuterStep));
		middleToInnerFirst = new TransitStation(middleTrack, middleToInnerFirstTransitLocation, innerTrack,
				innerToMiddleFirstTransitLocation, getScaled(transitMiddleInnerStep));
		middleToInnerSecond = new TransitStation(middleTrack, middleToInnerSecondTransitLocation, innerTrack,
				innerToMiddleSecondTransitLocation, getScaled(transitMiddleInnerStep));
		outerToMiddleFirst = new TransitStation(outerTrack, outerToMiddleFirstTransitLocation, middleTrack,
				middleToOuterFirstTransitLocation, getScaled(transitMiddleOuterStep));
		outerToMiddleSecond = new TransitStation(outerTrack, outerToMiddleSecondTransitLocation, middleTrack,
				middleToOuterSecondTransitLocation, getScaled(transitMiddleOuterStep));
		innerToMiddleFirst = new TransitStation(innerTrack, innerToMiddleFirstTransitLocation, middleTrack,
				middleToInnerFirstTransitLocation, getScaled(transitMiddleInnerStep));
		innerToMiddleSecond = new TransitStation(innerTrack, innerToMiddleSecondTransitLocation, middleTrack,
				middleToInnerSecondTransitLocation, getScaled(transitMiddleInnerStep));

	}

	public Path findPath(Track track, int location, Track newTrack, int newLocation) {
		if (track == newTrack) {
			return findPathOnSameTrack(location, newLocation, track);
		} else if (Math.abs(track.serial - newTrack.serial) == 1) {
			TransitStation transitStation = findTransitStation(track, location, newTrack);
			Path path = findPathOnSameTrack(location, transitStation.fromLocation, track);
			path.mergePaths(transitStation.getPath());
			path.mergePaths(findPathOnSameTrack(transitStation.toLocation, newLocation, newTrack));
		} else {
			TransitStation transitStation = findTransitStation(track, location, middleTrack);
			TransitStation secondTransitStation = findTransitStation(middleTrack, transitStation.toLocation, newTrack);
			Path path = findPathOnSameTrack(location, transitStation.fromLocation, track);
			path.mergePaths(transitStation.getPath());
			path.mergePaths(findPathOnSameTrack(transitStation.toLocation, secondTransitStation.fromLocation, middleTrack));
			path.mergePaths(secondTransitStation.getPath());
			path.mergePaths(findPathOnSameTrack(secondTransitStation.toLocation, newLocation, newTrack));
		}
		return null;
	}

	private Path findPathOnSameTrack(int location, int newLocation, Track track) {
		Path path = new Path(scaleFactor);
		while (location != newLocation) {
			if (location < track.cornerDifference)
				path.addLine(track.downRightCorner - location * track.stepDistance, track.downRightCorner,
						track.downRightCorner - (location + 1) * track.stepDistance, track.downRightCorner);
			else if (location < 2 * track.cornerDifference)
				path.addLine(track.upLeftCorner,
						track.upLeftCorner - (2 * track.cornerDifference - location) * track.stepDistance,
						track.upLeftCorner,
						track.upLeftCorner - (2 * track.cornerDifference - 1 - location) * track.stepDistance);
			else if (location < 3 * track.cornerDifference)
				path.addLine(track.upLeftCorner + (location - 2 * track.cornerDifference) * track.stepDistance,
						track.upLeftCorner,
						track.upLeftCorner + (location + 1 - 2 * track.cornerDifference) * track.stepDistance,
						track.upLeftCorner);
			else if (location < 4 * track.cornerDifference)
				path.addLine(track.downRightCorner,
						track.downRightCorner + (4 * track.cornerDifference - location) * track.stepDistance,
						track.downRightCorner,
						track.downRightCorner + (4 * track.cornerDifference - 1 - location) * track.stepDistance);
			location = location == 4 * track.cornerDifference - 1 ? 0 : location + 1;
		}
		return path;
	}

	private TransitStation findTransitStation(Track track, int location, Track newTrack) {
		if (track == innerTrack) {
			if (location > innerToMiddleFirst.fromLocation && location < innerToMiddleSecond.fromLocation)
				return innerToMiddleSecond;
			else
				return innerToMiddleFirst;
		} else if (track == outerTrack) {
			if (location > outerToMiddleFirst.fromLocation && location < outerToMiddleSecond.fromLocation)
				return outerToMiddleSecond;
			else
				return outerToMiddleFirst;
		} else {
			if (newTrack == innerTrack) {
				if (location > middleToInnerFirst.fromLocation && location < middleToInnerSecond.fromLocation)
					return middleToInnerSecond;
				else
					return middleToInnerFirst;
			} else if (newTrack == outerTrack) {
				if (location > middleToOuterFirst.fromLocation && location < middleToOuterSecond.fromLocation)
					return middleToOuterSecond;
				else
					return middleToOuterFirst;
			}
		}
		return null;
	}

	private int getScaled(int i) {
		return (int) (i / scaleFactor);
	}

	private class Track {

		private int serial;
		private int upLeftCorner;
		private int downRightCorner;
		private int stepDistance;
		private int cornerDifference;

		public Track(int serial, int upLeftCorner, int downRightCorner, int stepDistance, int cornerDifference) {
			this.serial = serial;
			this.upLeftCorner = upLeftCorner;
			this.downRightCorner = downRightCorner;
			this.stepDistance = stepDistance;
			this.cornerDifference = cornerDifference;
		}

	}

	private class TransitStation {

		private Track from;
		private int fromLocation;
		private Track to;
		private int toLocation;
		private int step;

		public TransitStation(Track from, int fromLocation, Track to, int toLocation, int step) {
			this.from = from;
			this.fromLocation = fromLocation;
			this.to = to;
			this.toLocation = toLocation;
			this.step = step;
		}

		public Path getPath() {
			Path path = new Path(scaleFactor);

			return null;
		}

	}

}

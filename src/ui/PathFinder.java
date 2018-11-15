package ui;

public class PathFinder {

	private static final int OUTER_TRACK = 0;
	private static final int MIDDLE_TRACK = 1;
	private static final int INNER_TRACK = 2;

	private static final int outerTrackUpLeftCorner = 248;
	private static final int outerTrackDownRightCorner = 2671;
	private static final int outerTrackCornerDifference = 14;

	private static final int middleTrackUpLeftCorner = 608;
	private static final int middleTrackDownRightCorner = 2310;
	private static final int middleTrackCornerDifference = 10;

	private static final int innerTrackUpLeftCorner = 960;
	private static final int innerTrackDownRightCorner = 1956;
	private static final int innerTrackCornerDifference = 6;

	private static final int middleToOuterFirstTransitLocation = 5;
	private static final int middleToOuterSecondTransitLocation = 25;
	private static final int middleToInnerFirstTransitLocation = 15;
	private static final int middleToInnerSecondTransitLocation = 35;

	private static final int outerToMiddleFirstTransitLocation = 7;
	private static final int outerToMiddleSecondTransitLocation = 35;

	private static final int innerToMiddleFirstTransitLocation = 9;
	private static final int innerToMiddleSecondTransitLocation = 21;

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

		outerTrack = new Track(getScaled(outerTrackUpLeftCorner), getScaled(outerTrackDownRightCorner),
				outerTrackCornerDifference);

		middleTrack = new Track(getScaled(middleTrackUpLeftCorner), getScaled(middleTrackDownRightCorner),
				middleTrackCornerDifference);

		innerTrack = new Track(getScaled(innerTrackUpLeftCorner), getScaled(innerTrackDownRightCorner),
				innerTrackCornerDifference);

		middleToOuterFirst = new TransitStation(middleTrack, middleToOuterFirstTransitLocation, outerTrack,
				outerToMiddleFirstTransitLocation);
		middleToOuterSecond = new TransitStation(middleTrack, middleToOuterSecondTransitLocation, outerTrack,
				outerToMiddleSecondTransitLocation);
		middleToInnerFirst = new TransitStation(middleTrack, middleToInnerFirstTransitLocation, innerTrack,
				innerToMiddleFirstTransitLocation);
		middleToInnerSecond = new TransitStation(middleTrack, middleToInnerSecondTransitLocation, innerTrack,
				innerToMiddleSecondTransitLocation);
		outerToMiddleFirst = new TransitStation(outerTrack, outerToMiddleFirstTransitLocation, middleTrack,
				middleToOuterFirstTransitLocation);
		outerToMiddleSecond = new TransitStation(outerTrack, outerToMiddleSecondTransitLocation, middleTrack,
				middleToOuterSecondTransitLocation);
		innerToMiddleFirst = new TransitStation(innerTrack, innerToMiddleFirstTransitLocation, middleTrack,
				middleToInnerFirstTransitLocation);
		innerToMiddleSecond = new TransitStation(innerTrack, innerToMiddleSecondTransitLocation, middleTrack,
				middleToInnerSecondTransitLocation);

	}

	public Path findPath(int trackID, int location, int newTrackID, int newLocation) {
		if (trackID == newTrackID) {
			Track track = getTrackByID(trackID);
			return findPathOnSameTrack(location, newLocation, track);
		} else if (Math.abs(trackID - newTrackID) == 1) {
			Track track = getTrackByID(trackID);
			Track newTrack = getTrackByID(newTrackID);
			TransitStation transitStation = findTransitStation(track, location, newTrack);
			Path path = findPathOnSameTrack(location, transitStation.fromLocation, track);
			path.mergePaths(transitStation.getPath());
			path.mergePaths(findPathOnSameTrack(transitStation.toLocation, newLocation, newTrack));
			return path;
		} else {
			Track track = getTrackByID(trackID);
			Track newTrack = getTrackByID(newTrackID);
			TransitStation transitStation = findTransitStation(track, location, middleTrack);
			TransitStation secondTransitStation = findTransitStation(middleTrack, transitStation.toLocation, newTrack);
			Path path = findPathOnSameTrack(location, transitStation.fromLocation, track);
			path.mergePaths(transitStation.getPath());
			path.mergePaths(
					findPathOnSameTrack(transitStation.toLocation, secondTransitStation.fromLocation, middleTrack));
			path.mergePaths(secondTransitStation.getPath());
			path.mergePaths(findPathOnSameTrack(secondTransitStation.toLocation, newLocation, newTrack));
			return path;
		}
	}

	private Track getTrackByID(int ID) {
		if (ID == OUTER_TRACK)
			return outerTrack;
		else if (ID == MIDDLE_TRACK)
			return middleTrack;
		else
			return innerTrack;
	}

	private Path findPathOnSameTrack(int location, int newLocation, Track track) {
		Path path = new Path(scaleFactor);
		int[] point = track.getLocation(location);
		do {
			location = location == 4 * track.cornerDifference - 1 ? 0 : location + 1;
			int[] nextPoint = track.getLocation(location);
			path.addLine(point[0], point[1], nextPoint[0], nextPoint[1]);
			point = nextPoint;
		} while (location != newLocation);
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
			} else {
				if (location > middleToOuterFirst.fromLocation && location < middleToOuterSecond.fromLocation)
					return middleToOuterSecond;
				else
					return middleToOuterFirst;
			}
		}
	}

	private class Track {

		private int upLeftCorner;
		private int downRightCorner;
		private int stepDistance;
		private int cornerDifference;

		public Track(int upLeftCorner, int downRightCorner, int cornerDifference) {
			this.upLeftCorner = upLeftCorner;
			this.downRightCorner = downRightCorner;
			this.stepDistance = (int) ((downRightCorner - upLeftCorner) / (double) cornerDifference);
			this.cornerDifference = cornerDifference;
		}

		public int[] getLocation(int location) {
			int[] point = new int[2];
			if (location < cornerDifference) {
				point[0] = downRightCorner - location * stepDistance;
				point[1] = downRightCorner;
			} else if (location < 2 * cornerDifference) {
				point[0] = upLeftCorner;
				point[1] = upLeftCorner + (2 * cornerDifference - location) * stepDistance;
			} else if (location < 3 * cornerDifference) {
				point[0] = upLeftCorner + (location - 2 * cornerDifference) * stepDistance;
				point[1] = upLeftCorner;
			} else if (location < 4 * cornerDifference) {
				point[0] = downRightCorner;
				point[1] = downRightCorner - (4 * cornerDifference - location) * stepDistance;
			}
			return point;
		}

	}

	private class TransitStation {

		private Track from;
		private int fromLocation;
		private Track to;
		private int toLocation;

		public TransitStation(Track from, int fromLocation, Track to, int toLocation) {
			this.from = from;
			this.fromLocation = fromLocation;
			this.to = to;
			this.toLocation = toLocation;
		}

		public Path getPath() {
			Path path = new Path(scaleFactor);
			int[] transitPoint = from.getLocation(fromLocation);
			int[] nextTransitPoint = to.getLocation(toLocation);
			path.addLine(transitPoint[0], transitPoint[1], nextTransitPoint[0], nextTransitPoint[1]);
			return path;
		}

	}
	
	private int getScaled(int i) {
		return (int) (i * scaleFactor);
	}

}

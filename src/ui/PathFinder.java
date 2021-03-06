package ui;

import java.awt.Point;

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

	private Track outerTrack;
	private Track middleTrack;
	private Track innerTrack;

	private TransitStation middleOuterFirst;
	private TransitStation middleOuterSecond;
	private TransitStation middleInnerFirst;
	private TransitStation middleInnerSecond;

	public PathFinder(double scaleFactor) {

		outerTrack = new Track(outerTrackUpLeftCorner * scaleFactor, outerTrackDownRightCorner * scaleFactor,
				outerTrackCornerDifference);
		middleTrack = new Track(middleTrackUpLeftCorner * scaleFactor, middleTrackDownRightCorner * scaleFactor,
				middleTrackCornerDifference);
		innerTrack = new Track(innerTrackUpLeftCorner * scaleFactor, innerTrackDownRightCorner * scaleFactor,
				innerTrackCornerDifference);

		middleOuterFirst = new TransitStation(middleTrack, middleToOuterFirstTransitLocation, outerTrack,
				outerToMiddleFirstTransitLocation);
		middleOuterSecond = new TransitStation(middleTrack, middleToOuterSecondTransitLocation, outerTrack,
				outerToMiddleSecondTransitLocation);
		middleInnerFirst = new TransitStation(middleTrack, middleToInnerFirstTransitLocation, innerTrack,
				innerToMiddleFirstTransitLocation);
		middleInnerSecond = new TransitStation(middleTrack, middleToInnerSecondTransitLocation, innerTrack,
				innerToMiddleSecondTransitLocation);
	}

	public void addPoint(Path path, int trackID, int location) {
		int[] point = getTrackByID(trackID).getLocation(location);
		path.addPoint(new Point(point[0], point[1]));
	}

	public void addPath(Path path, int trackID, int location, int newTrackID, int newLocation) {
		if (trackID == newTrackID) {
			Track track = getTrackByID(trackID);
			addPathOnSameTrack(path, location, newLocation, track);
		} else if (Math.abs(trackID - newTrackID) == 1) {
			Track track = getTrackByID(trackID);
			Track newTrack = getTrackByID(newTrackID);
			TransitStation transitStation = findTransitStation(track, location);
			addPathOnSameTrack(path, location, transitStation.getIndexOnTrack(track), track);
			transitStation.addPath(path, track);
			addPathOnSameTrack(path, transitStation.getIndexOnTrack(newTrack), newLocation, newTrack);
			trackID = newTrackID;
			location = newLocation;
		} else {
			Track track = getTrackByID(trackID);
			Track newTrack = getTrackByID(newTrackID);
			TransitStation transitStation = findTransitStation(track, location);
			TransitStation secondTransitStation = findTransitStation(middleTrack,
					transitStation.getIndexOnTrack(middleTrack));
			addPathOnSameTrack(path, location, transitStation.getIndexOnTrack(track), track);
			transitStation.addPath(path, track);
			addPathOnSameTrack(path, transitStation.getIndexOnTrack(middleTrack),
					secondTransitStation.getIndexOnTrack(middleTrack), middleTrack);
			secondTransitStation.addPath(path, middleTrack);
			addPathOnSameTrack(path, secondTransitStation.getIndexOnTrack(newTrack), newLocation, newTrack);
			trackID = newTrackID;
			location = newLocation;
		}
	}

	private Track getTrackByID(int ID) {
		if (ID == OUTER_TRACK)
			return outerTrack;
		else if (ID == MIDDLE_TRACK)
			return middleTrack;
		else if (ID == INNER_TRACK)
			return innerTrack;
		else
			return null;
	}

	private void addPathOnSameTrack(Path path, int location, int newLocation, Track track) {
		int[] point = track.getLocation(location);
		while (location != newLocation) {
			location = (location + 1) % (4 * track.cornerDifference);
			int[] nextPoint = track.getLocation(location);
			path.addLine(point[0], point[1], nextPoint[0], nextPoint[1]);
			point = nextPoint;
		}
	}

	private TransitStation findTransitStation(Track track, int location) {
		TransitStation result;
		if (track == innerTrack) {
			result = middleInnerFirst;
			if (location > middleInnerFirst.getIndexOnTrack(track))
				result = middleInnerSecond;
			if (location > middleInnerSecond.getIndexOnTrack(track))
				result = middleInnerFirst;
		} else if (track == outerTrack) {
			result = middleOuterFirst;
			if (location > middleOuterFirst.getIndexOnTrack(track))
				result = middleOuterSecond;
			if (location > middleOuterSecond.getIndexOnTrack(track))
				return middleOuterFirst;
		} else {
			result = middleOuterFirst;
			if (location > middleOuterFirst.getIndexOnTrack(track))
				result = middleInnerFirst;
			if (location > middleInnerFirst.getIndexOnTrack(track))
				result = middleOuterSecond;
			if (location > middleOuterSecond.getIndexOnTrack(track))
				result = middleInnerSecond;
			if (location > middleInnerSecond.getIndexOnTrack(track))
				result = middleOuterFirst;
		}
		return result;
	}

	private class Track {

		private double upLeftCorner;
		private double downRightCorner;
		private int cornerDifference;
		private double stepDistance;

		public Track(double upLeftCorner, double downRightCorner, int cornerDifference) {
			this.upLeftCorner = upLeftCorner;
			this.downRightCorner = downRightCorner;
			this.cornerDifference = cornerDifference;
			this.stepDistance = (downRightCorner - upLeftCorner) / cornerDifference;
		}

		public int[] getLocation(int location) {
			int[] point = new int[2];
			if (location < cornerDifference) {
				point[0] = (int) (downRightCorner - location * stepDistance);
				point[1] = (int) downRightCorner;
			} else if (location < 2 * cornerDifference) {
				point[0] = (int) upLeftCorner;
				point[1] = (int) (upLeftCorner + (2 * cornerDifference - location) * stepDistance);
			} else if (location < 3 * cornerDifference) {
				point[0] = (int) (upLeftCorner + (location - 2 * cornerDifference) * stepDistance);
				point[1] = (int) upLeftCorner;
			} else if (location < 4 * cornerDifference) {
				point[0] = (int) downRightCorner;
				point[1] = (int) (downRightCorner - (4 * cornerDifference - location) * stepDistance);
			}
			return point;
		}

	}

	private class TransitStation {

		private Track track1;
		private int index1;
		private Track track2;
		private int index2;

		public TransitStation(Track from, int fromLocation, Track to, int toLocation) {
			this.track1 = from;
			this.index1 = fromLocation;
			this.track2 = to;
			this.index2 = toLocation;
		}

		public int getIndexOnTrack(Track track) {
			return track == track1 ? index1 : index2;
		}

		public void addPath(Path path, Track track) {
			int[] transitPoint;
			int[] nextTransitPoint;
			if (track == track1) {
				transitPoint = track1.getLocation(index1);
				nextTransitPoint = track2.getLocation(index2);
			} else {
				transitPoint = track2.getLocation(index2);
				nextTransitPoint = track1.getLocation(index1);
			}
			path.addLine(transitPoint[0], transitPoint[1], nextTransitPoint[0], nextTransitPoint[1]);
		}

	}

}

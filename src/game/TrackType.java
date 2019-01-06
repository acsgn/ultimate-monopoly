package game;

public enum TrackType {
	OUTER_TRACK, MIDDLE_TRACK, INNER_TRACK;

	public static TrackType getType(int ordinal) {
		if (ordinal == OUTER_TRACK.ordinal())
			return OUTER_TRACK;
		else if (ordinal == MIDDLE_TRACK.ordinal())
			return MIDDLE_TRACK;
		else
			return INNER_TRACK;
	}
}

package game;

public enum TrackType {
	OUTER_TRACK(1), MIDDLE_TRACK(2), INNER_TRACK(3);

	private int value;

	private TrackType(int i) {
		value = i;
	}

	public int getValue() {
		return value;
	}

}

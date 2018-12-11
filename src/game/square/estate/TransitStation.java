package game.square.estate;

import game.TrackType;

public class TransitStation extends Estate {

	private static final EstateSquareType type = EstateSquareType.TRANSITSTATION;
	private static final int TRANSIT_STATION_PRICE = 200;

	private int trainDepot = 0;

	private TrackType track1;
	private TrackType track2;
	private int index1;
	private int index2;

	private int rent = 100;

	public TransitStation(String name, TrackType track1, TrackType track2, int index1, int index2) {
		super(name, TRANSIT_STATION_PRICE, type);
		this.track1 = track1;
		this.track2 = track2;
		this.index1 = index1;
		this.index2 = index2;
	}

	// The rent system is prone to change
	@Override
	public int getRent() {
		return rent;
	}

	public void buildDepot() {
		trainDepot++;
	}

	public boolean sellDepot() {
		if (trainDepot > 0) {
			trainDepot--;
			return true;
		}
		return false;
	}

	public TrackType getOtherTrack(TrackType track) {
		return track == track1 ? track2 : track1;
	}

	public int getOtherIndex(TrackType track) {
		return track == track1 ? index2 : index1;
	}

}
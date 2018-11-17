package game.square.estate;

import game.Board;
import game.TrackType;
import game.strategy.RentStrategyFactory;

public class TransitStation extends Estate {

	private static final EstateSquareType type = EstateSquareType.TRANSITSTATION;
	private static final int TRANSIT_STATION_PRICE = 200;

	private int trainDepot = 0;

	private TrackType track1;
	private TrackType track2;
	private int index1;
	private int index2;
	private boolean notInitialized = true;

	private int rent = 100;

	public TransitStation(String name, TrackType track1, TrackType track2) {
		super(name, TRANSIT_STATION_PRICE, type);
		this.track1 = track1;
		this.track2 = track2;
	}

	// The rent system is prone to change
	@Override
	public int getRent() {
		return rent;
	}

	public TrackType getOtherTrack(TrackType track) {
		if (notInitialized)
			initialize();
		return track == track1 ? track2 : track1;
	}

	public int getOtherIndex(TrackType track) {
		if (notInitialized)
			initialize();
		return track == track1 ? index2 : index1;
	}

	private void initialize() {
		index1 = Board.getInstance().getIndexOfSquare(this, track1);
		index2 = Board.getInstance().getIndexOfSquare(this, track2);
		notInitialized = false;
	}

}
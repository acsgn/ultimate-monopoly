package game.square.estate;

import java.util.Hashtable;

import game.Board;
import game.TrackType;
import game.strategy.RentStrategyFactory;

public class TransitStation extends Estate {

	private static final EstateSquareType type = EstateSquareType.TRANSITSTATION;
	
	private int trainDepot = 0;

	private Hashtable<TrackType, TrackLocationPair> transit = new Hashtable<TrackType, TrackLocationPair>(2);
	private TrackType track1;
	private TrackType track2;
	private boolean notInitialized = true;

	private int rent = 100;

	public TransitStation(String name, int price,  TrackType track1, TrackType track2) {
		super(name, price, type);
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
		return transit.get(track).track;
	}

	public int getOtherIndex(TrackType track) {
		if (notInitialized)
			initialize();
		return transit.get(track).index;
	}

	private void initialize() {
		int indexOnTrack1 = Board.getInstance().getIndexOfSquare(this, track1);
		int indexOnTrack2 = Board.getInstance().getIndexOfSquare(this, track2);
		transit.put(track1, new TrackLocationPair(track2, indexOnTrack2));
		transit.put(track2, new TrackLocationPair(track1, indexOnTrack1));
		notInitialized = false;
	}

	private class TrackLocationPair {
		private TrackType track;
		private int index;

		private TrackLocationPair(TrackType track, int index) {
			this.track = track;
			this.index = index;
		}
	}

}
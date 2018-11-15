package game.square.tradable;

import game.Player;
import game.TrackType;
import game.square.Square;

public class TransitstationSquare extends Square {
	
	private TrackType[] tracks = new TrackType[2];

	public TransitstationSquare(String name, int number, TrackType track1, TrackType track2) {
		super(name, number);
		tracks[0] = track1;
		tracks[1] = track2;
	}

	@Override
	public void executeAction(Player player) {

	}

	public TrackType getOtherTrack(TrackType track) {
		if (tracks[0] == track)
			return tracks[1];
		else
			return tracks[0];
	}

}

package game.square.action;

import game.Board;
import game.Player;
import game.TrackType;

public class HollandTunnel extends Action {

	private static final TrackType track1 = TrackType.INNER_TRACK;
	private static final TrackType track2 = TrackType.OUTER_TRACK;

	private boolean notInitialized = true;

	private int index1;
	private int index2;

	public HollandTunnel() {
	}

	@Override
	public void executeWhenLanded(Player player) {
		if (notInitialized)
			initialize();
		if (player.getPosition() == index1)
			player.goTo(track2, index2);
		else
			player.goTo(track1, index1);
	}

	private void initialize() {
		index1 = Board.getInstance().getIndexOfSquare(this, track1);
		index2 = Board.getInstance().getIndexOfSquare(this, track2);
		notInitialized = false;
	}

}
